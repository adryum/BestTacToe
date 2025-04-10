package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.components.Button
import com.testdevlab.besttactoe.ui.components.ButtonType
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorationsWithDarkContentAndColumn
import com.testdevlab.besttactoe.ui.components.ToggleButton
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.DarkBlueBlueList
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.ldp
import de.drick.compose.hotpreview.HotPreview

@Composable
fun SettingsView(
    gameHandler: GameHandler= GameHandler,
    navigationHandler: NavigationObject = NavigationObject
) {
    SettingsViewContent(
        onGoClick = navigationHandler::goTo,
        onPlayClick = gameHandler::startGame
    )
}

@Composable
fun SettingsViewContent(
    onGoClick: (Views) -> Unit,
    onPlayClick: (GameMode) -> Unit,
) {
    var isSoundEnabled by remember { mutableStateOf(true) }
    var isAnimationEnabled by remember { mutableStateOf(false) }

    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.ldp, Alignment.CenterVertically)
        ) {
            ToggleButton(
                isEnabled = isSoundEnabled,
                text = "Sound",
                leftGradientColor = Orange,
                rightGradient = Yellow,
                onClick = { isSoundEnabled = !isSoundEnabled}
            )
            ToggleButton(
                isEnabled = isAnimationEnabled,
                text = "Animation",
                leftGradientColor = Orange,
                rightGradient = Yellow,
                onClick = { isAnimationEnabled = !isAnimationEnabled}
            )
            Button(
                containerModifier = Modifier.fillMaxWidth(.7f),
                text = "RoboRumble",
                colorGradient = DarkBlueBlueList,
                buttonType = ButtonType.Center,
                onClick = {
                    onGoClick(Views.GameView)
                    onPlayClick(GameMode.RoboRumble)
                }
            )
            Button(
                containerModifier = Modifier.fillMaxWidth(.7f),
                text = "Resolution",
                colorGradient = DarkBlueBlueList,
                buttonType = ButtonType.Center,
                onClick = {}
            )
        }
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun SettingsViewPreview() {
    SettingsViewContent({}, {})
}
