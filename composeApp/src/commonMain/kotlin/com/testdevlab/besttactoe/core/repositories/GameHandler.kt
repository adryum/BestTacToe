package com.testdevlab.besttactoe.core.repositories

import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_equals
import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.cache.models.ChosenIconDBModel
import com.testdevlab.besttactoe.core.cache.models.GameDBModel
import com.testdevlab.besttactoe.core.cache.models.GameResultDBModel
import com.testdevlab.besttactoe.core.cache.models.ScoreDBModel
import com.testdevlab.besttactoe.core.cache.toJson
import com.testdevlab.besttactoe.core.cache.toObject
import com.testdevlab.besttactoe.core.common.launchDefault
import com.testdevlab.besttactoe.core.networking.SocketClient
import com.testdevlab.besttactoe.core.networking.SocketEvent
import com.testdevlab.besttactoe.core.repositories.MultiplayerHandler.sendCreateGame
import com.testdevlab.besttactoe.core.repositories.MultiplayerHandler.sendGameJoin
import com.testdevlab.besttactoe.ui.GameResult
import com.testdevlab.besttactoe.ui.GameResultModel
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.ParticipantUIModel
import com.testdevlab.besttactoe.ui.Piece
import com.testdevlab.besttactoe.ui.PopUpModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.Segment
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.isLoss
import com.testdevlab.besttactoe.ui.theme.isMultiplayer
import com.testdevlab.besttactoe.ui.theme.isRoboRumble
import com.testdevlab.besttactoe.ui.theme.isVictory
import com.testdevlab.besttactoe.ui.theme.isVsAI
import com.testdevlab.besttactoe.ui.theme.log
import com.testdevlab.besttactoe.ui.theme.toColor
import com.testdevlab.besttactoe.ui.theme.toSegmentDBModelList
import com.testdevlab.besttactoe.ui.theme.toSegmentUIModelList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class GameMode {
    VS_AI,
    HotSeat,
    Multiplayer,
    RoboRumble,
    None
}

object GameHandler {
    private var gameAI: GameAI? = null
    private val _playerData = MutableStateFlow<ParticipantUIModel?>(null)
    private val _opponentData = MutableStateFlow<ParticipantUIModel?>(null)
    private val _tableData = MutableStateFlow<List<SegmentUIModel>>(emptyList())
    private val _gameMode = MutableStateFlow(GameMode.None)
    private val _score = MutableStateFlow<ScoreModel?>(null)
    private val _gameResult = MutableStateFlow<GameResultModel?>(null)
    private val _roundResults = MutableStateFlow<List<GameResult>>(emptyList())
    private val _hasGameEnded = MutableStateFlow(false)
    private val _isRoundTimeout = MutableStateFlow(true)
    private val _canAIMove = MutableSharedFlow<Unit>(1)
    private val _roundCount = 5
    private val _isPlayerTurn = MutableStateFlow(false)
    private val _isGamesStart = MutableStateFlow(false)
    private val _code = MutableStateFlow<String?>(null)
    val isGamesStart = _isGamesStart.asStateFlow()
    var isPlayerTurn = _isPlayerTurn.asStateFlow()
    val gameResult = _gameResult.asStateFlow()
    val hasGameEnded = _hasGameEnded.asStateFlow()
    val isRoundTimeout = _isRoundTimeout.asStateFlow()
    val gameMode = _gameMode.asStateFlow()
    val score = _score.asStateFlow()
    val playerData = _playerData.asStateFlow()
    val opponentData = _opponentData.asStateFlow()
    val tableData = _tableData.asStateFlow()
    val turnHolderPieceType get() = if (_isPlayerTurn.value) Piece.Player else Piece.Opponent
    val turnHolderSegmentType get() = if (_isPlayerTurn.value) Segment.Player else Segment.Opponent
    val roundResults = _roundResults.asStateFlow()
    val code = _code.asStateFlow()

    init {
        // for AI
        launchDefault {
            _canAIMove.collect {
                if (
                    (_gameMode.value.isVsAI() && !_isPlayerTurn.value && !_isRoundTimeout.value) ||
                    (_gameMode.value.isRoboRumble() && !_isRoundTimeout.value)
                ) {
                    delay(if (_gameMode.value.isRoboRumble()) 10 else  700)
                    gameAI?.makeAMove(_tableData.value)
                }
            }
        }
        launchDefault { // create game
            SocketClient.events.collect { event ->
                if (event !is SocketEvent.GameCreated) return@collect

                _code.update { event.response.code }
            }
        }
        launchDefault { // turn. Gets called once per round to determine who will move first
            SocketClient.events.collect { event ->
                if (event !is SocketEvent.GameTurn) return@collect

                while (_playerData.value == null || _opponentData.value == null) {
                    log("delayed")
                    delay(100)
                }

                log("collected ${event.response.id}")
                log("me: ${_playerData.value?.name}  ${_playerData.value?.id}")
                log("opponent: ${_opponentData.value?.name}  ${_opponentData.value?.id}")
                setPlayerTurn(_playerData.value?.id == event.response.id)
            }
        }
        launchDefault { // start game
            SocketClient.events.collect { event ->
                if (event !is SocketEvent.GameStarted) return@collect

                startMultiplayerGame(
                    opponentName = event.response.opponentName,
                    playerId = event.response.selfId,
                    opponentId = event.response.opponentId
                )

                NavigationObject.goTo(Views.GameView)
            }
        }
        launchDefault { // make move
            SocketClient.events.collect { event ->
                if (event !is SocketEvent.MoveMade) return@collect

                makeAMove(MoveModel(
                    segmentIndex = event.response.segment,
                    pieceIndex = event.response.piece
                ))
            }
        }
        launchDefault { // rematch
            SocketClient.events.collect { event ->
                if (event !is SocketEvent.Rematch) return@collect

                NavigationObject.showPopUp(
                    content = PopUpModel(
                        title = "Rematch?",
                        description = "Opponent wants to do a rematch!",
                        buttonOneText = "Fuk Yea!",
                        onActionOne = { startMultiplayerGame(
                            opponentName = _opponentData.value!!.name,
                            opponentId = _opponentData.value!!.id,
                            playerId = _playerData.value!!.id
                        ) },
                        buttonTwoText = "Hell NAW!!!",
                        onActionTwo = { MultiplayerHandler.sendRematch(_code.value!!) }
                    )
                )
            }
        }
        launchDefault { // err
            SocketClient.events.collect { event ->
                if (event is SocketEvent.Error) {

                }
            }
        }
        launchDefault { // ended game. When somebody leaves game
            SocketClient.events.collect { event ->
                if (event !is SocketEvent.GameEnded) return@collect

                NavigationObject.goBack()
                saveAndClearGame()
            }
        }
        launchDefault { // ended round
            SocketClient.events.collect { event ->
                if (event is SocketEvent.RoundEnded) {
                    // don't need to call, it get logically triggered in TTTManager
//                    _isRoundTimeout.update { true }
                }
            }
        }
    }

    fun handleGoingBack(previousView: Views) {
        if (previousView == Views.GameView) {
            saveAndClearGame()
        } else if (previousView == Views.CreateLobbyView) {
            MultiplayerHandler.sendGameLeave(_code.value!!)
        }
    }

    private fun runGamePreparations() {
        _isGamesStart.update { true }
        _isRoundTimeout.update { true }
        makeNewTable()
        makeNewScore()
    }

    fun setParticipantData(opponentName: String, playerId: Int, opponentId: Int) {
        val chosenIcons = Preferences
            .chosenIcons
            ?.toObject<ChosenIconDBModel>()!!

        _playerData.update { ParticipantUIModel(
            name = Preferences.playerName!!,
            icon = chosenIcons.playerIcon.resource,
            tint = chosenIcons.playerTint.toColor(),
            id = playerId
        ) }

        _opponentData.update { ParticipantUIModel(
            name = opponentName,
            icon = chosenIcons.opponentIcon.resource,
            tint = chosenIcons.opponentTint.toColor(),
            id = opponentId
        ) }

        log("SET")
    }

    //region multiplayer
    fun startMultiplayerGame(opponentName: String, playerId: Int, opponentId: Int) {
        setParticipantData(
            opponentName = opponentName,
            playerId = playerId,
            opponentId = opponentId
        )

        _gameMode.update { GameMode.Multiplayer }

        runGamePreparations()
    }

    fun multiplayerRematch() {
        MultiplayerHandler.sendRematch(_code.value!!)
    }

    fun createLobby() {
        sendCreateGame(name = Preferences.playerName!!)
    }

    fun joinLobby(code: String) {
        sendGameJoin(
            code = code,
            name = Preferences.playerName!!
        )

        _code.update { code }
    }
    //endregion
    fun makeAMove(pieceData: MoveModel) {
        log("made move")
        // locally make move -> send move
        // when receiving, just make move
        if (_gameMode.value.isMultiplayer() && _isPlayerTurn.value) {
            MultiplayerHandler.sendMakeMove(
                code = _code.value!!,
                id = _playerData.value!!.id,
                segment = pieceData.segmentIndex,
                piece = pieceData.pieceIndex
            )
            log("sentTurn!")
        }

        // switches turn after completion
        _tableData.update {
            TicTacToeManager.processMove(pieceData, _tableData.value.map { it.copy() })
        }
    }

    fun startLocalGame(gameMode: GameMode) {
        setParticipantData(opponentName = getRandomName(), opponentId = 1, playerId = 0)

        _gameMode.update { gameMode }

        runGamePreparations()

        if (gameMode.isVsAI() || gameMode.isRoboRumble())
            gameAI = GameAI()

        tossACoin()
    }

    fun rematch() {
        rematchGameClean()

        runGamePreparations()

        if (_gameMode.value.isVsAI() || _gameMode.value.isRoboRumble()) {
            gameAI = GameAI()
        }

        tossACoin()
    }

    fun endRound(gameResult: GameResult) {
        log("ended round")
        _roundResults.update {
             _roundResults.value
                .toMutableList()
                .apply { add(gameResult) }
                .toList()
        }

        addScore(
            player = if (gameResult.isVictory()) 1 else 0,
            opponent = if (gameResult.isLoss()) 1 else 0
        )

        val hasGameEnded = _roundResults.value.size >= _roundCount

        if (hasGameEnded) {
            endGame()
        } else {
            _isRoundTimeout.update { true }
        }
    }

    private fun endGame() {
        log("ended game")
        var playerPoints = 0
        var opponentPoints = 0

        for (match in _roundResults.value) {
            if (match.isVictory()) playerPoints++
            else if (match.isLoss()) opponentPoints++
        }

        val gameResult =
            if (playerPoints == opponentPoints) GameResult.Draw
            else if (playerPoints > opponentPoints) GameResult.Victory
            else GameResult.Loss

        _gameResult.update {
            GameResultModel(
                name = when (gameResult) {
                    GameResult.Victory -> _playerData.value!!.name
                    GameResult.Draw -> "DRAW"
                    GameResult.Loss -> _opponentData.value!!.name
                },
                icon = when (gameResult) {
                    GameResult.Victory -> _playerData.value!!.icon
                    GameResult.Draw -> Res.drawable.ic_equals
                    GameResult.Loss -> _opponentData.value!!.icon
                },
                result = gameResult
            )
        }

        _hasGameEnded.update { true }
    }

    fun proceedToTheNextRound() {
        log("preceeded to nex round")
        if (_isGamesStart.value) {
            _isGamesStart.update { false }
        } else {
            makeNewTable()
        }

        if (_gameMode.value.isVsAI() || _gameMode.value.isRoboRumble())
            gameAI = GameAI()

        if (!_gameMode.value.isMultiplayer())
            tossACoin()

        _isRoundTimeout.update { false }
    }

    private fun saveUnfinishedGame() {
        val dataJSON = GameDBModel(
            score = ScoreDBModel(
                playerScore = _score.value?.playerScore!!,
                opponentScore = _score.value?.opponentScore!!,
                opponentName = _opponentData.value!!.name,
                playerName = _playerData.value!!.name
            ),
            table = _tableData.value.toSegmentDBModelList(),
            isPlayerTurn = _isPlayerTurn.value,
            gameMode = _gameMode.value,
            matchHistory = _roundResults.value
        ).toJson()

        if (_gameMode.value.isVsAI())
            Preferences.vsAI = dataJSON
        else
            Preferences.hotSeat = dataJSON
    }

    fun loadGame(gameMode: GameMode) {
        println("Game Loaded!")
        val loadedGame =
            if (gameMode.isVsAI())
                Preferences.vsAI!!.toObject<GameDBModel>()
            else
                Preferences.hotSeat!!.toObject<GameDBModel>()

        setParticipantData(
            opponentName = loadedGame.score.opponentName,
            playerId = 0,
            opponentId = 1
        )

        _gameMode.update { loadedGame.gameMode }

        _score.update {
            ScoreModel(
                playerScore = loadedGame.score.playerScore,
                opponentScore = loadedGame.score.opponentScore
            )
        }

        _tableData.update { loadedGame.table.toSegmentUIModelList() }

        _roundResults.update { loadedGame.matchHistory }

        if (_gameMode.value.isVsAI()) gameAI = GameAI()

        setPlayerTurn(loadedGame.isPlayerTurn)
    }

    fun saveAndClearGame() {
        if (_gameMode.value.isMultiplayer()) {
            MultiplayerHandler.sendGameLeave(_code.value!!)
        }

        if (_hasGameEnded.value) {
            Preferences.addGameToHistory(GameResultDBModel(
                playerName = _playerData.value!!.name,
                opponentName = _opponentData.value!!.name,
                gameMode = _gameMode.value,
                matches = _roundResults.value
            ))
        } else {
            saveUnfinishedGame()
        }

        fullGameClean()
    }

    private fun rematchGameClean() {
        _gameResult.update { null }
        _roundResults.update { emptyList() }
        _hasGameEnded.update { false }
        _score.update { null }
    }

    private fun fullGameClean() {
        rematchGameClean()
        gameAI = null
        _code.update { null }
        _tableData.update { emptyList() }
        _gameMode.update { GameMode.None }
        _playerData.update { null }
        _opponentData.update { null }
    }

    //region other
    private fun getRandomName() = when ((0..10).random()) {
        0 -> "CaramelParrot"
        1 -> "LocalToaster21"
        2 -> "Mighty Playa"
        3 -> "IOS user"
        4 -> "Shih Tzu Lover"
        5 -> "RabidAura123"
        6 -> "Lieutenant Hams"
        7 -> "Biggie Cheese"
        8 -> "Naughty Santa"
        9 -> "BloomingPapaya"
        10 -> "EnvelopedRose"
        else -> "Noname"
    }

    fun setCode(code: String) {
        _code.update { code }
    }
    fun isThereASavedGame(gameMode: GameMode) = Preferences.isThereASavedGame(gameMode)

    private fun addScore(player: Int = 0, opponent: Int = 0) {
        _score.update {
            it?.copy(
                playerScore = it.playerScore + player,
                opponentScore = it.opponentScore + opponent
            )
        }
    }

    private fun setPlayerTurn(value: Boolean) {
        _isPlayerTurn.update { value }

        // triggers AI move
        if ((_gameMode.value.isVsAI() && !_isPlayerTurn.value) || _gameMode.value.isRoboRumble())
            _canAIMove.tryEmit(Unit)
    }

    fun switchTurns() = setPlayerTurn(!_isPlayerTurn.value)

    private fun tossACoin() = setPlayerTurn((0..1).random() == 0)

    private fun makeNewTable() {
        _tableData.update {
            TicTacToeManager.createTable(true)
        }
    }

    private fun makeNewScore(player: Int = 0, opponent: Int = 0) {
        _score.update {
            ScoreModel(
                opponentScore = opponent,
                playerScore = player
            )
        }
    }
    //endregion
}