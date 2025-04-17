package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.ui.GameResult
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.Piece
import com.testdevlab.besttactoe.ui.PieceUIModel
import com.testdevlab.besttactoe.ui.Segment
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.theme.isPlayer

object TicTacToeManager {
    fun processMove(
        moveModel: MoveModel,
        table: List<SegmentUIModel>
    ): List<SegmentUIModel> {
        // set piece
        // check segment
        // set segment
        // check table
        // result
        var modifiedTable = table

        modifiedTable = setPieceValueAt(table = modifiedTable, moveModel = moveModel)

        val segmentResult = checkSegmentState(
            table = modifiedTable,
            segmentIndex = moveModel.segmentIndex,
            victor = GameHandler.turnHolderSegmentType
        )

        if (segmentResult != Segment.None)
            modifiedTable = setSegmentState(
                state = segmentResult,
                table = modifiedTable,
                segmentIndex = moveModel.segmentIndex
            )

        val tableResult = checkTableState(
            table = modifiedTable,
            victor = GameHandler.turnHolderSegmentType
        )

        if (tableResult != Segment.None) {
            // victory code
            val gameResult = when (tableResult) {
                Segment.Opponent -> GameResult.Loss
                Segment.Player -> GameResult.Victory
                Segment.Draw -> GameResult.Draw
                Segment.None -> null
            }

            GameHandler.endRound(gameResult = gameResult!!)
        } else {
            modifiedTable = activateNextSegments(table = modifiedTable, moveModel = moveModel)
            GameHandler.switchTurns()
        }

        return modifiedTable
    }

    fun createTable(enableAllSegments: Boolean = false): List<SegmentUIModel> {
        val segmentList = mutableListOf<SegmentUIModel>()
        val pieceList = mutableListOf<PieceUIModel>()

        // create piece list
        for (i in 0..<9) {
            pieceList.add(
                i,
                PieceUIModel(
                    index = i,
                    state = Piece.Empty,
                )
            )
        }

        // create segment list aka table
        for (i in 0..<9) {
            segmentList.add(
                i,
                SegmentUIModel(
                    index = i,
                    isActive = enableAllSegments,
                    state = Segment.None,
                    pieces = pieceList.map { it.copy() }
                )
            )
        }

        return segmentList.map { it.copy() }
    }

    private fun activateNextSegments(table: List<SegmentUIModel>, moveModel: MoveModel) =
        if (table[moveModel.pieceIndex].isAnythingButNone) {
            activateAllUnfinishedSegments(table = table)
        } else {
            moveToNextSegment(pieceIndex = moveModel.pieceIndex, table = table)
        }

    private fun checkTableState(
        table: List<SegmentUIModel>,
        victor: Segment
    ): Segment {
        if (hasSegmentVictoryLine(table)) {
            return if (victor.isPlayer()) {
                Segment.Player
            } else {
                Segment.Opponent
            }
        }

        if (isTableDraw(table)) return Segment.Draw

        return Segment.None
    }

    private fun checkSegmentState(
        table: List<SegmentUIModel>,
        segmentIndex: Int,
        victor: Segment
    ): Segment {
        if (hasVictoryLine(table[segmentIndex].pieces)) {
            return if (victor.isPlayer()) {
                Segment.Player
            } else {
                Segment.Opponent
            }
        }

        if (isSegmentDraw(table[segmentIndex].pieces)) return Segment.Draw

        return Segment.None
    }

    private fun setPieceValueAt(table: List<SegmentUIModel>, moveModel: MoveModel) =
        table.map { segment ->
            if (segment.index != moveModel.segmentIndex) {
                segment.copy()
            } else {
                segment.copy(pieces = segment.pieces.map { piece ->
                    if (piece.index == moveModel.pieceIndex) {
                        piece.copy(state = GameHandler.turnHolderPieceType)
                    } else {
                        piece.copy()
                    }
                })
            }
        }

    private fun moveToNextSegment(
        pieceIndex: Int,
        table: List<SegmentUIModel>
    ): List<SegmentUIModel> {
        return table.map { segment ->
            when (segment.index) {
                pieceIndex -> segment.copy(isActive = true)
                else -> segment.copy(isActive = false)
            }
        }
    }

    private fun activateAllUnfinishedSegments(table: List<SegmentUIModel>) =
        table.map { segment ->
            if (segment.isNone) {
                segment.copy(isActive = true)
            } else {
                segment.copy()
            }
        }

    private fun setSegmentState(
        deactivateProvidedSegment: Boolean = true,
        segmentIndex: Int,
        state: Segment,
        table: List<SegmentUIModel>
    ) = table.map { segment ->
        if (segment.index != segmentIndex) {
            segment.copy()
        } else {
            segment.copy(
                state = state,
                isActive = !deactivateProvidedSegment
            )
        }
    }

    private fun isTableDraw(table: List<SegmentUIModel>): Boolean {
        for (segment in table) {
            if (segment.isNone) return false
        }
        return true
    }

    private fun isSegmentDraw(pieces: List<PieceUIModel>): Boolean {
        for (piece in pieces) {
            if (piece.isEmpty) return false
        }
        return true
    }

    //region segment victory checks
    private fun hasSegmentVictoryLine(table: List<SegmentUIModel>) =
        isDiagonalSegmentVictory(table) ||
                isHorizontalSegmentVictory(table) ||
                isVerticalSegmentVictory(table)

    private fun isDiagonalSegmentVictory(table: List<SegmentUIModel>): Boolean {
        // 2 diagonal checks \ /
        if (table[4].isNone) return false

        return table[0].state == table[4].state &&
                table[0].state == table[8].state ||
                table[2].state == table[4].state &&
                table[2].state == table[6].state
    }

    private fun isHorizontalSegmentVictory(table: List<SegmentUIModel>): Boolean {
        // 3 horizontal checks =-
        var rowsFirstItemIndex: Int

        for (row in 0..2) {
            // 0 1 2
            // 3 4 5
            // 6 7 8
            rowsFirstItemIndex = row * 3
            if (
                table[rowsFirstItemIndex].isAnythingButNone &&
                table[rowsFirstItemIndex].state == table[rowsFirstItemIndex + 1].state &&
                table[rowsFirstItemIndex].state == table[rowsFirstItemIndex + 2].state
            ) {
                println("Horizontal victory")

                return true
            }
        }

        return false
    }

    private fun isVerticalSegmentVictory(table: List<SegmentUIModel>): Boolean {
        // 3 vertical checks |||
        for (column in 0..2) {
            // 0 1 2
            // 3 4 5
            // 6 7 8

            if (
                table[column].isAnythingButNone &&
                table[column].state == table[column + 3].state &&
                table[column].state == table[column + 6].state
            ) {
                println("Vertical victory")
                return true
            }
        }

        return false
    }
    //endregion

    //region piece victory checks
    private fun hasVictoryLine(pieceList: List<PieceUIModel>) =
        isDiagonalVictory(pieceList) ||
                isHorizontalVictory(pieceList) ||
                isVerticalVictory(pieceList)

    private fun isDiagonalVictory(pieces: List<PieceUIModel>): Boolean {
        // 2 diagonal checks \ /
        if (pieces[4].isEmpty) return false

        return pieces[0].state == pieces[4].state &&
                pieces[0].state == pieces[8].state ||
                pieces[2].state == pieces[4].state &&
                pieces[2].state == pieces[6].state
    }

    private fun isHorizontalVictory(pieces: List<PieceUIModel>): Boolean {
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

    private fun isVerticalVictory(segmentPieces: List<PieceUIModel>): Boolean {
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