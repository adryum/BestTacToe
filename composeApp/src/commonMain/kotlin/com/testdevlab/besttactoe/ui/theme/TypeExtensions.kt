package com.testdevlab.besttactoe.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_circle_dashed
import besttactoe.composeapp.generated.resources.ic_cross
import besttactoe.composeapp.generated.resources.ic_robot
import com.testdevlab.besttactoe.core.cache.models.GameResultDBModel
import com.testdevlab.besttactoe.core.cache.models.PieceDBModel
import com.testdevlab.besttactoe.core.cache.models.SegmentDBModel
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.GameResult
import com.testdevlab.besttactoe.ui.GameResultUIModel
import com.testdevlab.besttactoe.ui.Piece
import com.testdevlab.besttactoe.ui.PieceUIModel
import com.testdevlab.besttactoe.ui.ResultTypeCountModel
import com.testdevlab.besttactoe.ui.Segment
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.SquareSides
import com.testdevlab.besttactoe.ui.components.ButtonType
import com.testdevlab.besttactoe.ui.navigation.Views
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

val Int.ldp @Composable get() = with (LocalDensity.current) {
    dp
}

val Float.ldp @Composable get() = with (LocalDensity.current) {
    dp
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

fun Views.showTopBar(): Boolean = when (this) {
    Views.CreateLobbyView,
    Views.JoinLobbyView,
    Views.MultiplayerView,
    Views.SettingsView,
    Views.HistoryView,
    Views.Customization,
    Views.CodeView -> true

    Views.GameView,
    Views.MainView, -> false
}

fun Views.getViewTitle(): String = when (this) {
    Views.MainView -> "Best TacToe"
    Views.CreateLobbyView -> "Create room"
    Views.CodeView -> ""
    Views.Customization -> "Customize!"
    Views.GameView -> ""
    Views.JoinLobbyView -> "Join room"
    Views.MultiplayerView -> "Multiplayer"
    Views.SettingsView -> "Settings"
    Views.HistoryView -> "History"
}

fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * PI
        val x = cos(angleRad).toFloat() //Fractional x
        val y = sin(angleRad).toFloat() //Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)
fun PieceUIModel.toPieceDBModel() = PieceDBModel(
    index = this.index,
    state = this.state
)
fun SegmentUIModel.toSegmentDBModel() = SegmentDBModel(
    index = this.index,
    state = this.state,
    isActive = this.isActive,
    pieces = pieces.map { it.toPieceDBModel() }
)
fun List<SegmentUIModel>.toSegmentDBModelList() = this.map { it.toSegmentDBModel() }

fun PieceDBModel.toPieceUIModel() = PieceUIModel(
    index = this.index,
    state = this.state
)
fun SegmentDBModel.toSegmentUIModel() = SegmentUIModel(
    index = this.index,
    state = this.state,
    isActive = this.isActive,
    pieces = pieces.map { it.toPieceUIModel() }
)
fun List<SegmentDBModel>.toSegmentUIModelList() = this.map { it.toSegmentUIModel() }

fun GameResult.isVictory() = this == GameResult.Victory
fun GameResult.isLoss() = this == GameResult.Loss
fun GameResult.isDraw() = this == GameResult.Draw

fun GameResult.color() = when (this) {
    GameResult.Victory -> Blue
    GameResult.Loss -> Red
    GameResult.Draw -> GrayLight
}

fun GameResult.icon() = when (this) {
    GameResult.Victory -> Res.drawable.ic_cross
    GameResult.Loss -> Res.drawable.ic_circle_dashed
    GameResult.Draw -> Res.drawable.ic_robot
}

fun GameResultDBModel.toGameResultUIModel() = GameResultUIModel(
    playerName, opponentName, gameMode, rounds
)
fun List<GameResultDBModel>.toGameResultUIModelList() = this.map { it.toGameResultUIModel() }

fun GameMode.isVsAI() = this == GameMode.VS_AI
fun GameMode.isMultiplayer() = this == GameMode.Multiplayer
fun GameMode.isHotSeat() = this == GameMode.HotSeat
fun GameMode.isRoboRumble() = this == GameMode.RoboRumble

fun Segment.isPlayer() = this == Segment.Player
fun Segment.isOpponent() = this == Segment.Opponent
fun Segment.isDraw() = this == Segment.Draw
fun Segment.isNone() = this == Segment.None

fun Piece.isPlayer() = this == Piece.Player
fun Piece.isOpponent() = this == Piece.Opponent
fun Piece.isEmpty() = this == Piece.Empty

fun ButtonType.isLeft() = this == ButtonType.LeftSide
fun ButtonType.isCenter() = this == ButtonType.Center
fun ButtonType.isRight() = this == ButtonType.RightSide

fun Int.toColor() = Color(this)

fun Int.toRGBHex() = toColor().toRGBHex()

fun Color.toRGBHex(): String {
    val redHex = (this.red * 255).toInt().toString(16).padStart(2, '0')
    val greenHex = (this.green * 255).toInt().toString(16).padStart(2, '0')
    val blueHex = (this.blue * 255).toInt().toString(16).padStart(2, '0')
    return "#$redHex$greenHex$blueHex".uppercase()
}

fun String.toColor(): Color {
    val regex = Regex("^#([0-9a-fA-F]{6})$")
    if (!regex.matches(this)) {
        return White
    }
    val hex = if (startsWith("#")) substring(1) else this
    val red = hex.substring(0, 2).toInt(16) / 255f
    val green = hex.substring(2, 4).toInt(16) / 255f
    val blue = hex.substring(4, 6).toInt(16) / 255f

    return Color(red = red, green = green, blue = blue, alpha = 1f)
}

fun List<GameResult>.toResultTypeCountModel(): ResultTypeCountModel {
    var wins = 0
    var losses = 0
    var draws = 0

    for (round in this) {
        if (round.isVictory()) wins++
        else if (round.isLoss()) losses++
        else draws++
    }

    return ResultTypeCountModel(
        wins = wins,
        losses = losses,
        draws = draws
    )
}

fun SquareSides.toRoundedCornerShape() = RoundedCornerShape(
    topStartPercent = this.topLeft,
    topEndPercent = this.topRight,
    bottomEndPercent = this.bottomRight,
    bottomStartPercent = this.bottomLeft
)

fun ButtonType.toAlignment() = when (this) {
    ButtonType.LeftSide -> Alignment.CenterStart
    ButtonType.Center -> Alignment.Center
    ButtonType.RightSide -> Alignment.CenterEnd
}

fun ButtonType.toTextAlignment() = when (this) {
    ButtonType.LeftSide -> TextAlign.Start
    ButtonType.Center -> TextAlign.Center
    ButtonType.RightSide -> TextAlign.End
}
