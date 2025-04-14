package com.testdevlab.besttactoe.ui.theme

import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import kotlinx.coroutines.delay

fun Modifier.popped(
    delay: Long = 200,
    stiffness: Float = Spring.StiffnessLow,
    damping: Float = Spring.DampingRatioMediumBouncy
): Modifier = composed {
    var isReady by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(
        targetValue = if (isReady) 1f else 0.2f,
        animationSpec = spring(
            dampingRatio = damping,
            stiffness = stiffness
        )
    )

    LaunchedEffect(Unit) {
        delay(delay)
        isReady = true
    }

    scale(animatedScale).fadeIn(duration = delay.toInt())
}

fun Modifier.fadeIn(
    duration: Int,
    easing: Easing = LinearEasing
): Modifier = composed {
    var isShown by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isShown) 1f else 0f,
        animationSpec = tween(
            durationMillis = duration,
            easing = easing,
            delayMillis = 100
        )
    )

    LaunchedEffect(Unit) {
        isShown = true
    }

    alpha(alpha)
}

fun Modifier.fadeOut(
    duration: Int,
    easing: Easing = LinearEasing
): Modifier = composed {
    var isShown by remember { mutableStateOf(true) }
    val alpha by animateFloatAsState(
        targetValue = if (isShown) 1f else 0f,
        animationSpec = tween(
            durationMillis = duration,
            easing = easing,
        )
    )

    LaunchedEffect(Unit) {
        isShown = false
    }

    alpha(alpha)
}

fun Modifier.slideInFromLeft(
    duration: Int,
    easing: Easing = EaseOutQuart,
): Modifier = composed {
    var isShown by remember { mutableStateOf(false) }
    var width by remember { mutableStateOf<Float?>(null) }
    val offsetPart by animateFloatAsState(
        targetValue = if (isShown) 0f else 1f,
        animationSpec = tween(
            durationMillis = duration,
            easing = easing,
        )
    )

    // Logic: While width is not set - maximum offset. Else start animation from received width
    onGloballyPositioned {
        if (it.size.width > 0f) {
            width = it.size.width.toFloat()
            isShown = true
        }
    }.then(
        Modifier.graphicsLayer {
            translationX = if (width != null) -width!! * offsetPart else -9999999f
        }
    )
}

fun Modifier.slideOutToLeft(
    duration: Int,
    easing: Easing = EaseOutQuart
): Modifier = composed {
    var isShown by remember { mutableStateOf(true) }
    var width by remember { mutableStateOf<Float?>(null) }
    val offsetPart by animateFloatAsState(
        targetValue = if (isShown) 0f else 1f,
        animationSpec = tween(
            durationMillis = duration,
            easing = easing,
        )
    )

    onGloballyPositioned {
        if (it.size.width > 0f) {
            width = it.size.width.toFloat()
            isShown = false
        }
    }.then(
        Modifier.graphicsLayer {
            translationX = if (width != null) -width!! * offsetPart else 0f
        }
    )
}

fun Modifier.slideInOutLeft(
    duration: Int,
    outDuration: Int = 300,
    isShown: Boolean,
    copyDurations: Boolean = true
): Modifier = composed {
    if (isShown) {
        slideInFromLeft(
            duration = duration,
        )
    } else {
        slideOutToLeft(
            duration = if (copyDurations) duration else outDuration,
        )
    }
}

fun Modifier.idleRotate(
    maxAngle: Float,
    oneCycleDurationMills: Int,
    easing: Easing = EaseInOutQuart
): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val angle by transition.animateFloat(
        initialValue = maxAngle,
        targetValue = -maxAngle,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = oneCycleDurationMills,
                easing = easing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Modifier.graphicsLayer {
        rotationZ = angle
    }
}

fun Modifier.slideInFromRight(
    duration: Int,
    easing: Easing = EaseOutQuart,
): Modifier = composed {
    var isShown by remember { mutableStateOf(false) }
    var width by remember { mutableStateOf<Float?>(null) }
    val offsetPart by animateFloatAsState(
        targetValue = if (isShown) 0f else 1f,
        animationSpec = tween(
            durationMillis = duration,
            easing = easing,
        )
    )

    // Logic: While width is not set - maximum offset. Else start animation from received width
    onGloballyPositioned {
        if (it.size.width > 0f) {
            width = it.size.width.toFloat()
            isShown = true
        }
    }.then(
        Modifier.graphicsLayer {
            translationX = if (width != null) width!! * offsetPart else 9999999f
        }
    )
}

fun Modifier.slideOutToRight(
    duration: Int,
    easing: Easing = EaseOutQuart
): Modifier = composed {
    var isShown by remember { mutableStateOf(true) }
    var width by remember { mutableStateOf<Float?>(null) }
    val offsetPart by animateFloatAsState(
        targetValue = if (isShown) 0f else 1f,
        animationSpec = tween(
            durationMillis = duration,
            easing = easing,
        )
    )

    onGloballyPositioned {
        if (it.size.width > 0f) {
            width = it.size.width.toFloat()
            isShown = false
        }
    }.then(
        Modifier.graphicsLayer {
            translationX = if (width != null) width!! * offsetPart else 0f
        }
    )
}

fun Modifier.slideInOutRight(
    duration: Int,
    outDuration: Int = 300,
    isShown: Boolean,
    copyDurations: Boolean = true
): Modifier = composed {
    if (isShown) {
        slideInFromRight(
            duration = duration,
        )
    } else {
        slideOutToRight(
            duration = if (copyDurations) duration else outDuration,
        )
    }
}

fun Modifier.slideLeftToRight(
    duration: Int,
    outDuration: Int = 300,
    isShown: Boolean,
    copyDurations: Boolean = true
): Modifier = composed {
    if (isShown) {
        slideInFromLeft(
            duration = duration,
        )
    } else {
        slideOutToRight(
            duration = if (copyDurations) duration else outDuration,
        )
    }
}