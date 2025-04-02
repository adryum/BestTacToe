package com.testdevlab.besttactoe.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.ui.components.LeftSideButton
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorationsWithDarkContentAndColumn
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkBlue
import com.testdevlab.besttactoe.ui.theme.DarkGreen
import com.testdevlab.besttactoe.ui.theme.Green
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.viewmodels.NavigationObject
import com.testdevlab.besttactoe.ui.viewmodels.Views
import de.drick.compose.hotpreview.HotPreview


@Composable
fun MainView(
    navigationObject: NavigationObject = NavigationObject,
    gameHandler: GameHandler = GameHandler
) {
    MainViewContent(
        onGameStart = gameHandler::startGame,
        goTo = navigationObject::goTo
    )
}

@Composable
fun MainViewContent(
    onGameStart: (Boolean) -> Unit,
    goTo: (Views) -> Unit
) {
    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        LeftSideButton(
            modifier = Modifier,
            text = "VS AI",
            leftGradientColor = Orange,
            rightGradient = Yellow,
            onClick = {
                onGameStart(true) // vs AI mode true
                goTo(Views.GameView)
            }
        )
        LeftSideButton(
            modifier = Modifier,
            text = "Multiplayer",
            leftGradientColor = DarkGreen,
            rightGradient = Green,
            onClick = { goTo(Views.MultiplayerView) }
        )
        LeftSideButton(
            modifier = Modifier,
            text = "Settings",
            leftGradientColor = DarkBlue,
            rightGradient = Blue,
            onClick = { goTo(Views.SettingsView) }
        )
    }
}

@HotPreview(name = "Menu",  widthDp = 540, heightDp = 1020)
@Composable
private fun MainViewPreview() {
    MainViewContent(
        goTo = {},
        onGameStart = {}
    )
}
