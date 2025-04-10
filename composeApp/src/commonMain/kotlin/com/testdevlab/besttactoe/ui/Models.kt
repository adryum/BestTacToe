package com.testdevlab.besttactoe.ui

import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.resources.DrawableResource

data class ScoreModel(
    val playerScore: Int,
    val opponentScore: Int
)

enum class SegmentType {
    Opponent,
    Player,
    None,
    Draw
}

enum class PieceType {
    Opponent,
    Player,
    Empty,
}

data class SegmentUIModel(
    val index: Int,
    val isActive: Boolean,
    val pieces: List<PieceUIModel>,
    val state: SegmentType
) {
    val isOpponents = state == SegmentType.Opponent
    val isNone = state == SegmentType.None
    val isPlayers = state == SegmentType.Player
    val isDraw = state == SegmentType.Draw
    val isAnythingButNone = state != SegmentType.None
}

data class PieceUIModel(
    val index: Int,
    val state: PieceType
) {
    val isOpponents = state == PieceType.Opponent
    val isEmpty = state == PieceType.Empty
    val isPlayers = state == PieceType.Player
    val isAnythingButEmpty = state != PieceType.Empty
}

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

data class TableOuterPadding(
    val tablePadding: Dp,
    val segmentPadding: Dp,
    val piecePadding: Dp
)

data class GameResultModel(
    val name: String?,
    val icon: DrawableResource?,
    val isVictory: Boolean
)

enum class GamesResultType {
    Victory,
    Draw,
    Loss,
}
