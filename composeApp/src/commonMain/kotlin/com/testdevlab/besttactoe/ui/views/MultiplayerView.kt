package com.testdevlab.besttactoe.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.ui.components.LeftSideButton
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
    navigationObject: NavigationObject = NavigationObject
) {

    MultiplayerViewContent(goTo = navigationObject::goTo)
}

@Composable
fun MultiplayerViewContent(
    goTo: (Views) -> Unit
) {
    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        LeftSideButton(
            modifier = Modifier,
            text = "Join",
            leftGradientColor = Orange,
            rightGradient = Yellow,
            onClick = {
                goTo(Views.JoinLobbyView)
            }
        )
        LeftSideButton(
            modifier = Modifier,
            text = "Create",
            leftGradientColor = DarkOrange,
            rightGradient = Orange,
            onClick = { goTo(Views.CreateLobbyView) }
        )
        LeftSideButton(
            modifier = Modifier,
            text = "Hot-seat",
            leftGradientColor = DarkBlue,
            rightGradient = Blue,
            onClick = { goTo(Views.MultiplayerView) }
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun MultiplayerViewPreview() {
    MultiplayerViewContent(goTo={})
}
