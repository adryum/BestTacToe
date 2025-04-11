package com.testdevlab.besttactoe

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_back_rounded
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.ui.components.TicTacToePiece
import com.testdevlab.besttactoe.ui.components.TopBar
import com.testdevlab.besttactoe.ui.components.ViewTitle
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkGreenGreenList
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.getViewTitle
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.showTopBar
import com.testdevlab.besttactoe.ui.views.CreateLobbyView
import com.testdevlab.besttactoe.ui.views.GameView
import com.testdevlab.besttactoe.ui.views.HistoryView
import com.testdevlab.besttactoe.ui.views.JoinRoomView
import com.testdevlab.besttactoe.ui.views.MainView
import com.testdevlab.besttactoe.ui.views.MultiplayerView
import com.testdevlab.besttactoe.ui.views.SettingsView
import de.drick.compose.hotpreview.HotPreview


@Composable
fun App() {
    // stuff passed here is shown on every device
    // for viewModels I will need to define here only values that I'll use from them
    val currentView by NavigationObject.currentView.collectAsState()

    Surface {
        AppContent(
            currentView = currentView,
            goBack = NavigationObject::goBack,
            exitGame = GameHandler::saveAndClearGame,
        )
    }
}


@Composable
fun AppContent(
    currentView: Views,
    goBack: () -> Unit,
    exitGame: () -> Unit,
) {
    val isTopBarShown = currentView.showTopBar()
    val viewTitle = currentView.getViewTitle()
    val topBarHeight = 80.ldp

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val color1 by animateColorAsState(
        targetValue = when (currentView) {
            Views.MainView -> DarkOrangeOrangeList[0]
            Views.GameView -> DarkOrangeOrangeList[0]
            Views.MultiplayerView -> DarkGreenGreenList[0]
            Views.JoinLobbyView -> Yellow
            Views.CreateLobbyView -> Orange
            Views.SettingsView -> Blue
            Views.HistoryView -> Blue
        },
        animationSpec = tween(durationMillis = 400, easing = LinearEasing)
    )

    val color2 by animateColorAsState(
        targetValue = when (currentView) {
            Views.MainView -> DarkOrangeOrangeList[1]
            Views.GameView -> DarkOrangeOrangeList[1]
            Views.MultiplayerView -> DarkGreenGreenList[1]
            Views.JoinLobbyView -> Yellow
            Views.CreateLobbyView -> Orange
            Views.HistoryView -> Blue
            Views.SettingsView -> Blue
        },
        animationSpec = tween(durationMillis = 400, easing = LinearEasing)
    )

    // background for the whole app. might later change with animations
    Box(
        modifier = Modifier.gradientBackground(
            colors = listOf(color1, color2),
            angle = angle
        )
    ) {
        Crossfade(
            targetState = currentView,
            animationSpec = tween()
        ) { currentView ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (isTopBarShown)
                    TopBar(height = topBarHeight) {
                        TicTacToePiece(
                            modifier = Modifier.padding(12.ldp).aspectRatio(1f),
                            isClickable = true,
                            icon = Res.drawable.ic_back_rounded,
                            onClick = {
                                goBack()
                                if (currentView == Views.GameView) {
                                    exitGame()
                                }
                            }
                        )
                    }

                // It gets inside, just is hidden
                if (currentView != Views.GameView)  {
                    ViewTitle(
                        modifier = Modifier.padding(top = if (isTopBarShown) 0.ldp else topBarHeight),
                        text = viewTitle
                    )
                }

                when (currentView) {
                    Views.MainView -> MainView()
                    Views.CreateLobbyView -> CreateLobbyView()
                    Views.GameView -> GameView()
                    Views.JoinLobbyView -> JoinRoomView()
                    Views.MultiplayerView -> MultiplayerView()
                    Views.SettingsView -> SettingsView()
                    Views.HistoryView -> HistoryView()
                }
            }
        }
    }
}


@Composable
@HotPreview("app", widthDp = 500, heightDp = 1000)
private fun AppPreview() {
    App()
}