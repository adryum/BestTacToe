package com.testdevlab.besttactoe.core.repositories

import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_circle
import besttactoe.composeapp.generated.resources.ic_thin_cross
import com.testdevlab.besttactoe.core.common.launchDefault
import com.testdevlab.besttactoe.ui.GameResultModel
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.OpponentUIModel
import com.testdevlab.besttactoe.ui.PieceType
import com.testdevlab.besttactoe.ui.PlayerUIModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.SegmentType
import com.testdevlab.besttactoe.ui.SegmentUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class GameMode {
    VS_AI,
    HotSeat,
    Multiplayer,
    RoboRumble
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
    private val _gameResult = MutableStateFlow<GameResultModel?>(null)
    private val _canAIMove = MutableStateFlow<Boolean?>(null)
    private val _gameMode = MutableStateFlow<GameMode?>(null)
    private val _score = MutableStateFlow<ScoreModel?>(null)
    private val gameEnded get() = _gameResult.value != null
    private val isPlayerTurn get() = _playerData.value.hasTurn
    val gameResult = _gameResult.asStateFlow()
    val gameMode = _gameMode.asStateFlow()
    val score = _score.asStateFlow()
    val playerData = _playerData.asStateFlow()
    val opponentData = _opponentData.asStateFlow()
    val tableData = _tableData.asStateFlow()
    val turnHolderPieceType get() = if (isPlayerTurn) PieceType.Player else PieceType.Opponent
    val turnHolderSegmentType get() = if (isPlayerTurn) SegmentType.Player else SegmentType.Opponent

    init {
        // for AI
        launchDefault {
            _canAIMove.collect {
                if (
                    (_gameMode.value == GameMode.VS_AI &&
                    _canAIMove.value != true &&
                    !gameEnded) ||
                    (_gameMode.value == GameMode.RoboRumble && !gameEnded)
                ) {
                    println("robo move")
                    delay(if (_gameMode.value == GameMode.RoboRumble) 100 else  700)
                    gameAI?.makeAMove(_tableData.value)
                }
            }
        }
    }

    fun makeAMove(pieceData: MoveModel) {
        println("move made")
        _tableData.update {
            TicTacToeManager.processMove(pieceData, _tableData.value.map { it.copy() })
        }
    }

    fun startGame(gameMode: GameMode) {
        println("game started")
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
            GameMode.Multiplayer -> {

            }
        }
    }

    fun playAgain() {
        println("play again Called")
        if (_gameResult.value?.isVictory == true)
            addScore(
                player = if (isPlayerTurn) 1 else 0,
                opponent = if (isPlayerTurn) 0 else 1
            )
        _gameResult.update { null }

        if (_gameMode.value == GameMode.VS_AI || _gameMode.value == GameMode.RoboRumble) {
            gameAI = GameAI()
            tossACoin()
        }

        makeTableData()
    }

    fun clearGameData() {
        println("clear data called")
        gameAI = null
        _gameResult.update { null }
        _gameMode.update { null }
        _score.update { null }
    }

    fun endGame(isVictory: Boolean) {
        println("end Game called")
        _gameResult.update {
            if (isVictory) {
                GameResultModel(
                    name = if (isPlayerTurn) _playerData.value.name else _opponentData.value.name,
                    icon = if (isPlayerTurn) _playerData.value.icon else _opponentData.value.icon,
                    isVictory = isVictory
                )
            } else {
                GameResultModel(
                    name = null,
                    icon = null,
                    isVictory = isVictory
                )
            }
        }
    }

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
        if (_gameMode.value == GameMode.VS_AI || _gameMode.value == GameMode.RoboRumble)
            _canAIMove.update { _playerData.value.hasTurn }
    }

    fun switchTurns() = setPlayerTurn(!isPlayerTurn)

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
}