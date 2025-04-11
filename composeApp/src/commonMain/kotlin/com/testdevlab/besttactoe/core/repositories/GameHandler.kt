package com.testdevlab.besttactoe.core.repositories

import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_circle
import besttactoe.composeapp.generated.resources.ic_equals
import besttactoe.composeapp.generated.resources.ic_thin_cross
import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.cache.models.GameDBModel
import com.testdevlab.besttactoe.core.cache.models.GameResultDBModel
import com.testdevlab.besttactoe.core.cache.models.ScoreDBModel
import com.testdevlab.besttactoe.core.cache.toJson
import com.testdevlab.besttactoe.core.cache.toObject
import com.testdevlab.besttactoe.core.common.launchDefault
import com.testdevlab.besttactoe.ui.GameResult
import com.testdevlab.besttactoe.ui.GameResultModel
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.OpponentUIModel
import com.testdevlab.besttactoe.ui.Piece
import com.testdevlab.besttactoe.ui.PlayerUIModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.Segment
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.theme.isLoss
import com.testdevlab.besttactoe.ui.theme.isMultiplayer
import com.testdevlab.besttactoe.ui.theme.isRoboRumble
import com.testdevlab.besttactoe.ui.theme.isVictory
import com.testdevlab.besttactoe.ui.theme.isVsAI
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
    private val _playerData = MutableStateFlow<PlayerUIModel>(
        PlayerUIModel(
            name = "Arbuz",
            icon = Res.drawable.ic_thin_cross,
            hasTurn = false
        )
    )
    private val _opponentData = MutableStateFlow<OpponentUIModel>(
        OpponentUIModel(
            name = "Crossy",
            icon = Res.drawable.ic_circle
        )
    )
    private val _tableData = MutableStateFlow<List<SegmentUIModel>>(emptyList())
    private val _gameMode = MutableStateFlow(GameMode.None)
    private val _score = MutableStateFlow<ScoreModel?>(null)
    private val _gameResult = MutableStateFlow<GameResultModel?>(null)
    private val _matchResults = MutableStateFlow<List<GameResult>>(emptyList())
    private val _hasGameEnded = MutableStateFlow(false)
    private val _isRoundTimeout = MutableStateFlow(false)
    private val _canAIMove = MutableSharedFlow<Unit>(1)
    private val _roundCount = 5
    private val _isPlayerTurn get() = _playerData.value.hasTurn
    val gameResult = _gameResult.asStateFlow()
    val hasGameEnded = _hasGameEnded.asStateFlow()
    val isRoundTimeout = _isRoundTimeout.asStateFlow()
    val gameMode = _gameMode.asStateFlow()
    val score = _score.asStateFlow()
    val playerData = _playerData.asStateFlow()
    val opponentData = _opponentData.asStateFlow()
    val tableData = _tableData.asStateFlow()
    val turnHolderPieceType get() = if (_isPlayerTurn) Piece.Player else Piece.Opponent
    val turnHolderSegmentType get() = if (_isPlayerTurn) Segment.Player else Segment.Opponent

    init {
        // for AI
        launchDefault {
            _canAIMove.collect {
                if (
                    (_gameMode.value.isVsAI() && !_isPlayerTurn && !_isRoundTimeout.value) ||
                    (_gameMode.value.isRoboRumble() && !_isRoundTimeout.value)
                ) {
                    delay(if (_gameMode.value.isRoboRumble()) 10 else  700)
                    gameAI?.makeAMove(_tableData.value)
                }
            }
        }
    }

    fun startGame(gameMode: GameMode) { println("game started")
        _gameMode.update { gameMode }
        makeTableData()
        makeNewGameScore()

        when (gameMode) {
            GameMode.RoboRumble,
            GameMode.VS_AI -> {
                gameAI = GameAI()
                tossACoin()
            }
            GameMode.HotSeat -> {
                tossACoin()
            }
            GameMode.Multiplayer -> {}
            GameMode.None -> {}
        }
    }

    fun rematch() { println("rematch clicked")
        _isRoundTimeout.update { false }
        _gameResult.update { null }
        _matchResults.update { emptyList() }
        _hasGameEnded.update { false }

        val gameMode = _gameMode.value

        if (gameMode.isMultiplayer()) {
            // multiplayer code

        } else {
            makeTableData()
            makeNewGameScore()

            if (gameMode.isVsAI() || gameMode.isRoboRumble()) {
                gameAI = GameAI()
            }

            tossACoin()
        }
    }

    fun endMatch(gameResult: GameResult) { println("match ended")
        _matchResults.update {
             _matchResults.value
                .toMutableList()
                .apply { add(gameResult) }
                .toList()
        }

        addScore(
            player = if (gameResult.isVictory()) 1 else 0,
            opponent = if (gameResult.isLoss()) 1 else 0
        )

        val hasGameEnded = _matchResults.value.size >= _roundCount

        if (hasGameEnded) {
            endGame()
        } else {
            _isRoundTimeout.update { true }
        }
    }

    private fun endGame() { println("game ended")
        var playerPoints = 0
        var opponentPoints = 0

        for (match in _matchResults.value) {
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
                    GameResult.Victory -> _playerData.value.name
                    GameResult.Draw -> "DRAW"
                    GameResult.Loss -> _opponentData.value.name
                },
                icon = when (gameResult) {
                    GameResult.Victory -> _playerData.value.icon
                    GameResult.Draw -> Res.drawable.ic_equals
                    GameResult.Loss -> _opponentData.value.icon
                },
                result = gameResult
            )
        }

        _hasGameEnded.update { true }
    }

    fun proceedToTheNextMatch() {
        makeTableData()
        if (_gameMode.value.isVsAI() || _gameMode.value.isRoboRumble()) {
            gameAI = GameAI()
            tossACoin()
        }
        _isRoundTimeout.update { false }
    }

    private fun saveUnfinishedGame() {
        val dataJSON = GameDBModel(
            score = ScoreDBModel(
                playerScore = _score.value?.playerScore!!,
                opponentScore = _score.value?.opponentScore!!,
                opponentName = _opponentData.value.name,
                playerName = _playerData.value.name
            ),
            table = _tableData.value.toSegmentDBModelList(),
            isPlayerTurn = _isPlayerTurn,
            gameMode = _gameMode.value,
            matchHistory = _matchResults.value
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

        _gameMode.update { loadedGame.gameMode }

        _score.update {
            ScoreModel(
                playerScore = loadedGame.score.playerScore,
                opponentScore = loadedGame.score.opponentScore
            )
        }

        _tableData.update { loadedGame.table.toSegmentUIModelList() }

        _matchResults.update { loadedGame.matchHistory }

        if (_gameMode.value.isVsAI()) gameAI = GameAI()

        setPlayerTurn(loadedGame.isPlayerTurn)
    }

    fun makeAMove(pieceData: MoveModel) {
        _tableData.update {
            TicTacToeManager.processMove(pieceData, _tableData.value.map { it.copy() })
        }
    }

    fun isThereASavedGame(gameMode: GameMode) = Preferences.isThereASavedGame(gameMode)

    fun saveAndClearGame() {
        if (_hasGameEnded.value) {
            Preferences.addGameToHistory(GameResultDBModel(
                playerName = _playerData.value.name,
                opponentName = _opponentData.value.name,
                gameMode = _gameMode.value,
                matches = _matchResults.value
            ))
        } else {
            saveUnfinishedGame()
        }

        clearGameData()
    }

    private fun clearGameData() { println("clear data called")
        gameAI = null
        _tableData.update { emptyList() }
        _gameMode.update { GameMode.None }
        _score.update { null }
        _matchResults.update { emptyList() }
        _hasGameEnded.update { false }
        _gameResult.update { null }
        _isRoundTimeout.update { false }
    }

    //region other
    private fun addScore(player: Int = 0, opponent: Int = 0) {
        _score.update {
            it?.copy(
                playerScore = it.playerScore + player,
                opponentScore = it.opponentScore + opponent
            )
        }
    }

    private fun setPlayerTurn(value: Boolean) {
        _playerData.update { _playerData.value.copy(hasTurn = value) }

        // triggers AI move
        if ((_gameMode.value.isVsAI() && !_isPlayerTurn) || _gameMode.value.isRoboRumble())
            _canAIMove.tryEmit(Unit)
    }

    fun switchTurns() = setPlayerTurn(!_isPlayerTurn)

    private fun tossACoin() = setPlayerTurn((0..1).random() == 0)

    private fun makeTableData() {
        _tableData.update {
            TicTacToeManager.createTable(true)
        }
    }

    private fun makeNewGameScore(player: Int = 0, opponent: Int = 0) {
        _score.update {
            ScoreModel(
                opponentScore = opponent,
                playerScore = player
            )
        }
    }
    //endregion
}