package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.components.Button
import com.testdevlab.besttactoe.ui.components.ButtonType
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
    onGameStart: (GameMode) -> Unit,
    goTo: (Views) -> Unit
) {
    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "VS AI",
            leftGradientColor = Orange,
            rightGradient = Yellow,
            buttonType = ButtonType.LeftSide,
            onClick = {
                onGameStart(GameMode.VS_AI)
                goTo(Views.GameView)
            }
        )
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Multiplayer",
            leftGradientColor = DarkGreen,
            rightGradient = Green,
            buttonType = ButtonType.LeftSide,
            onClick = { goTo(Views.MultiplayerView) }
        )
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Settings",
            leftGradientColor = DarkBlue,
            rightGradient = Blue,
            buttonType = ButtonType.LeftSide,
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
