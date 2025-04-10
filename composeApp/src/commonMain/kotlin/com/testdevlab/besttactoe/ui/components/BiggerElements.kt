package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.testdevlab.besttactoe.ui.theme.buttonStyle
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.textTitle


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
            textAlign = TextAlign.Center
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
