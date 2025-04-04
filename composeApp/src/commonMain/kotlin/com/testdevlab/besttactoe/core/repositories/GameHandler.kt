package com.testdevlab.besttactoe.core.repositories

import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_circle
import besttactoe.composeapp.generated.resources.ic_thin_cross
import com.testdevlab.besttactoe.core.common.launchDefault
import com.testdevlab.besttactoe.ui.GameEndModel
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.OpponentUIModel
import com.testdevlab.besttactoe.ui.PlayerUIModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class GameMode {
    VS_AI,
    HotSeat,
    Multiplayer
}

object GameHandler {
    private val _tableData = MutableStateFlow<List<SegmentUIModel>>(emptyList())
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
    private val _gameResult = MutableStateFlow<GameEndModel?>(null)
    private var gameAI: GameAI? = null
    private val _canAIMove = MutableStateFlow<Boolean?>(null)
    private val _gameMode = MutableStateFlow<GameMode?>(null)
    private val _gameScore = MutableStateFlow<ScoreModel?>(null)
    private val gameEnded get() = _gameResult.value != null
    val gameResult = _gameResult.asStateFlow()
    val gameMode = _gameMode.asStateFlow()
    val gameScore = _gameScore.asStateFlow()
    val playerData = _playerData.asStateFlow()
    val opponentData = _opponentData.asStateFlow()
    val tableData = _tableData.asStateFlow()
    val turnPieceType get() = if (_canAIMove.value == true) PieceType.Player else PieceType.Opponent
    val isPlayerTurn get() = _playerData.value.hasTurn

    init {
        // for AI
        launchDefault {
            _canAIMove.collect {
                if (
                    _gameMode.value == GameMode.VS_AI &&
                    _canAIMove.value != true &&
                    !gameEnded
                ) {
                    println("robo move")
                    delay(700)
                    gameAI?.makeAMove(_tableData.value)
                }
            }
        }
    }

    fun playAgain() {
        startGame(
            gameMode = _gameMode.value!!,
            clearScore = false
        )
    }

    fun endGame(isVictory: Boolean) {
        _gameResult.update {
            if (isVictory) {
                GameEndModel(
                    name = if (isPlayerTurn) _playerData.value.name else _opponentData.value.name,
                    icon = if (isPlayerTurn) _playerData.value.icon else _opponentData.value.icon,
                    isVictory = isVictory
                )
            } else {
                GameEndModel(
                    name = null,
                    icon = null,
                    isVictory = isVictory
                )
            }
        }

        _gameScore.update {
            if (isVictory) {
                _gameScore.value?.copy(
                    playerScore = if (isPlayerTurn)
                        _gameScore.value!!.playerScore + 1 else _gameScore.value!!.playerScore,
                    opponentScore = if (!isPlayerTurn)
                        _gameScore.value!!.opponentScore + 1 else _gameScore.value!!.opponentScore,
                )
            } else {
                _gameScore.value?.copy()!!
            }
        }
    }

    private fun makeNewGameScore(player: Int = 0, opponent: Int = 0) {
        _gameScore.update {
            ScoreModel(
                opponentScore = opponent,
                playerScore = player
            )
        }
    }

    fun startGame(gameMode: GameMode, clearScore: Boolean) {
        if (clearScore) makeNewGameScore()
        when (gameMode) {
            GameMode.VS_AI -> {
                _gameMode.update { GameMode.VS_AI }
                makeTableData()
                gameAI = GameAI()
                tossACoin()
            }
            GameMode.HotSeat -> {
                _gameMode.update { GameMode.HotSeat }
                makeTableData()
                tossACoin()
            }
            GameMode.Multiplayer -> {
                _gameMode.update { GameMode.Multiplayer }

            }
        }
    }

    private fun setPlayerTurn(value: Boolean) {
        _playerData.update { _playerData.value.copy(hasTurn = value) }
        // triggers AI move
        _canAIMove.update { _playerData.value.hasTurn }
    }
    private fun switchTurns() = setPlayerTurn(!_playerData.value.hasTurn)

    private fun tossACoin() = setPlayerTurn((0..1).random() == 0)

    fun clearGame() {
        gameAI = null
        _gameResult.update { null }
        _gameMode.update { null }
        _gameScore.update { null }
    }

    private fun makeTableData() {
        _tableData.update {
            TicTacToeManager.createTable(true)
        }
    }

    fun makeAMove(pieceData: MoveModel) {
        _tableData.update {
            TicTacToeManager.processMove(pieceData, _tableData.value.map { it.copy() })
        }
        switchTurns()
    }

    fun startMultiplayerGame() {

    }
}