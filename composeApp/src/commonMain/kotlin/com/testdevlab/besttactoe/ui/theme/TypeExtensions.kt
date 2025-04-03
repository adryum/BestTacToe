package com.testdevlab.besttactoe.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.viewmodels.Views
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
    Views.GameView -> true

    Views.MainView, -> false
}

fun Views.showGameBar(): Boolean = when (this) {
    Views.CreateLobbyView,
    Views.JoinLobbyView,
    Views.MultiplayerView,
    Views.MainView,
    Views.SettingsView -> false

    Views.GameView -> true
}

fun Views.getViewTitle(): String = when (this) {
    Views.MainView -> "Best TacToe"
    Views.CreateLobbyView -> "Create room"
    Views.GameView -> ""
    Views.JoinLobbyView -> "Join room"
    Views.MultiplayerView -> "Multiplayer"
    Views.SettingsView -> "Settings"
}

fun List<PiecesUIModel>.toPieceStateList() = this.map { it.state }

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

