package com.testdevlab.besttactoe.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.ui.CornerRadius
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.TransparentDark
import com.testdevlab.besttactoe.ui.theme.buttonStyle
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.textSmall
import com.testdevlab.besttactoe.ui.theme.textTitle
import com.testdevlab.besttactoe.ui.theme.white_60
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TurnShower(
    modifier: Modifier = Modifier,
    leftGradientColor: Color,
    rightGradientColor: Color,
    playerIcon: DrawableResource,
    playerIconColor: Color,
    opponentIconColor: Color,
    isPlayerTurn: Boolean,
    opponentIcon: DrawableResource,
) {
    // IDEA: on player move, this text scrolls to left and other players text scrolls in from the left
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.ldp)
    ) {
        SideButtonWithImage(
            modifier = if (isPlayerTurn)
                Modifier.fillMaxWidth(.7f) else Modifier.width(100.ldp),
            text = "Your turn",
            cornerRadius = CornerRadius(0, 50, 50, 0),
            leftGradientColor = leftGradientColor,
            rightGradient = rightGradientColor,
            isEnabled = false,
            icon = playerIcon,
            iconColor = playerIconColor,
            onClick = {}
        )
        SideButtonWithImage(
            modifier = if (isPlayerTurn)
                 Modifier.width(100.ldp) else Modifier.fillMaxWidth(.7f),
            text = "'s turn",
            cornerRadius = CornerRadius(50, 0 ,0 ,50),
            leftGradientColor = leftGradientColor,
            rightGradient = rightGradientColor,
            isEnabled = false,
            icon = opponentIcon,
            iconColor = opponentIconColor,
            onClick = {}
        )
    }
}

@Composable
fun GamesTopBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    backgroundColor: Color = white_60,
    @DrawableRes playerIcon: DrawableResource,
    height: Dp = 80.ldp,
    score: ScoreModel,
    time: String
) {
    Box(modifier = modifier.height(height).background(backgroundColor)) {
        Row {
            GamesTopBarElement(
                text = "You are",
                content = {
                    Image(
                        modifier = Modifier.aspectRatio(1f/1f),
                        painter = painterResource(playerIcon),
                        contentDescription = null,
                    )
                }
            )
            GamesTopBarElement(
                text = "Score",
                content = {
                    Row {
                        // 3 different text components, so they space out evenly when someone gets really high score
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${score.playerScore}",
                            style = textSmall
                        )
                        Text(
                            modifier = Modifier.weight(.3f),
                            text = "|",
                            style = textSmall
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${score.enemyScore}",
                            style = textSmall
                        )
                    }
                }
            )
            GamesTopBarElement(
                text = "You are",
                content = {
                    Text(time, style = textSmall)
                }
            )
        }
    }
}

@Composable
fun GamesTopBarElement(
    modifier: Modifier = Modifier,
    text: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = textSmall,
            textAlign = TextAlign.Center
        )
        content()
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
            textAlign = TextAlign.Center
        )
    }
}

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
    content: @Composable () -> Unit
) {
    var highestTopPadding = 0.dp
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(columnSpaceBy, columnAlignment),
            ) {
                content()
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
        DarkBackground() {
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
            .gradientBackground(listOf(leftGradientColor, rightGradientColor), 0f)
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
                    .gradientBackground(listOf(inputLeftGradientColor, inputRightGradientColor), 0f),
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