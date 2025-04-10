package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.TransparentDark
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.ldp

@Composable
fun StepDecoration(
    modifier: Modifier = Modifier,
    height: Dp = 40.ldp,
    angle: Float = -90f,
    startColor: Color = Black35,
    endColor: Color = TransparentDark,
) {
    Box(modifier = modifier
        .height(height)
        .fillMaxWidth()
        .gradientBackground(listOf(startColor, endColor), angle)
    )
}

@Composable
fun MultipleStepDecorations(
    count: Int,
    height: Dp = 40.ldp,
    angle: Float = -90f,
    startColor: Color = Black35,
    endColor: Color = TransparentDark,
) {
    Box {
        for (i in 0 ..< count) {
            Box(modifier = Modifier
                .zIndex(i.toFloat())
                .padding(top = height / 2 * i) // each box is placed lower
                .height(height)
                .fillMaxWidth()
                .gradientBackground(listOf(startColor, endColor), angle)
            )
        }
    }
}

@Composable
fun MultipleStepDecorationsWithDarkContentAndColumn(
    count: Int,
    height: Dp = 40.ldp,
    angle: Float = -90f,
    startColor: Color = Black35,
    endColor: Color = TransparentDark,
    columnSpaceBy: Dp = 20.ldp,
    columnAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    var highestTopPadding = 0.dp
    var maxWidth by remember { mutableStateOf(0) }

    Box {
        for (i in 1 .. count) {
            Box(modifier = Modifier
                .zIndex(i.toFloat())
                .padding(top = highestTopPadding) // each box is placed lower
                .height(height)
                .fillMaxWidth()
                .gradientBackground(listOf(startColor, endColor), angle)
            )
            highestTopPadding = height / 2 * i
        }
        DarkBackground(
            modifier = Modifier.padding(top = highestTopPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(vertical = 16.ldp),
                verticalArrangement = Arrangement.spacedBy(columnSpaceBy, columnAlignment),
            ) {
                Box(modifier = Modifier.fillMaxWidth().onSizeChanged { maxWidth = it.width })
                content(maxWidth)
            }
        }
    }
}

@Composable
fun DarkBackground(
    modifier: Modifier = Modifier,
    innerBottomPadding: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Black35)
            .padding(bottom = innerBottomPadding)
        ,
    ) {
        content()
    }
}

@Composable
fun DarkBackgroundWithDarkTop(
    modifier: Modifier = Modifier,
    columnSpaceBy: Dp = 20.ldp,
    verticalColumnAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalColumnAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
    ) {
        StepDecoration(modifier = Modifier, angle = 90f)
        DarkBackground {
            Column(
                modifier = Modifier.padding(top = 30.ldp).fillMaxSize(),
                horizontalAlignment = horizontalColumnAlignment,
                verticalArrangement = Arrangement.spacedBy(columnSpaceBy, verticalColumnAlignment)
            ) {
                content()
            }
        }
    }
}
