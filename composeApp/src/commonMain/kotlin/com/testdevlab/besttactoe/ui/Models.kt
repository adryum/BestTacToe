package com.testdevlab.besttactoe.ui

import androidx.compose.ui.unit.Dp
import com.testdevlab.besttactoe.core.repositories.GameMode
import org.jetbrains.compose.resources.DrawableResource

data class ScoreModel(
    val playerScore: Int,
    val opponentScore: Int
)

data class GameResultUIModel(
    val playerName: String,
    val opponentName: String,
    val gameMode: GameMode,
    val matches: List<GameResult>
)

data class SegmentUIModel(
    val index: Int,
    val isActive: Boolean,
    val pieces: List<PieceUIModel>,
    val state: Segment
) {
    val isOpponents = state == Segment.Opponent
    val isNone = state == Segment.None
    val isPlayers = state == Segment.Player
    val isDraw = state == Segment.Draw
    val isAnythingButNone = state != Segment.None
}

data class PieceUIModel(
    val index: Int,
    val state: Piece
) {
    val isOpponents = state == Piece.Opponent
    val isEmpty = state == Piece.Empty
    val isPlayers = state == Piece.Player
    val isAnythingButEmpty = state != Piece.Empty
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
    val name: String,
    val icon: DrawableResource,
    val result: GameResult
)

enum class Segment {
    Opponent,
    Player,
    None,
    Draw
}

enum class Piece {
    Opponent,
    Player,
    Empty,
}

enum class GameResult {
    Victory,
    Draw,
    Loss,
}
