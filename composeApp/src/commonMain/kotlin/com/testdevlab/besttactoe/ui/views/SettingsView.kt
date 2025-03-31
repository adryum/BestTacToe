package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.ui.components.ToggleButton
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGreen
import com.testdevlab.besttactoe.ui.viewmodels.NavigationObject
import com.testdevlab.besttactoe.ui.viewmodels.Views
import de.drick.compose.hotpreview.HotPreview

@Composable
fun SettingsView(
    navigationObject: NavigationObject = NavigationObject
) {
    SettingsViewContent(
        goTo = navigationObject::goTo
    )
}

@Composable
fun SettingsViewContent(
    goTo: (Views) -> Unit
) {
    var isSoundEnabled by remember { mutableStateOf(true) }
    var isAnimationEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(lightGreen).padding(top = 60.ldp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(180.ldp)
    ) {
        Column(
            modifier = Modifier.weight(.7f),
            verticalArrangement = Arrangement.spacedBy(30.ldp)
        ) {
            ToggleButton(
                isEnabled = isSoundEnabled,
                text = "Sound",
                onClick = { isSoundEnabled = !isSoundEnabled }
            )
            ToggleButton(
                isEnabled = isAnimationEnabled,
                text = "Animation",
                onClick = { isAnimationEnabled = !isAnimationEnabled }
            )

        }
    }

}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun SettingsViewPreview() {
    SettingsViewContent(goTo = {})
}
