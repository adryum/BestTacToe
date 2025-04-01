package com.testdevlab.besttactoe.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.viewmodels.Views

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
        Views.SettingsView -> true

        Views.MainView,
        Views.GameView -> false
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
    Views.MainView -> "BestTacToe"
    Views.CreateLobbyView -> "Create room"
    Views.GameView -> ""
    Views.JoinLobbyView -> "Join room"
    Views.MultiplayerView -> "Multiplayer"
    Views.SettingsView -> "Settings"
}

fun List<PiecesUIModel>.toPieceStateList() = this.map { it.state }
