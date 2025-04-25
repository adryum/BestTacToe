package com.testdevlab.besttactoe.ui.views

import CodeInputWrapped
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import de.drick.compose.hotpreview.HotPreview

@Composable
fun CreateLobbyView(
    gameHandler: GameHandler = GameHandler,
) {
    val isLoadingView by NavigationObject.isViewLoadingIn.collectAsState()
    CreateLobbyViewContent(
        onGameCreate = gameHandler::createLobby,
        goToDelayed = NavigationObject::delayedGoTo,
        isLoadingView = isLoadingView
    )
}

@Composable
fun CreateLobbyViewContent(
    isLoadingView: Boolean,
    onGameCreate: (Int) -> Unit,
    goToDelayed: (Views, Long, () -> Unit) -> Unit
) {
    val delay = 200L
    var bestOf by remember { mutableStateOf(TextFieldValue("")) }

    CodeInputWrapped(
        modifier = Modifier.fillMaxWidth(),
        title = "Turn Count",
        value = bestOf,
        onValueChanged = { bestOf = it },
        onClick = {
            goToDelayed(Views.CodeView, delay) {
                onGameCreate(bestOf.text.toInt())
            }
        },
        isShown = isLoadingView,
        allowOnlyNumbers = true
    )
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun CreateLobbyViewPreview() {
    CreateLobbyView()
}
