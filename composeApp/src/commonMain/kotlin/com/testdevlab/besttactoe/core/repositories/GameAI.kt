package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.SegmentUIModel

class GameAI() {
    fun makeAMove(table: List<SegmentUIModel>) {
        val segmentIndex = choseSegment(table)
        val pieceIndex = chosePiece(table[segmentIndex])

        GameHandler.makeAMove(
            pieceData = MoveModel(
                segmentIndex = segmentIndex,
                pieceIndex = pieceIndex,
            )
        )
    }

    private fun choseSegment(table: List<SegmentUIModel>): Int {
        val possibleSegments = mutableListOf<SegmentUIModel>()
        for (segment in table) {
            if (segment.isActive) possibleSegments.add(segment)
        }
        val randomSegment = (0..< possibleSegments.size).random()

        return possibleSegments[randomSegment].index
    }

    private fun chosePiece(segment: SegmentUIModel): Int {
        val possiblePieces = mutableListOf<PiecesUIModel>()
        for (piece in segment.pieces) {
            if (piece.state == PieceType.Empty) possiblePieces.add(piece)
        }
        val randomPiece = (0..< possiblePieces.size).random()

        return possiblePieces[randomPiece].index
    }
}