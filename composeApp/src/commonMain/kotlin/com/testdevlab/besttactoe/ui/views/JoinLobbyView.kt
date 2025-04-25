package com.testdevlab.besttactoe.ui.views

import CodeInputField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.ui.components.Button
import com.testdevlab.besttactoe.ui.components.ButtonType
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.theme.OrangeList
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.popInOut
import com.testdevlab.besttactoe.ui.theme.textMedium
import de.drick.compose.hotpreview.HotPreview

@Composable
fun JoinRoomView(
    gameHandler: GameHandler = GameHandler
) {
    val isLoadingView by NavigationObject.isViewLoadingIn.collectAsState()
    JoinRoomViewContent(
        onCodeEnter = gameHandler::joinLobby,
        isLoadingView = isLoadingView
    )
}

@Composable
fun JoinRoomViewContent(
    isLoadingView: Boolean,
    onCodeEnter: (String) -> Unit
) {
    var codeInputValue by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.ldp)
    ) {
        CodeInputField(
            modifier = Modifier
                .size(height = 100.ldp, width = 250.ldp)
                .popInOut(
                    delay = 200,
                    isShown = isLoadingView
                ),
            value = codeInputValue,
            onValueChanged = { textFieldValue ->
                codeInputValue = textFieldValue
            },
            limitSymbols = true,
            limitSymbolsToCount = 4
        )
        Button(
            modifier = Modifier
                .size(width = 120.ldp, height = 50.ldp)
                .popInOut(
                    delay = 300,
                    isShown = isLoadingView
                ),
            text = "Enter",
            buttonType = ButtonType.Center,
            colorGradient = OrangeList,
            textStyle = textMedium,
            horizontalPadding = 4.ldp
        ) {
            onCodeEnter(codeInputValue.text)
        }
    }
}

@HotPreview(name = "Menu",  widthDp = 540, heightDp = 1020)
@Composable
private fun JoinRoomViewPreview() {
    JoinRoomView()
}
