package com.testdevlab.besttactoe.ui.views

import CodeInputField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.testdevlab.besttactoe.core.cache.PreferenceHandler
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
import com.testdevlab.besttactoe.ui.theme.popIn
import de.drick.compose.hotpreview.HotPreview

@Composable
fun SettingsView(
    gameHandler: GameHandler= GameHandler,
    navigationHandler: NavigationObject = NavigationObject
) {
    val username by PreferenceHandler.userName.collectAsState()

    LaunchedEffect(username) {
        PreferenceHandler.refreshVisuals()
    }

    println(username)

    if (username != null)
        SettingsViewContent(
            username = username!!,
            onGoClick = navigationHandler::goTo,
            onPlayClick = gameHandler::startLocalGame,
            saveName = PreferenceHandler::setUserName
        )
}

@Composable
fun SettingsViewContent(
    username: String,
    onGoClick: (Views) -> Unit,
    onPlayClick: (GameMode) -> Unit,
    saveName: (String) -> Unit
) {
    var usernameValue by remember { mutableStateOf(TextFieldValue(username)) }
    var isSoundEnabled by remember { mutableStateOf(true) }
    var isAnimationEnabled by remember { mutableStateOf(false) }

    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.ldp, Alignment.CenterVertically)
        ) {
            CodeInputField(
                value = usernameValue,
                onValueChanged = {
                    usernameValue = it
                    saveName(it.text)
                },
                limitSymbols = true,
                limitSymbolsToCount = 16
            )
            ToggleButton(
                modifier = Modifier.popIn(200),
                isEnabled = isSoundEnabled,
                text = "Sound",
                leftGradientColor = Orange,
                rightGradient = Yellow,
                onClick = { isSoundEnabled = !isSoundEnabled}
            )
            ToggleButton(
                modifier = Modifier.popIn(300),
                isEnabled = isAnimationEnabled,
                text = "Animation",
                leftGradientColor = Orange,
                rightGradient = Yellow,
                onClick = { isAnimationEnabled = !isAnimationEnabled}
            )
            Button(
                modifier = Modifier.fillMaxWidth(.7f).popIn(400),
                text = "RoboRumble",
                colorGradient = DarkBlueBlueList,
                buttonType = ButtonType.Center,
                onClick = {
                    onGoClick(Views.GameView)
                    onPlayClick(GameMode.RoboRumble)
                }
            )
            Button(
                modifier = Modifier.fillMaxWidth(.7f).popIn(500),
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
//    SettingsViewContent({}, {})
}
