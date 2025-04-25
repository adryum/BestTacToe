package com.testdevlab.besttactoe.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.testdevlab.besttactoe.core.repositories.GameMode
import org.jetbrains.compose.resources.DrawableResource

data class SquareSides(
    val topLeft: Int = 0,
    val topRight: Int = 0,
    val bottomLeft: Int = 0,
    val bottomRight: Int = 0
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

data class ParticipantUIModel(
    val name: String,
    val icon: DrawableResource,
    val tint: Color,
    val id: Int
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

data class PopUpModel(
    val title: String,
    val description: String,
    val buttonOneText: String,
    val buttonTwoText: String,
    val onActionOne: () -> Unit,
    val onActionTwo: () -> Unit,
)

data class IconUIModel(
    val res: DrawableResource,
    val tint: Color,
)

data class ResultTypeCountModel(
    val wins: Int,
    val draws: Int,
    val losses: Int
)

data class GameEndedAnimationModel(
    val name: String,
    val onRematch: () -> Unit,
    val onLeave: () -> Unit
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
