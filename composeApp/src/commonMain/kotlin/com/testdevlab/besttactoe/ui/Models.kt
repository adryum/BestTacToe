package com.testdevlab.besttactoe.ui

import com.testdevlab.besttactoe.core.repositories.PieceType
import org.jetbrains.compose.resources.DrawableResource

data class ScoreModel(val playerScore: Int, val enemyScore: Int)

sealed class Tile(
    open val state: PieceType
) {
    val isOpponents get() = state == PieceType.Opponent
    val isEmpty get() = state == PieceType.Empty
    val isPlayers get() = state == PieceType.Player
    val isAnythingButEmpty get() = state != PieceType.Empty
}

data class SegmentUIModel(
    val index: Int,
    val isActive: Boolean,
    val pieces: List<PiecesUIModel>,
    override val state: PieceType
): Tile(state)

data class PiecesUIModel(
    val index: Int,
    override val state: PieceType
): Tile(state)

data class OpponentUIModel(
    val name: String,
    val icon: DrawableResource
)

data class PlayerUIModel(
    val name: String,
    val icon: DrawableResource,
    val hasTurn: Boolean
)

data class MoveModel(
    val segmentIndex: Int,
    val pieceIndex: Int,
)

//data class CornerRadius(
//    val topLeft: Dp,
//    val topRight: Dp,
//    val bottomRight: Dp,
//    val bottomLeft: Dp,
//)

data class CornerRadius(
    val topLeft: Int,
    val topRight: Int,
    val bottomRight: Int,
    val bottomLeft: Int,
)