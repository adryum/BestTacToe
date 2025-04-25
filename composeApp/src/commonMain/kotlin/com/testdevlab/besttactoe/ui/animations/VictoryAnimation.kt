package com.testdevlab.besttactoe.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.zIndex
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_crown_sharp
import com.testdevlab.besttactoe.ui.GameEndedAnimationModel
import com.testdevlab.besttactoe.ui.components.Button
import com.testdevlab.besttactoe.ui.components.ButtonType
import com.testdevlab.besttactoe.ui.components.WrappedImage
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import com.testdevlab.besttactoe.ui.theme.RedDarkOrangeList
import com.testdevlab.besttactoe.ui.theme.White
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.idleBreathing
import com.testdevlab.besttactoe.ui.theme.idleRotateClamped
import com.testdevlab.besttactoe.ui.theme.ifThen
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.popInOut
import com.testdevlab.besttactoe.ui.theme.textTitle
import kotlinx.coroutines.delay

@Composable
fun VictoryAnimation(
    data: GameEndedAnimationModel
) {
    var isShown by remember { mutableStateOf(false) }
    var areButtonsShown by remember { mutableStateOf(false) }
    var textColor by remember { mutableStateOf(White) }
    var areButtonsShaking by remember { mutableStateOf(true) }

    getRainbowColors(duration = 30, colorCount = 10,
        onAnimationEnd = {
            areButtonsShaking = false
        },
        currentColor = {
            textColor = it
        }
    )

    val offset = remember { Animatable(-15f) }
    LaunchedEffect(Unit) {
        delay(100)
        isShown = true
        offset.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 5000, easing = EaseInOutSine),
        )
    }

    LaunchedEffect(Unit) {
        delay(1500)
        areButtonsShown = true
    }

    Column(
        modifier = Modifier.alpha(if (isShown) 1f else 0f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(120.ldp)
    ) {
        Column(
            modifier = Modifier
                .rotate(-5f)
                .offset(-offset.value.ldp, -offset.value.ldp)
                .idleRotateClamped(
                    maxAngle = 1f,
                    oneCycleDurationMills = 2000,
                    easing = EaseInOut
                ),
        ) {
            WrappedImage(
                modifier = Modifier
                    .size(150.ldp)
                    .zIndex(1f)
                    .align(Alignment.Start)
                    .popInOut(delay = 500, isShown = isShown, fadeDuration = 300),
                res = Res.drawable.ic_crown_sharp,
                tint = Yellow
            )
            Text(
                modifier = Modifier
                    .popInOut(delay = 400, isShown = isShown, fadeDuration = 300)
                    .ifThen(areButtonsShaking) {
                        idleBreathing(
                            fromScale = .95f,
                            toScale = 1.05f,
                            breatheInTime = 90,
                        )
                    },
                text = "${data.name} won!",
                style = textTitle.copy(color = textColor)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.ldp, Alignment.CenterHorizontally)
        ) {
            Button(
                modifier = Modifier
                    .popInOut(delay = 100, isShown = areButtonsShown, fadeDuration = 100),
                text = "Rematch",
                colorGradient = OrangeYellowList,
                buttonType = ButtonType.Center,
                onClick = data.onRematch
            )

            Button(
                modifier = Modifier
                    .popInOut(delay = 300, isShown = areButtonsShown, fadeDuration = 100),
                text = "Leave",
                colorGradient = RedDarkOrangeList,
                buttonType = ButtonType.Center,
                onClick = data.onLeave
            )
        }
    }
}