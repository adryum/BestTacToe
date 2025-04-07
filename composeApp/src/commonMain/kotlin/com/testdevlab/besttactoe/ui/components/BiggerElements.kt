package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.ui.GameResultModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkOrange
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.TransparentDark
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.buttonStyle
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.pxToDp
import com.testdevlab.besttactoe.ui.theme.textMedium
import com.testdevlab.besttactoe.ui.theme.textTitle
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun VictoryPopUp(
    modifier: Modifier = Modifier,
    gameResult: GameResultModel,
    onGoBackClick: () -> Unit,
    onPlayAgainClick: () -> Unit,
) {
    DarkBackground(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.ldp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (gameResult.isVictory) {
                Text(
                    text = "${gameResult.name!!} is Victorious!",
                    style = buttonStyle
                )
            } else {
                Text(
                    text = "!Draw!",
                    style = buttonStyle
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.ldp)
                    .height(80.ldp),
                horizontalArrangement = Arrangement.spacedBy(32.ldp)
            ) {
                Button(
                    containerModifier = Modifier.weight(1f),
                    leftGradientColor = Orange,
                    rightGradient = Yellow,
                    text = "Play Again!",
                    buttonType = ButtonType.Center,
                    onClick = onPlayAgainClick,
                    textStyle = textMedium
                )
                Button(
                    containerModifier = Modifier.weight(1f),
                    leftGradientColor = Color.Red,
                    rightGradient = DarkOrange,
                    text = "Leave",
                    buttonType = ButtonType.Center,
                    onClick = onGoBackClick,
                    textStyle = textMedium
                )
            }
        }
    }
}

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
    var width by remember { mutableStateOf(0) }
    Row(
        modifier = modifier
            .padding(vertical = 16.ldp)
            .fillMaxWidth()
            .onSizeChanged {
                width = it.width
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MoveShower(
            text = "Your turn",
            leftGradientColor = leftGradientColor,
            rightGradient = rightGradientColor,
            isPlayerTurn = isPlayerTurn,
            icon = playerIcon,
            iconPadding = 15.ldp,
            iconColor = playerIconColor,
            textStyle = textMedium,
            showerType = MoveShowerType.LeftSide,
            width = width.pxToDp(),
            contentAlignment = Alignment.CenterEnd
        )
        MoveShower(
            text = "'s turn",
            leftGradientColor = leftGradientColor,
            rightGradient = rightGradientColor,
            isPlayerTurn = !isPlayerTurn,
            icon = opponentIcon,
            iconColor = opponentIconColor,
            iconPadding = 15.ldp,
            textStyle = textMedium,
            showerType = MoveShowerType.RightSide,
            width = width.pxToDp(),
            contentAlignment = Alignment.CenterStart
        )
    }
}

@Composable
fun GamesTopBar(
    modifier: Modifier = Modifier,
    playerName: String,
    opponentName: String,
    score: ScoreModel
) {
    Row(
        modifier = modifier.padding(vertical = 16.ldp),
        horizontalArrangement = Arrangement.spacedBy(16.ldp, Alignment.CenterHorizontally)
    ) {
        Button(
            containerModifier = Modifier.weight(1f),
            leftGradientColor = Blue,
            rightGradient = Blue,
            text = playerName,
            isClickable = false,
            onClick = {},
            textStyle = textMedium,
            verticalPadding = 12.ldp,
            horizontalPadding = 10.ldp,
            buttonType = ButtonType.LeftSide,
            contentAlignment = Alignment.Center
        )

        Column (
            modifier = Modifier.weight(.7f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Score",
                style = textMedium,
                textAlign = TextAlign.Center
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${score.playerScore}",
                    style = textMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.weight(.05f),
                    text = "|",
                    style = textMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${score.opponentScore}",
                    style = textMedium,
                    textAlign = TextAlign.Center
                )
            }

        }

        Button(
            containerModifier = Modifier.weight(1f),
            leftGradientColor = Color.Red,
            rightGradient = Color.Red,
            text = opponentName,
            isClickable = false,
            onClick = {},
            textStyle = textMedium,
            verticalPadding = 12.ldp,
            horizontalPadding = 10.ldp,
            buttonType = ButtonType.RightSide,
            contentAlignment = Alignment.Center
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
    val scrollState = rememberScrollState()

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