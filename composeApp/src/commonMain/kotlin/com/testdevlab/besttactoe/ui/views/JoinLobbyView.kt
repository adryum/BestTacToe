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
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
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
            colorGradient = DarkOrangeOrangeList,
            inputColorGradient = OrangeYellowList,
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
