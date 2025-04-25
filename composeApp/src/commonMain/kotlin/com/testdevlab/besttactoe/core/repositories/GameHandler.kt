package com.testdevlab.besttactoe.core.repositories

import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_equals
import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.cache.models.ChosenVisualDBModel
import com.testdevlab.besttactoe.core.cache.models.GameDBModel
import com.testdevlab.besttactoe.core.cache.models.GameResultDBModel
import com.testdevlab.besttactoe.core.cache.models.ParticipantDBModel
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
import com.testdevlab.besttactoe.ui.Segment
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.isMultiplayer
import com.testdevlab.besttactoe.ui.theme.isRoboRumble
import com.testdevlab.besttactoe.ui.theme.isVsAI
import com.testdevlab.besttactoe.ui.theme.log
import com.testdevlab.besttactoe.ui.theme.toColor
import com.testdevlab.besttactoe.ui.theme.toResultTypeCountModel
import com.testdevlab.besttactoe.ui.theme.toSegmentDBModelList
import com.testdevlab.besttactoe.ui.theme.toSegmentUIModelList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class GameMode(name: String) {
    VS_AI("VS AI"),
    HotSeat("Hot Seat"),
    Multiplayer("Multiplayer"),
    RoboRumble("RoboRumble"),
    None("None")
}

object GameHandler {
    private var gameAI: GameAI? = null
    private val _playerData = MutableStateFlow<ParticipantUIModel?>(null)
    private val _opponentData = MutableStateFlow<ParticipantUIModel?>(null)
    private val _tableData = MutableStateFlow<List<SegmentUIModel>>(emptyList())
    private val _gameMode = MutableStateFlow(GameMode.None)
    private val _gameResult = MutableStateFlow<GameResultModel?>(null)
    private val _roundResults = MutableStateFlow<List<GameResult>>(emptyList())
    private val _hasGameEnded = MutableStateFlow(false)
    private val _isRoundTimeout = MutableStateFlow(true)
    private val _canAIMove = MutableSharedFlow<Unit>(1)
    private val _roundCount = MutableStateFlow(5)
    private val _isPlayerTurn = MutableStateFlow(false)
    private val _isGamesStart = MutableStateFlow(false)
    private val _code = MutableStateFlow<String?>(null)
    private val _doesOpponentWantARematch = MutableStateFlow(false)
    private val _isAnimationShown = MutableStateFlow(false)
    val isAnimationShown = _isAnimationShown.asStateFlow()
    val roundCount = _roundCount.asStateFlow()
    val isGamesStart = _isGamesStart.asStateFlow()
    var isPlayerTurn = _isPlayerTurn.asStateFlow()
    val gameResult = _gameResult.asStateFlow()
    val hasGameEnded = _hasGameEnded.asStateFlow()
    val isRoundTimeout = _isRoundTimeout.asStateFlow()
    val gameMode = _gameMode.asStateFlow()
    val playerData = _playerData.asStateFlow()
    val opponentData = _opponentData.asStateFlow()
    val tableData = _tableData.asStateFlow()
    val turnHolderPieceType get() = if (_isPlayerTurn.value) Piece.Player else Piece.Opponent
    val turnHolderSegmentType get() = if (_isPlayerTurn.value) Segment.Player else Segment.Opponent
    val roundResults = _roundResults.asStateFlow()
    val code = _code.asStateFlow()
    val doesOpponentWantARematch = _doesOpponentWantARematch.asStateFlow()

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
                    opponentId = event.response.opponentId,
                    firstOf = event.response.firstTo
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
        launchDefault { // err
            SocketClient.events.collect { event ->
                if (event is SocketEvent.Error) {

                }
            }
        }
        launchDefault { // ended game. When somebody leaves game
            SocketClient.events.collect { event ->
                if (event !is SocketEvent.GameEnded) return@collect

                NavigationObject.goBackTill(Views.MultiplayerView)
                saveGame()
            }
        }
    }

    fun setAnimation(value: Boolean) {
        _isAnimationShown.update { value }
    }

    fun handleGoingBack(previousView: Views) {
        if (previousView == Views.GameView) {
            saveGame()
        } else if (previousView == Views.CodeView) {
            MultiplayerHandler.sendGameLeave(_code.value!!)
        }
    }

    private fun runGamePreparations(gameMode: GameMode) {
        _gameMode.update { gameMode }
        _isGamesStart.update { true }
        _isRoundTimeout.update { true }
        makeNewTable()
    }

    fun setParticipantData(opponentName: String, playerId: Int = 2, opponentId: Int = 2) {
        val chosenIcons = Preferences
            .chosenVisuals
            ?.toObject<ChosenVisualDBModel>()!!

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

    fun handleRematch() {
        if (_gameMode.value.isMultiplayer()) {
//            if (!_doesOpponentWantARematch.value) {
//                sendRematchRequest()
//            }
//            sendRematchRequest()
            rematchGameClean()
            runGamePreparations(_gameMode.value)
            _doesOpponentWantARematch.update { false }
        } else {
            rematch()
        }
    }

    //region multiplayer
    fun startMultiplayerGame(opponentName: String, playerId: Int, opponentId: Int, firstOf: Int) {
        setParticipantData(
            opponentName = opponentName,
            playerId = playerId,
            opponentId = opponentId
        )

        _roundCount.update { firstOf }

        runGamePreparations(GameMode.Multiplayer)
    }

    fun sendRematchRequest() {
        MultiplayerHandler.sendRematch(_code.value!!)
    }

    fun createLobby(bestOf: Int) {
        _roundCount.update { bestOf }
        sendCreateGame(name = Preferences.playerName!!, bestOf = bestOf)
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
        setParticipantData(opponentName = getRandomName())

        _roundCount.update { 3 }

        runGamePreparations(gameMode)

        if (gameMode.isVsAI() || gameMode.isRoboRumble())
            gameAI = GameAI()

        tossACoin()
    }

    fun rematch() {
        rematchGameClean()

        runGamePreparations(_gameMode.value)

        if (_gameMode.value.isVsAI() || _gameMode.value.isRoboRumble()) {
            gameAI = GameAI()
        }

        tossACoin()
    }

    fun endRound(gameResult: GameResult) {
        log("Ended round")

        _roundResults.update {
             _roundResults.value
                .toMutableList()
                .apply { add(gameResult) }
                .toList()
        }

        val resultCount = _roundResults.value.toResultTypeCountModel()

        _hasGameEnded.update {
            resultCount.draws + resultCount.wins >= _roundCount.value
                    || resultCount.draws + resultCount.losses >= _roundCount.value
        }

        if (_hasGameEnded.value) {
            endGame()
        } else {
            _isRoundTimeout.update { true }
        }
    }

    private fun endGame() {
        log("Ended game")

        val resultCount = _roundResults.value.toResultTypeCountModel()

        val gameResult =
            if (resultCount.wins == resultCount.losses) GameResult.Draw
            else if (resultCount.wins > resultCount.losses) GameResult.Victory
            else GameResult.Loss

        val victorName = when (gameResult) {
            GameResult.Victory -> _playerData.value!!.name
            GameResult.Draw -> "DRAW"
            GameResult.Loss -> _opponentData.value!!.name
        }

        val victorIcon = when (gameResult) {
                GameResult.Victory -> _playerData.value!!.icon
                GameResult.Draw -> Res.drawable.ic_equals
                GameResult.Loss -> _opponentData.value!!.icon
        }

        _gameResult.update {
            GameResultModel(
                name = victorName,
                icon = victorIcon,
                result = gameResult
            )
        }
    }

    fun proceedToTheNextRound() {
        log("Proceeding to next round")

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

    fun loadGame(gameMode: GameMode) {
        log("Loading game...")

        val loadedGame =
            if (gameMode.isVsAI())
                Preferences.vsAI!!.toObject<GameDBModel>()
            else
                Preferences.hotSeat!!.toObject<GameDBModel>()

        setParticipantData(opponentName = loadedGame.participants.opponentName)

        _roundCount.update { loadedGame.roundCount }

        _gameMode.update { loadedGame.gameMode }

        _tableData.update { loadedGame.table.toSegmentUIModelList() }

        _roundResults.update { loadedGame.roundHistory }

        if (_gameMode.value.isVsAI()) gameAI = GameAI()

        setPlayerTurn(loadedGame.isPlayerTurn)
    }

    fun saveGame() {
        log("Saving game...")

        if (_gameMode.value.isMultiplayer()) {
            MultiplayerHandler.sendGameLeave(_code.value!!)

            if (!hasGameEnded.value) {
                fullGameClean()
                return
            }
        }

        if (_hasGameEnded.value) {
            Preferences.addGameToHistory(GameResultDBModel(
                playerName = _playerData.value!!.name,
                opponentName = _opponentData.value!!.name,
                gameMode = _gameMode.value,
                rounds = _roundResults.value
            ))
        } else {
            saveUnfinishedGame()
        }

        fullGameClean()
    }

    private fun saveUnfinishedGame() {
        val dataJSON = GameDBModel(
            participants = ParticipantDBModel(
                opponentName = _opponentData.value!!.name,
                playerName = _playerData.value!!.name
            ),
            table = _tableData.value.toSegmentDBModelList(),
            isPlayerTurn = _isPlayerTurn.value,
            gameMode = _gameMode.value,
            roundHistory = _roundResults.value,
            roundCount = _roundCount.value
        ).toJson()

        if (_gameMode.value.isVsAI())
            Preferences.vsAI = dataJSON
        else
            Preferences.hotSeat = dataJSON
    }

    private fun rematchGameClean() {
        _gameResult.update { null }
        _roundResults.update { emptyList() }
        _hasGameEnded.update { false }
    }

    private fun fullGameClean() {
        rematchGameClean()
        gameAI = null
        _code.update { null }
        _tableData.update { emptyList() }
        _gameMode.update { GameMode.None }
        _playerData.update { null }
        _opponentData.update { null }
        _roundCount.update { 0 }
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

    fun isThereASavedGame(gameMode: GameMode) = Preferences.isThereASavedGame(gameMode)

    private fun setPlayerTurn(value: Boolean) {
        _isPlayerTurn.update { value }

        // triggers AI move
        if ((_gameMode.value.isVsAI() && !_isPlayerTurn.value) || _gameMode.value.isRoboRumble())
            _canAIMove.tryEmit(Unit)
    }

    fun switchTurns() = setPlayerTurn(!_isPlayerTurn.value)

    private fun tossACoin() {
        log("Tossed a coin!")
        return setPlayerTurn((0..1).random() == 0)
    }

    private fun makeNewTable() {
        _tableData.update {
            TicTacToeManager.createTable(true)
        }
    }
    //endregion
}