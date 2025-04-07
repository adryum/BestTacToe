package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.PieceType
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.SegmentType
import com.testdevlab.besttactoe.ui.SegmentUIModel

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
            victorType = GameHandler.turnHolderSegmentType
        )

        if (segmentResult != SegmentType.None)
            modifiedTable = setSegmentState(
                state = segmentResult,
                table = modifiedTable,
                segmentIndex = moveModel.segmentIndex
            )

        val tableResult = checkTableState(
            table = modifiedTable,
            victorType = GameHandler.turnHolderSegmentType
        )

        if (tableResult != SegmentType.None) {
            // victory code
            GameHandler.endGame(isVictory = tableResult != SegmentType.Draw)
        } else {
            modifiedTable = activateNextSegments(table = modifiedTable, moveModel = moveModel)
            GameHandler.switchTurns()
        }

        return modifiedTable
    }

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
                    state = SegmentType.None,
                    pieces = pieceList.map { it.copy() }
                )
            )
        }

        return list.map { it.copy() }
    }

    private fun activateNextSegments(
        table: List<SegmentUIModel>,
        moveModel: MoveModel
    ) = if (table[moveModel.pieceIndex].isAnythingButNone) {
            activateAllUnfinishedSegments(table)
        } else {
            moveToNextSegment(moveModel.segmentIndex, moveModel.pieceIndex, table)
        }
    }

    private fun checkTableState(
        table: List<SegmentUIModel>,
        victorType: SegmentType
    ): SegmentType {
        if (hasSegmentVictoryLine(table)) {
            return if (victorType == SegmentType.Player) {
                SegmentType.Player
            } else {
                SegmentType.Opponent
            }
        }

        if (isTableDraw(table)) return SegmentType.Draw

        return SegmentType.None
    }

    private fun checkSegmentState(
        table: List<SegmentUIModel>,
        segmentIndex: Int,
        victorType: SegmentType
    ): SegmentType {
        if (hasVictoryLine(table[segmentIndex].pieces)) {
            return if (victorType == SegmentType.Player) {
                SegmentType.Player
            } else {
                SegmentType.Opponent
            }
        }

        if (isSegmentDraw(table[segmentIndex].pieces)) return SegmentType.Draw

        return SegmentType.None
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
        state: SegmentType,
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

    private fun isSegmentDraw(pieces: List<PiecesUIModel>): Boolean {
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
    private fun hasVictoryLine(pieceList: List<PiecesUIModel>) =
        isDiagonalVictory(pieceList) ||
        isHorizontalVictory(pieceList) ||
        isVerticalVictory(pieceList)

    private fun isDiagonalVictory(pieces: List<PiecesUIModel>): Boolean {
        // 2 diagonal checks \ /
        if (pieces[4].isEmpty) return false

        return pieces[0].state == pieces[4].state &&
                pieces[0].state == pieces[8].state ||
                pieces[2].state == pieces[4].state &&
                pieces[2].state == pieces[6].state
    }

    private fun isHorizontalVictory(pieces: List<PiecesUIModel>): Boolean {
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

    private fun isVerticalVictory(segmentPieces: List<PiecesUIModel>): Boolean {
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
