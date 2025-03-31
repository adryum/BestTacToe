package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.compose_multiplatform
import besttactoe.composeapp.generated.resources.ic_human
import besttactoe.composeapp.generated.resources.ic_settings
import com.testdevlab.besttactoe.ui.components.CodeInputField
import com.testdevlab.besttactoe.ui.components.IconTextButton
import com.testdevlab.besttactoe.ui.components.TextButton
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGreen
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.ldp)
    ) {
        CodeInputField(
            textValue = codeInputValue,
            onValueChanged = { newValue ->
                codeInputValue = newValue
            }
        )

        TextButton(
            text = "Enter",
            onClick = { onCodeEnter(codeInputValue.text) }
        )
    }
}

@HotPreview(name = "Menu",  widthDp = 540, heightDp = 1020)
@Composable
private fun JoinRoomViewPreview() {
    JoinRoomView()
}
