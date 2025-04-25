package com.testdevlab.besttactoe.ui.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.testdevlab.besttactoe.ui.theme.White
import com.testdevlab.besttactoe.ui.theme.getRainbowColorMutableList
import kotlinx.coroutines.delay

@Composable
fun getRainbowColors(
    duration: Int,
    colorChangeSpeed: Int = 200,
    colorCount: Int,
    startingColor: Color = White,
    endColor: Color = White,
    currentColor: (Color) -> Unit = {},
    onAnimationEnd: () -> Unit = {}
) {
    var targetIndex by remember { mutableStateOf(0) }
    val colors = remember {
        getRainbowColorMutableList(colorCount).apply {
            add(0, startingColor)
            add(endColor)
        }
    }
    val colorIndex by animateIntAsState(
        targetValue = targetIndex,
        animationSpec = tween(
            durationMillis = duration,
            easing = LinearEasing
        ),
        finishedListener = {
            if (targetIndex < colors.size - 1)
                targetIndex++
            else
                onAnimationEnd()
        }
    )
    val color by animateColorAsState(
        targetValue = colors[colorIndex],
        animationSpec = tween(
            durationMillis = colorChangeSpeed,
            easing = EaseInOutSine
        )
    )

    LaunchedEffect(Unit) {
        delay(duration.toLong())
        targetIndex = 1
    }

    currentColor(color)
}
