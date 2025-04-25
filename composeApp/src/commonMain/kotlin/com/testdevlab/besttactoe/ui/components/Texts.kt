package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SpacedBetweenTexts(
    modifier: Modifier = Modifier,
    leftText: String,
    rightText: String,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Center,
    fontFamily: FontFamily = FontFamily.Default
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = leftText,
            style = style,
            textAlign = textAlign,
            fontFamily = fontFamily
        )
        Text(
            text = rightText,
            style = style,
            textAlign = textAlign,
            fontFamily = fontFamily
        )
    }
}

@Composable
fun OutlinedText(
    modifier: Modifier,
    text: String,
    style: TextStyle,
    outlineColor: Color,
    outlineWidthPx: Float = 5f,
    miter: Float = 10f,
    join: StrokeJoin = StrokeJoin.Round
) {
    Box {
        Text(
            modifier = modifier,
            text = text,
            style = style
        )
        Text(
            modifier = modifier,
            text = text,
            style = style.copy(
                color = outlineColor,
                drawStyle = androidx.compose.ui.graphics.drawscope.Stroke(
                    miter = miter,
                    width = outlineWidthPx,
                    join = join
                )
            )
        )
    }
}