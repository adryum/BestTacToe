package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_human
import com.testdevlab.besttactoe.ui.components.CubeButton
import com.testdevlab.besttactoe.ui.theme.ldp
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
    Row (
        modifier = Modifier.padding(top = 60.ldp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CubeButton(
            text = "Join",
            icon = Res.drawable.ic_human,
            onClick = { goTo(Views.JoinLobbyView) }
        )
        Spacer(Modifier.width(18.ldp))

        CubeButton(
            text = "Create",
            icon = Res.drawable.ic_human,
            onClick = { goTo(Views.CreateLobbyView) }
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun MultiplayerViewPreview() {
    MultiplayerViewContent(goTo={})
}
