package com.testdevlab.besttactoe.core.repositories

import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_circle
import besttactoe.composeapp.generated.resources.ic_thin_cross
import com.testdevlab.besttactoe.core.common.launchDefault
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.OpponentUIModel
import com.testdevlab.besttactoe.ui.PlayerUIModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
    private val _gameWinner = MutableStateFlow<OpponentUIModel?>(null)
    private var gameAI: GameAI? = null
    private val _canAIMove = MutableStateFlow<Boolean?>(null)
    private val gameEnded get() = _gameWinner.value != null
    val gameWinner = _gameWinner.asStateFlow()
    val playerData = _playerData.asStateFlow()
    val opponentData = _opponentData.asStateFlow()
    val tableData = _tableData.asStateFlow()
    val turnPieceType get() = if (_canAIMove.value == true) PieceType.Player else PieceType.Opponent

    init {
        // for AI
        launchDefault {
            _canAIMove.collect {
                println("is player turn $it")
                if (_canAIMove.value != true && !gameEnded) {
                    println("robo move")
                    delay(700)
                    gameAI?.makeAMove(_tableData.value)
                }
            }
        }
    }

    fun triggerVictory() {
        // winner turnPieceType
        _gameWinner.update {
            if (_canAIMove.value == true) {
                OpponentUIModel(
                    name = _playerData.value.name,
                    icon = _playerData.value.icon
                )
            } else {
                OpponentUIModel(
                    name = _opponentData.value.name,
                    icon = _opponentData.value.icon
                )
            }
        }
    }

    fun startGame(vsAI: Boolean) {
        if (vsAI) {
            makeTableData()
            gameAI = GameAI()
            tossACoin()
        } else {
            ///
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
        _gameWinner.update { null }
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