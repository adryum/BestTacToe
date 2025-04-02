package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.ui.components.Button
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorationsWithDarkContentAndColumn
import com.testdevlab.besttactoe.ui.components.ToggleButton
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkBlue
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.ldp
import de.drick.compose.hotpreview.HotPreview

@Composable
fun SettingsView() {
    SettingsViewContent()
}

@Composable
fun SettingsViewContent() {
    var isSoundEnabled by remember { mutableStateOf(true) }
    var isAnimationEnabled by remember { mutableStateOf(false) }

    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.ldp, Alignment.CenterVertically)
        ) {
            ToggleButton(
                isEnabled = isSoundEnabled,
                text = "Sound",
                leftGradientColor = Orange,
                rightGradient = Yellow,
                onClick = { isSoundEnabled = !isSoundEnabled}
            )
            ToggleButton(
                isEnabled = isAnimationEnabled,
                text = "Animation",
                leftGradientColor = Orange,
                rightGradient = Yellow,
                onClick = { isAnimationEnabled = !isAnimationEnabled}
            )
            Button(
                text = "Resolution",
                leftGradientColor = DarkBlue,
                rightGradient = Blue,
                onClick = {}
            )
        }
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun SettingsViewPreview() {
    SettingsViewContent()
}
