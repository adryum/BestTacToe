package com.testdevlab.besttactoe.ui.animations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.cache.models.ChosenVisualDBModel
import com.testdevlab.besttactoe.core.cache.toObject
import com.testdevlab.besttactoe.core.utils.isWindows
import com.testdevlab.besttactoe.handleDeviceSetSpeed
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.idleRotate
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.scale
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.toColor
import org.jetbrains.compose.resources.painterResource

@Composable
fun VSAnimation(
    modifier: Modifier = Modifier,
) {
    val icons = Preferences
        .chosenVisuals
        ?.toObject<ChosenVisualDBModel>()!!

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(150.ldp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(if (isWindows()) 200.ldp else 100.ldp)
                    .idleRotate(oneCycleDurationMills = 3000.handleDeviceSetSpeed())
                    .scale(from = .2f, to = 1.2f),
                painter = painterResource(icons.playerIcon.resource),
                contentDescription = null,
                colorFilter = ColorFilter.tint(icons.playerTint.toColor())
            )
            Image(
                modifier = Modifier
                    .size(if (isWindows()) 200.ldp else 100.ldp)
                    .idleRotate(
                        oneCycleDurationMills = 3000.handleDeviceSetSpeed(),
                        isClockwise = false
                    )
                    .scale(from = .2f, to = 1.2f),
                painter = painterResource(icons.opponentIcon.resource),
                contentDescription = null,
                colorFilter = ColorFilter.tint(icons.opponentTint.toColor())
            )
        }
        Text(
            modifier = Modifier.zIndex(1f).scale(from = .2f, to = 1f, delay = 0),
            text = "VS",
            color = GrayDark,
            style = textLarge.copy(fontSize = if (isWindows()) 200.sp else 100.sp),
        )
    }
}