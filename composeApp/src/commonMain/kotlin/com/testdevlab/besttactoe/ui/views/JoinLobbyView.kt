package com.testdevlab.besttactoe.ui.views

import CodeInputField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import com.testdevlab.besttactoe.ui.components.DarkBackgroundWithDarkTop
import com.testdevlab.besttactoe.ui.theme.DarkOrange
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.ldp
import de.drick.compose.hotpreview.HotPreview

@Composable
fun JoinRoomView() {
    JoinRoomViewContent(onCodeEnter = {})
}

@Composable
fun JoinRoomViewContent(
    onCodeEnter: (String) -> Unit
) {
    var codeInputValue by remember { mutableStateOf(TextFieldValue("")) }
    DarkBackgroundWithDarkTop(
        verticalColumnAlignment = Alignment.Top,
        horizontalColumnAlignment = Alignment.CenterHorizontally
    ) {
        CodeInputField(
            value = codeInputValue,
            height = 80.ldp,
            leftGradientColor = DarkOrange,
            rightGradientColor = Orange,
            inputLeftGradientColor = Orange,
            inputRightGradientColor = Yellow,
            onValueChanged = { textFieldValue ->
                codeInputValue = textFieldValue
            },
            onSend = { onCodeEnter(codeInputValue.text) }
        )
    }
}

@HotPreview(name = "Menu",  widthDp = 540, heightDp = 1020)
@Composable
private fun JoinRoomViewPreview() {
    JoinRoomView()
}
