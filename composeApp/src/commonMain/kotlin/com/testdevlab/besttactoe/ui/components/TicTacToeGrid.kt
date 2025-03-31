package com.testdevlab.besttactoe.ui.components

enum class PieceStates {
    Enemy,
    Player,
    None
}

class TicTacToeGrid {
    private val _ticTacToeValues: Array<PieceStates> = generateTTTGrid()
    val ticTacToeValues: Array<PieceStates>
        get() = _ticTacToeValues


    fun setValueAt(index: Int, value: PieceStates): TicTacToeGrid {
        if (isTileUnclaimed(index)) _ticTacToeValues[index] = value
        return this
    }

    fun checkForVictoryLine(): Boolean {
        return isDiagonalVictory() || isHorizontalVictory() || isVerticalVictory()
    }

    private fun generateTTTGrid(): Array<PieceStates> {
        return Array(9) { PieceStates.None }
    }

    private fun isTileUnclaimed(index: Int): Boolean = _ticTacToeValues[index] == PieceStates.None

    private fun isDiagonalVictory(): Boolean {
        // 2 diagonal checks \ /
        return (_ticTacToeValues[0] == _ticTacToeValues[4]
                && _ticTacToeValues[0] == _ticTacToeValues[8]
                ||
                _ticTacToeValues[2] == _ticTacToeValues[4]
                && _ticTacToeValues[2] == _ticTacToeValues[6])
    }

    private fun isHorizontalVictory(): Boolean {
        // 3 horizontal checks =-
        var rowsFirstItemIndex: Int

        for (row in 0..2) {
            // 0 1 2
            // 3 4 5
            // 6 7 8
            rowsFirstItemIndex = row * 3
            if (
                _ticTacToeValues[row] == _ticTacToeValues[rowsFirstItemIndex + 1]
                &&
                _ticTacToeValues[rowsFirstItemIndex] == _ticTacToeValues[rowsFirstItemIndex + 2]
            ) {
                return true
            }
        }

        return false
    }

    private fun isVerticalVictory(): Boolean {
        // 3 vertical checks |||
        for (column in 0..2) {
            // 0 1 2
            // 3 4 5
            // 6 7 8

            if (
                _ticTacToeValues[column] == _ticTacToeValues[column + 3]
                &&
                _ticTacToeValues[column] == _ticTacToeValues[column + 6]
            ) {
                return true
            }
        }

        return false
    }
}


