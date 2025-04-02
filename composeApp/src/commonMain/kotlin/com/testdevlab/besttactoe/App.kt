package com.testdevlab.besttactoe

import androidx.compose.animation.Crossfade
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
import besttactoe.composeapp.generated.resources.ic_back
import com.testdevlab.besttactoe.ui.components.TTTPiece
import com.testdevlab.besttactoe.ui.components.TopBar
import com.testdevlab.besttactoe.ui.components.ViewTitle
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkBlue
import com.testdevlab.besttactoe.ui.theme.DarkGreen
import com.testdevlab.besttactoe.ui.theme.DarkOrange
import com.testdevlab.besttactoe.ui.theme.Green
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.getViewTitle
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.showTopBar
import com.testdevlab.besttactoe.ui.viewmodels.NavigationObject
import com.testdevlab.besttactoe.ui.viewmodels.Views
import com.testdevlab.besttactoe.ui.views.CreateLobbyView
import com.testdevlab.besttactoe.ui.views.GameView
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
            goBack = NavigationObject::goBack
        )
    }
}


@Composable
fun AppContent(
    currentView: Views,
    goBack: () -> Unit
) {
    val isTopBarShown = currentView.showTopBar()
    val viewTitle = currentView.getViewTitle()

    // background for the whole app. might later change with animations
    Box(
        modifier = Modifier.gradientBackground(
            when (currentView) {
                Views.MainView -> listOf(Orange, DarkOrange)
                Views.GameView -> listOf(Orange, DarkOrange)
                Views.MultiplayerView -> listOf(Green, DarkGreen)
                Views.JoinLobbyView -> listOf(Yellow, Orange)
                Views.CreateLobbyView -> listOf(Orange, DarkOrange)
                Views.SettingsView -> listOf(Blue, DarkBlue)
            },
            angle = 0f
        )
    ) {
        Crossfade(
            targetState = currentView,
        ) { currentView ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (isTopBarShown)
                    TopBar(height = 100.ldp) {
                        TTTPiece(
                            modifier = Modifier.padding(12.ldp).aspectRatio(1f),
                            isClickable = true,
                            icon = Res.drawable.ic_back,
                            onClick = goBack
                        )
                }

                // It gets inside, just is hidden
                if (currentView != Views.GameView)  {
                    ViewTitle(
                        modifier = Modifier.padding(top = if (isTopBarShown) 0.ldp else  100.ldp),
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