package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.SetPieceValueModel
import com.testdevlab.besttactoe.ui.theme.toPieceStateList
import com.testdevlab.besttactoe.AppLogger

enum class PieceStates {
    Enemy,
    Player,
    Draw,
    None
}

object TicTacToeManager {
    fun processMove(
        pieceValueModel: SetPieceValueModel,
        table: List<SegmentUIModel>
    ): List<SegmentUIModel> {
        return table
            .setPieceValueAt(pieceValueModel)
            .setSegmentWinner(
                victor = pieceValueModel.newValue,
                segmentIndex = pieceValueModel.segmentIndex,
            )
            .activateDeactivateSegments(
                currentSegmentIndex = pieceValueModel.segmentIndex,
                pieceIndex = pieceValueModel.pieceIndex
            )
    }

    private fun List<SegmentUIModel>.setPieceValueAt(
        pieceValueModel: SetPieceValueModel,
    ): List<SegmentUIModel> {
        // tile ownership check was made in UI
        return this.map { segment ->
            if (segment.index == pieceValueModel.segmentIndex) {
                segment.copy(
                    pieces = segment.pieces.map { piece ->
                                if (piece.index == pieceValueModel.pieceIndex) {
                                    piece.copy(state = pieceValueModel.newValue)
                                } else {
                                    piece.copy()
                                }
                    }
                )
            } else {
                segment.copy()
            }
        }
    }

    private fun List<SegmentUIModel>.activateDeactivateSegments(
        currentSegmentIndex: Int,
        pieceIndex: Int,
    ): List<SegmentUIModel> {
        // check if next segment is already completed
        return if (this[pieceIndex].state != PieceStates.None) {
            activateAllUnfinishedSegments(this)
        } else {
            moveToNextSegment(currentSegmentIndex, pieceIndex, this)
        }
    }

    //region segment moves
    private fun moveToNextSegment(
        currentSegmentIndex: Int,
        pieceIndex: Int,
        table: List<SegmentUIModel>
    ): List<SegmentUIModel> {
        return table.map { segment ->
            if (currentSegmentIndex == pieceIndex) return table

            when (segment.index) {
                pieceIndex -> segment.copy(isActive = true)
                else -> segment.copy(isActive = false)
            }
        }
    }

    private fun activateAllUnfinishedSegments(table: List<SegmentUIModel>): List<SegmentUIModel> {
        return table.map { segment ->
            if (segment.state == PieceStates.None) {
                segment.copy(isActive = true)
            } else {
                segment.copy()
            }
        }
    }
    //endregion

    private fun List<SegmentUIModel>.setSegmentWinner(
        victor: PieceStates,
        segmentIndex: Int,
    ): List<SegmentUIModel> {
        if (!checkForVictoryLine(this[segmentIndex].pieces.toPieceStateList())) return this

        return this.map { segment ->
            if (segment.index == segmentIndex) {
                segment.copy(
                    state = victor,
                    isActive = false
                )
            } else {
                segment.copy()
            }
        }
    }

    //region victory checks
    private fun checkForVictoryLine(segmentPieces: List<PieceStates>): Boolean {
        return  isDiagonalVictory(segmentPieces) ||
                isHorizontalVictory(segmentPieces) ||
                isVerticalVictory(segmentPieces)
    }

    private fun isDiagonalVictory(segmentPieces: List<PieceStates>): Boolean {
        // 2 diagonal checks \ /
        if (segmentPieces[4] == PieceStates.None) return false
        AppLogger.i("diagonal vic","${(segmentPieces[0] == segmentPieces[4] &&
                segmentPieces[0] == segmentPieces[8] )} or ${(
                segmentPieces[2] == segmentPieces[4] &&
                segmentPieces[2] == segmentPieces[6])}")

        return (segmentPieces[0] == segmentPieces[4] &&
                segmentPieces[0] == segmentPieces[8] ||
                segmentPieces[2] == segmentPieces[4] &&
                segmentPieces[2] == segmentPieces[6])
    }

    private fun isHorizontalVictory(segmentPieces: List<PieceStates>): Boolean {
        // 3 horizontal checks =-
        var rowsFirstItemIndex: Int

        for (row in 0..2) {
            // 0 1 2
            // 3 4 5
            // 6 7 8
            rowsFirstItemIndex = row * 3
            if (
                segmentPieces[rowsFirstItemIndex] != PieceStates.None &&
                segmentPieces[rowsFirstItemIndex] == segmentPieces[rowsFirstItemIndex + 1] &&
                segmentPieces[rowsFirstItemIndex] == segmentPieces[rowsFirstItemIndex + 2]
            ) {
                AppLogger.i("horizontal vic","")
                return true
            }
        }

        return false
    }

    private fun isVerticalVictory(segmentPieces: List<PieceStates>): Boolean {
        // 3 vertical checks |||

        for (column in 0..2) {
            // 0 1 2
            // 3 4 5
            // 6 7 8

            if (
                segmentPieces[column] != PieceStates.None &&
                segmentPieces[column] == segmentPieces[column + 3] &&
                segmentPieces[column] == segmentPieces[column + 6]
            ) {
                AppLogger.i("vertical vic","")
                return true
            }
        }

        return false
    }
    //endregion
}
