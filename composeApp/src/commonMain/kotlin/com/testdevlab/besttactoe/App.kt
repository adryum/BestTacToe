package com.testdevlab.besttactoe

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.ui.components.ViewTitle
import com.testdevlab.besttactoe.ui.components.TopBar

import com.testdevlab.besttactoe.ui.views.MainView
import com.testdevlab.besttactoe.ui.theme.BestTacToeTheme
import com.testdevlab.besttactoe.ui.theme.getViewTitle
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGreen
import com.testdevlab.besttactoe.ui.theme.showTopBar
import com.testdevlab.besttactoe.ui.viewmodels.NavigationObject
import com.testdevlab.besttactoe.ui.viewmodels.Views
import com.testdevlab.besttactoe.ui.views.CreateLobbyView
import com.testdevlab.besttactoe.ui.views.GameView
import com.testdevlab.besttactoe.ui.views.JoinRoomView
import com.testdevlab.besttactoe.ui.views.MultiplayerView
import com.testdevlab.besttactoe.ui.views.SettingsView
import de.drick.compose.hotpreview.HotPreview


@Composable
fun App() {
    // stuff passed here is shown on every device
    // for viewModels I will need to define here only values that I'll use from them
    BestTacToeTheme {
        val currentView by NavigationObject.currentView.collectAsState()

        AppContent(
            currentView = currentView,
            goBack = NavigationObject::goBack
        )
    }
//    Image(painterResource(Res.drawable.compose_multiplatform), null)
}


@Composable
fun AppContent(
    currentView: Views,
    goBack: () -> Unit
) {
    val isTopBarShown = currentView.showTopBar()
    val viewTitle = currentView.getViewTitle()

    val scrollState = rememberScrollState()

    // background for the whole app. might later change with animations
    Surface(modifier = Modifier.fillMaxSize(), color = lightGreen) {
        Crossfade(
            targetState = currentView,
        ) { currentView ->
            Column {
                if (isTopBarShown) TopBar { goBack() }

                // It gets inside, just is hidden
                if (currentView != Views.GameView)  {
                    ViewTitle(
                        modifier = Modifier.padding(vertical = 30.ldp),
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