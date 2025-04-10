package com.testdevlab.besttactoe.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_circle
import besttactoe.composeapp.generated.resources.ic_cross
import besttactoe.composeapp.generated.resources.ic_robot
import com.testdevlab.besttactoe.core.cache.models.HistoryDBModel
import com.testdevlab.besttactoe.core.cache.models.PieceDBModel
import com.testdevlab.besttactoe.core.cache.models.SegmentDBModel
import com.testdevlab.besttactoe.ui.GamesResultType
import com.testdevlab.besttactoe.ui.PieceUIModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
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
    Views.GameView,
    Views.HistoryView -> true

    Views.MainView, -> false
}

fun Views.getViewTitle(): String = when (this) {
    Views.MainView -> "Best TacToe"
    Views.CreateLobbyView -> "Create room"
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

fun GamesResultType.isVictory() = this == GamesResultType.Victory
fun GamesResultType.isLoss() = this == GamesResultType.Loss
fun GamesResultType.isDraw() = this == GamesResultType.Draw

fun GamesResultType.color() = when (this) {
    GamesResultType.Victory -> Blue
    GamesResultType.Loss -> Red
    GamesResultType.Draw -> GrayLight
}

fun GamesResultType.icon() = when (this) {
    GamesResultType.Victory -> Res.drawable.ic_cross
    GamesResultType.Loss -> Res.drawable.ic_circle
    GamesResultType.Draw -> Res.drawable.ic_robot
}

fun List<List<GamesResultType>>.toHistoryDBModel() = HistoryDBModel(
    results = this
)