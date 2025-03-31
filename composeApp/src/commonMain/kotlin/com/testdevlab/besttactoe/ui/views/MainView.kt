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
import besttactoe.composeapp.generated.resources.ic_checkmark
import besttactoe.composeapp.generated.resources.ic_human
import besttactoe.composeapp.generated.resources.ic_robot
import besttactoe.composeapp.generated.resources.ic_settings
import com.testdevlab.besttactoe.ui.components.IconTextButton
import com.testdevlab.besttactoe.ui.components.ToggleButton
import com.testdevlab.besttactoe.ui.components.ViewTitle
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGreen
import com.testdevlab.besttactoe.ui.viewmodels.MenuViewModel
import com.testdevlab.besttactoe.ui.viewmodels.NavigationObject
import com.testdevlab.besttactoe.ui.viewmodels.Views
import de.drick.compose.hotpreview.HotPreview


@Composable
fun MainView(
    navigationObject: NavigationObject = NavigationObject,
    menuViewModel: MenuViewModel = MenuViewModel
) {
    MainViewContent(
        goTo = navigationObject::goTo
    )
}

@Composable
fun MainViewContent(
    goTo: (Views) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(lightGreen).padding(top = 60.ldp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(180.ldp)
    ) {
        Column(
            modifier = Modifier.weight(.7f),
            verticalArrangement = Arrangement.spacedBy(30.ldp)
        ) {
            IconTextButton(
                modifier = Modifier,
                text = "VS AI",
                icon = Res.drawable.ic_robot,
                onClick = { goTo(Views.GameView) }
            )
            IconTextButton(
                modifier = Modifier,
                text = "Multiplayer",
                icon = Res.drawable.ic_human,
                onClick = { goTo(Views.MultiplayerView) }
            )
            IconTextButton(
                modifier = Modifier,
                text = "Settings",
                isBad = true,
                icon = Res.drawable.ic_settings,
                onClick = { goTo(Views.SettingsView) }
            )
        }
    }

}

@HotPreview(name = "Menu",  widthDp = 540, heightDp = 1020)
@Composable
private fun MainViewPreview() {
    MainViewContent(goTo = {})
}
