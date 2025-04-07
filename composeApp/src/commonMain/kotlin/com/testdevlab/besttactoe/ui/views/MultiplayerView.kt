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
import com.testdevlab.besttactoe.ui.theme.DarkOrange
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.viewmodels.NavigationObject
import com.testdevlab.besttactoe.ui.viewmodels.Views
import de.drick.compose.hotpreview.HotPreview

@Composable
fun MultiplayerView(
    navigationObject: NavigationObject = NavigationObject,
    gameHandler: GameHandler = GameHandler
) {

    MultiplayerViewContent(
        startGame = gameHandler::startGame,
        goTo = navigationObject::goTo
    )
}

@Composable
fun MultiplayerViewContent(
    startGame: (GameMode) -> Unit,
    goTo: (Views) -> Unit
) {
    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Join",
            leftGradientColor = Orange,
            rightGradient = Yellow,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goTo(Views.JoinLobbyView)
            }
        )
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Create",
            leftGradientColor = DarkOrange,
            rightGradient = Orange,
            buttonType = ButtonType.LeftSide,
            onClick = { goTo(Views.CreateLobbyView) }
        )
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Hot-seat",
            leftGradientColor = DarkBlue,
            rightGradient = Blue,
            buttonType = ButtonType.LeftSide,
            onClick = {
                startGame(GameMode.HotSeat)
                goTo(Views.GameView)
            }
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun MultiplayerViewPreview() {
    MultiplayerViewContent(
        goTo={},
        startGame = {}
    )
}
