package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.AppLogger
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.Tile

enum class PieceType {
    Opponent,
    Player,
    Draw,
    Empty
}

object TicTacToeManager {
    fun createTable(enableAllSegments: Boolean = false): List<SegmentUIModel> {
        val list = mutableListOf<SegmentUIModel>()
        val pieceList = mutableListOf<PiecesUIModel>()

        // create piece list
        for (i in 0 ..< 9) {
            pieceList.add(
                i,
                PiecesUIModel(
                    index = i,
                    state = PieceType.Empty,
                )
            )
        }

        // create segment list aka table
        for (i in 0 ..< 9) {
            list.add(
                i,
                SegmentUIModel(
                    index = i,
                    isActive = enableAllSegments,
                    state = PieceType.Empty,
                    pieces = pieceList.map { it.copy() }
                )
            )
        }

        return list.map { it.copy() }
    }

    fun processMove(
        moveModel: MoveModel,
        table: List<SegmentUIModel>
    ): List<SegmentUIModel> {
        return table
            .setPieceValueAt(moveModel)
            .setSegmentWinner(
                victor = GameHandler.turnPieceType,
                segmentIndex = moveModel.segmentIndex,
            )
            .checkSegmentVictoryLines()
            .activateDeactivateSegments(
                currentSegmentIndex = moveModel.segmentIndex,
                pieceIndex = moveModel.pieceIndex
            )
    }

    private fun List<SegmentUIModel>.checkSegmentVictoryLines(): List<SegmentUIModel> {
        if (hasVictoryLine(this)) {
            // victory code
            GameHandler.triggerVictory()
            AppLogger.i("asd", "viCTORTYYYYY")
        }

        return this
    }

    private fun List<SegmentUIModel>.setPieceValueAt(
        moveModel: MoveModel,
    ): List<SegmentUIModel> {
        // tile ownership check was made in UI
        return this.map { segment ->
            if (segment.index == moveModel.segmentIndex) {
                segment.copy(
                    pieces = segment.pieces.map { piece ->
                        if (piece.index == moveModel.pieceIndex) {
                            piece.copy(state = GameHandler.turnPieceType)
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
        return if (this[pieceIndex].isAnythingButEmpty) {
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
            if (segment.isEmpty) {
                segment.copy(isActive = true)
            } else {
                segment.copy()
            }
        }
    }
    //endregion

    private fun List<SegmentUIModel>.setSegmentWinner(
        victor: PieceType,
        segmentIndex: Int,
    ): List<SegmentUIModel> {
        if (!hasVictoryLine(this[segmentIndex].pieces)) return this

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
    private fun hasVictoryLine(segmentPieces: List<Tile>) =
        isDiagonalVictory(segmentPieces) ||
        isHorizontalVictory(segmentPieces) ||
        isVerticalVictory(segmentPieces)

    private fun isDiagonalVictory(pieces: List<Tile>): Boolean {
        // 2 diagonal checks \ /
        if (pieces[4].isEmpty) return false

        return pieces[0].state == pieces[4].state &&
                pieces[0].state == pieces[8].state ||
                pieces[2].state == pieces[4].state &&
                pieces[2].state == pieces[6].state
    }

    private fun isHorizontalVictory(pieces: List<Tile>): Boolean {
        // 3 horizontal checks =-
        var rowsFirstItemIndex: Int

        for (row in 0..2) {
            // 0 1 2
            // 3 4 5
            // 6 7 8
            rowsFirstItemIndex = row * 3
            if (
                pieces[rowsFirstItemIndex].isAnythingButEmpty &&
                pieces[rowsFirstItemIndex].state == pieces[rowsFirstItemIndex + 1].state &&
                pieces[rowsFirstItemIndex].state == pieces[rowsFirstItemIndex + 2].state
            ) {
                println("Horizontal victory")

                return true
            }
        }

        return false
    }

    private fun isVerticalVictory(segmentPieces: List<Tile>): Boolean {
        // 3 vertical checks |||
        for (column in 0..2) {
            // 0 1 2
            // 3 4 5
            // 6 7 8

            if (
                segmentPieces[column].isAnythingButEmpty &&
                segmentPieces[column].state == segmentPieces[column + 3].state &&
                segmentPieces[column].state == segmentPieces[column + 6].state
            ) {
                println("Vertical victory")
                return true
            }
        }

        return false
    }
    //endregion
}
