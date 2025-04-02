package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.ui.SetPieceValueModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.testdevlab.besttactoe.ui.PlayerUIModel
import com.testdevlab.besttactoe.ui.UserUIModel
import com.testdevlab.besttactoe.AppLogger

object GameHandler {
    private val _tableData = MutableStateFlow<List<SegmentUIModel>>(emptyList())
    private val _playerData = MutableStateFlow(
        listOf(
            PlayerUIModel(
                name = "Arbuz",
                pieceType = PieceStates.Player,
                hasTurn = false
            ),
            PlayerUIModel(
                name = "Abhdul",
                pieceType = PieceStates.Enemy,
                hasTurn = true
            )
        )
    )
    private val _userData = MutableStateFlow(UserUIModel(name = "Abhdul"))
    private val isSinglePlayer = MutableStateFlow(false)
    val userData = _userData.asStateFlow()
    val playerData = _playerData.asStateFlow()
    val tableData = _tableData.asStateFlow()

    fun startGame(vsAI: Boolean) {
        if (vsAI) {
            isSinglePlayer.update { true }
            makeTableData()
        } else {
            isSinglePlayer.update { false }

        }
    }

    private fun makeTableData() {
        _tableData.update {
            AppLogger.i("in update", "")
            TicTacToeManager.createTable(true)
        }
    }

    fun makeAMove(pieceData: SetPieceValueModel) {
        _tableData.update {
            TicTacToeManager.processMove(pieceData, _tableData.value.map { it.copy() })
        }
    }

    fun startMultiplayerGame() {

    }

}