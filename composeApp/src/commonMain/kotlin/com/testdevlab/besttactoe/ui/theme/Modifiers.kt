package com.testdevlab.besttactoe.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.EaseOutBounce
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
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay

fun Modifier.ifThen(bool: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    // Modifier.() allows to do the same stuff as .apply{}
    // this.modifier() - appends provided modifiers to extended modifier
    return if (bool) this.modifier() else this
}

fun Modifier.popIn(
    delay: Long = 200,
    fadeInDuration: Int = 200,
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

    fadeIn(duration = fadeInDuration).scale(animatedScale)
}

fun Modifier.popOut(
    delay: Long = 200,
    fadeOutDuration: Int = 200,
    stiffness: Float = Spring.StiffnessLow,
    damping: Float = Spring.DampingRatioMediumBouncy
): Modifier = composed {
    var isReady by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(
        targetValue = if (isReady) 0f else 1f,
        animationSpec = spring(
            dampingRatio = damping,
            stiffness = stiffness
        )
    )

    LaunchedEffect(Unit) {
        delay(delay)
        isReady = true
    }

    fadeOut(duration = fadeOutDuration).scale(animatedScale)
}

fun Modifier.popInOut(
    delay: Long = 0,
    isShown: Boolean,
    fadeDuration: Int = 200,
    delayOnPopOut: Boolean = false
): Modifier = composed {
    if (isShown) {
        popIn(
            fadeInDuration = fadeDuration,
            delay = delay
        )
    } else {
        popOut(
            fadeOutDuration = fadeDuration,
            delay = if (delayOnPopOut) delay else 0
        )
    }
}

fun Modifier.scale(
    from: Float,
    to: Float,
    delay: Long = 200,
    stiffness: Float = Spring.StiffnessLow,
    damping: Float = Spring.DampingRatioMediumBouncy
): Modifier = composed {
    var isReady by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(
        targetValue = if (isReady) to else from,
        animationSpec = spring(
            dampingRatio = damping,
            stiffness = stiffness
        )
    )

    LaunchedEffect(Unit) {
        delay(delay)
        isReady = true
    }

    scale(animatedScale)
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
    to: Float = 0f,
    easing: Easing = LinearEasing
): Modifier = composed {
    var isShown by remember { mutableStateOf(true) }
    val alpha by animateFloatAsState(
        targetValue = if (isShown) 1f else to,
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
    }.then(graphicsLayer {
        translationX = if (width != null) -width!! * offsetPart else -9999999f
    })
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
    }.then(graphicsLayer {
        translationX = if (width != null) -width!! * offsetPart else 0f
    })
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

enum class RotationAxis {
    Z,
    X,
    Y
}

fun Modifier.idleRotateClamped(
    maxAngle: Float,
    oneCycleDurationMills: Int,
    easing: Easing = EaseInOutQuart,
    axis: RotationAxis = RotationAxis.Z
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

    graphicsLayer {
        when (axis) {
            RotationAxis.Z -> rotationZ = angle
            RotationAxis.X -> rotationX = angle
            RotationAxis.Y -> rotationY = angle
        }
    }
}

fun Modifier.idleBreathing(
    fromScale: Float = 1f,
    toScale: Float,
    breatheInTime: Int,
    easing: Easing = EaseInOutQuart,
    repeatMode: RepeatMode = RepeatMode.Reverse,
    enabled: Boolean = true
): Modifier = composed {
    if (!enabled) return@composed this

    val transition = rememberInfiniteTransition()
    val scale by transition.animateFloat(
        initialValue = fromScale,
        targetValue = toScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = breatheInTime,
                easing = easing
            ),
            repeatMode = repeatMode
        )
    )

    scale(scale)
}

fun Modifier.idleRotate(
    oneCycleDurationMills: Int,
    isClockwise: Boolean = true,
    easing: Easing = EaseInOutQuart,
    axis: RotationAxis = RotationAxis.Z,
    repeatMode: RepeatMode = RepeatMode.Reverse
): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val angleProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = if (isClockwise) 1f else -1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = oneCycleDurationMills,
                easing = easing
            ),
            repeatMode = repeatMode
        )
    )

    graphicsLayer {
        when (axis) {
            RotationAxis.Z -> rotationZ = 360 * angleProgress
            RotationAxis.X -> rotationX = 360 * angleProgress
            RotationAxis.Y -> rotationY = 360 * angleProgress
        }
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
    }.then(graphicsLayer {
        translationX = if (width != null) width!! * offsetPart else 9999999f
    })
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
    }.then(graphicsLayer {
        translationX = if (width != null) width!! * offsetPart else 0f
    })
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

fun Modifier.fallDown(
    duration: Int,
    distanceToFallFrom: Dp,
    easing: Easing = EaseOutBounce
): Modifier = composed {
    val offset = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        offset.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = duration,
                easing = easing,
            )
        )
    }

    graphicsLayer {
        translationY = -distanceToFallFrom.toPx() * offset.value
    }
}
