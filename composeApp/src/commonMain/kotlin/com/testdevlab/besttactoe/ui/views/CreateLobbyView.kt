package com.testdevlab.besttactoe.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.testdevlab.besttactoe.ui.components.CodeShower
import com.testdevlab.besttactoe.ui.components.DarkBackgroundWithDarkTop
import com.testdevlab.besttactoe.ui.theme.DarkOrange
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.ldp
import de.drick.compose.hotpreview.HotPreview

@Composable
fun CreateLobbyView() {
    CreateLobbyViewContent(
        codeString = "IMCODE"
    )
}

@Composable
fun CreateLobbyViewContent(
    codeString: String
) {
    DarkBackgroundWithDarkTop(verticalColumnAlignment = Alignment.Top) {
        CodeShower(
            code = codeString,
            leftGradientColor = Orange,
            rightGradientColor = Yellow,
            inputLeftGradientColor = DarkOrange,
            inputRightGradientColor = Orange,
            height = 80.ldp
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun CreateLobbyViewPreview() {
    CreateLobbyView()
}
