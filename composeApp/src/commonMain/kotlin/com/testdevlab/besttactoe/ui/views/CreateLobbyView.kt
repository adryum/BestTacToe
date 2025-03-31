package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.compose_multiplatform
import besttactoe.composeapp.generated.resources.ic_human
import besttactoe.composeapp.generated.resources.ic_settings
import com.testdevlab.besttactoe.ui.components.IconTextButton
import com.testdevlab.besttactoe.ui.components.LobbyCodeShower
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGreen
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
    LobbyCodeShower(
        codeString = codeString
    )
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun CreateLobbyViewPreview() {
    CreateLobbyView()
}
