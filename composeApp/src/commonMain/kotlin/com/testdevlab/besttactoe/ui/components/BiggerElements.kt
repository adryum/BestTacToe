package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.cache.models.ChosenIconDBModel
import com.testdevlab.besttactoe.core.cache.toObject
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.buttonStyle
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.idleRotate
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.scale
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.textTitle
import com.testdevlab.besttactoe.ui.theme.toColor
import org.jetbrains.compose.resources.painterResource

@Composable
fun VSScreen(
    modifier: Modifier = Modifier
) {
    val icons = Preferences
        .chosenIcons
        ?.toObject<ChosenIconDBModel>()!!

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(48.ldp)
                .idleRotate(oneCycleDurationMills = 5000)
                .scale(from = .2f, to = 1.2f),
            painter = painterResource(icons.playerIcon.resource),
            contentDescription = null,
            colorFilter = ColorFilter.tint(icons.playerTint.toColor())
        )
        Text(
            modifier = Modifier.scale(1.5f).zIndex(1f),
            text = "VS",
            color = GrayDark,
            style = textLarge,
            fontFamily = getSportFontFamily()
        )
        Image(
            modifier = Modifier
                .size(48.ldp)
                .idleRotate(oneCycleDurationMills = 5000, isClockwise = false)
                .scale(from = .2f, to = 1.2f),
            painter = painterResource(icons.opponentIcon.resource),
            contentDescription = null,
            colorFilter = ColorFilter.tint(icons.opponentTint.toColor())
        )
    }
}

@Composable
fun ViewTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(modifier = modifier
            .height(200.ldp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text= text,
            style = textTitle,
            textAlign = TextAlign.Center,
            fontFamily = getSportFontFamily()
        )
    }
}

@Composable
fun TopBar(
    height: Dp,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height)
    ) {
        DarkBackground(innerBottomPadding = 20.ldp) {
            Row {
                content()
            }
        }
        StepDecoration(modifier = Modifier.padding(top = height - 20.ldp))
    }
}

@Composable
fun CodeShower(
    code: String,
    height: Dp,
    leftGradientColor: Color,
    rightGradientColor: Color,
    inputLeftGradientColor: Color,
    inputRightGradientColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .gradientBackground(
                listOf(leftGradientColor, rightGradientColor),
                angle = 0f
            )
            .padding(horizontal = 16.ldp, vertical = 12.ldp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.ldp, Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Code:",
                style = buttonStyle,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .gradientBackground(
                        listOf(inputLeftGradientColor, inputRightGradientColor),
                        angle = 0f
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = code,
                    style = buttonStyle,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
