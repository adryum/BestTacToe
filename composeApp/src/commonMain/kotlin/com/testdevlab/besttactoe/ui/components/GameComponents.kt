package com.testdevlab.besttactoe.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_back_rounded
import besttactoe.composeapp.generated.resources.ic_equals
import com.testdevlab.besttactoe.ui.GameResult
import com.testdevlab.besttactoe.ui.GameResultModel
import com.testdevlab.besttactoe.ui.IconUIModel
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.BlueList
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import com.testdevlab.besttactoe.ui.theme.Red
import com.testdevlab.besttactoe.ui.theme.RedList
import com.testdevlab.besttactoe.ui.theme.White
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.idleBreathing
import com.testdevlab.besttactoe.ui.theme.isDraw
import com.testdevlab.besttactoe.ui.theme.isLoss
import com.testdevlab.besttactoe.ui.theme.isVictory
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.popIn
import com.testdevlab.besttactoe.ui.theme.pxToDp
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.textMedium
import com.testdevlab.besttactoe.ui.theme.white_60
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
            Text(
                text = gameResult.name + if (gameResult.result.isDraw()) "" else " is Victorious!",
                style = textLarge,
                fontFamily = getSportFontFamily()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.ldp)
                    .height(80.ldp),
                horizontalArrangement = Arrangement.spacedBy(32.ldp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    colorGradient = OrangeYellowList,
                    text = "Rematch",
                    buttonType = ButtonType.Center,
                    onClick = onPlayAgainClick,
                    textStyle = textMedium
                )
                Button(
                    modifier = Modifier.weight(1f),
                    colorGradient = DarkOrangeOrangeList,
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
    colorList: List<Color>,
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
            colorGradient = colorList,
            isPlayerTurn = isPlayerTurn,
            icon = playerIcon,
            iconPadding = 15.ldp,
            iconColor = playerIconColor,
            textStyle = textMedium,
            showerType = MoveShowerType.LeftSide,
            width = width.pxToDp(),
            contentAlignment = Alignment.CenterEnd,
            horizontalPadding = 2.ldp
        )
        MoveShower(
            text = "'s turn",
            colorGradient = colorList,
            isPlayerTurn = !isPlayerTurn,
            icon = opponentIcon,
            iconColor = opponentIconColor,
            iconPadding = 15.ldp,
            textStyle = textMedium,
            showerType = MoveShowerType.RightSide,
            width = width.pxToDp(),
            contentAlignment = Alignment.CenterStart,
            horizontalPadding = 2.ldp
        )
    }
}

@Composable
fun PointShower(
    modifier: Modifier = Modifier,
    res: DrawableResource,
    tint: Color,
    results: List<GameResult>,
    maxPoints: Int,
    isPlayer: Boolean,
    reverseList: Boolean = false
) {
    val newResults = results.filter {
        (if (isPlayer) it.isVictory() else it.isLoss()) || it.isDraw()
    }
    val lastPointIndex = maxPoints - 1

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.ldp)
    ) {
        for (i in 0.. lastPointIndex) {
            val cycle = if (reverseList) lastPointIndex - i else i
            val isLastPoint = cycle == lastPointIndex

            val resource =
                if(cycle >= newResults.size) null
                else if (newResults[cycle].isDraw()) Res.drawable.ic_equals
                else res

            val pointTint =
                if (cycle >= newResults.size) White
                else if (newResults[cycle].isDraw()) GrayDark
                else tint

            val pointModifier = Modifier
                .size(24.ldp)
                .then(other =
                    if (isLastPoint)
                        Modifier.idleBreathing(
                            toScale = 1.1f,
                            breatheInTime = 1000,
                            easing = LinearEasing
                        )
                    else Modifier
                )

            Point(
                modifier = pointModifier,
                res = resource,
                tint = pointTint,
                bgColor = if (isLastPoint) Yellow else White
            )

//            if (cycle >= newResults.size) {
//                Point(
//                    modifier = Modifier.size(24.ldp)
//                        .then(other = Modifier.idleBreathing(toScale = 1.1f, breatheInTime = 2000, easing = LinearEasing))
//                        .then(other = if (isLastPoint)
//                            Modifier.idleBreathing(toScale = 1.1f, breatheInTime = 2000, easing = LinearEasing) else Modifier),
//                    res = null,
//                    bgColor = if (isLastPoint) Yellow else White
//                )
//            } else {
//                // player wins draws wins ect.
//                // opponent losses draws draws losses ect.
//                Point(
//                    modifier = Modifier.size(24.ldp),
//                    res = if (newResults[cycle].isDraw()) {
//                        Res.drawable.ic_equals
//                    } else {
//                        res
//                    },
//                    tint = if (newResults[cycle].isDraw()) {
//                        GrayDark
//                    } else {
//                        tint
//                    }
//                )
//            }
        }
    }
}

@Composable
fun ImageWithBackground(
    backgroundModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    res: DrawableResource?,
    tint: Color = White
) {
    Box(
        modifier = backgroundModifier,
        contentAlignment = Alignment.Center
    ) {
        if (res == null) return

        WrappedImage(
            modifier = imageModifier,
            res = res,
            tint = tint
        )
    }
}

@Composable
fun Point(
    modifier: Modifier = Modifier,
    res: DrawableResource?,
    tint: Color = White,
    bgColor: Color = White
) {
    ImageWithBackground(
        backgroundModifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(4.ldp))
            .background(bgColor),
        imageModifier = Modifier
            .fillMaxSize()
            .padding(2.ldp)
            .popIn(damping = Spring.DampingRatioHighBouncy),
        res = res,
        tint = tint
    )
}

@Composable
fun NameCard(
    modifier: Modifier = Modifier,
    name: String,
    colors: List<Color>,
    icon: IconUIModel,
    results: List<GameResult>,
    maxPoints: Int,
    side: ButtonType,
    isPlayerTurn: Boolean,
    isPlayer: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (side == ButtonType.RightSide) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.ldp)
    ) {
        PointShower(
            modifier = Modifier.padding(horizontal = 8.ldp),
            res = icon.res,
            tint = icon.tint,
            results = results,
            maxPoints = maxPoints,
            isPlayer = isPlayer,
            reverseList = !isPlayer
        )

        Button(
            modifier = Modifier
                .idleBreathing(
                    fromScale = 1f,
                    toScale = 1.05f,
                    breatheInTime = 500,
                    enabled = isPlayerTurn
                ),
            colorGradient = colors,
            text = name,
            isClickable = false,
            onClick = {},
            textStyle = textMedium,
            verticalPadding = 12.ldp,
            horizontalPadding = 10.ldp,
            buttonType = side,
            contentAlignment = Alignment.Center
        )
    }
}

@Composable
fun PhoneName(
    modifier: Modifier = Modifier,
    baseColor: Color = Red,
    shadowColor: Color = GrayDark,
    name: String = "I HAVE NO NAME",
    isInverted: Boolean = false,
    shadowsTopOffset: Float = 10f
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                if (isInverted)
                    rotationY = 180f
            }
            .drawBehind {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width - 8.dp.toPx(), size.height)
                    lineTo(0f, size.height)
                    close()
                }

                val shadowsPath = Path().apply {
                    moveTo(0f, shadowsTopOffset)
                    lineTo(size.width + shadowsTopOffset / 2, shadowsTopOffset)
                    lineTo(size.width - 8.dp.toPx() + shadowsTopOffset / 2, size.height + shadowsTopOffset)
                    lineTo(0f, size.height + shadowsTopOffset)
                    close()
                }

                drawPath(
                    path = shadowsPath,
                    color = shadowColor
                )

                drawPath(
                    path = path,
                    color = baseColor
                )
            }
            .padding(
                start = 16.ldp,
                end = 12.ldp,
                top = 4.ldp,
                bottom = 4.ldp
            )
    ) {
        Text(
            modifier = Modifier
                .graphicsLayer {
                    if (isInverted)
                        rotationY = 180f
                },
            text = name,
            style = textLarge.copy(
                fontFamily = getSportFontFamily(),
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun TopbarSide(
    modifier: Modifier = Modifier,
    side: ButtonType,
    name: String,
    isPlayerTurn: Boolean,
    isInverted: Boolean,
    icon: IconUIModel,
    results: List<GameResult>,
    maxPoints: Int,
    isPlayer: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (side == ButtonType.RightSide) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.ldp)
    ) {
        PointShower(
            modifier = Modifier.padding(horizontal = 8.ldp),
            res = icon.res,
            tint = icon.tint,
            results = results,
            maxPoints = maxPoints,
            isPlayer = isPlayer,
            reverseList = isInverted
        )
        PhoneName(
            modifier = Modifier.fillMaxWidth().then(
                if (isPlayerTurn) Modifier.idleBreathing(
                    fromScale = 1f,
                    toScale = 1.05f,
                    breatheInTime = 500,
                ) else Modifier),
            name = name,
            isInverted = isInverted,
            baseColor = if (isPlayer) Blue else Red
        )
    }
}

@Composable
fun GamesTopBarPhone(
    playerName: String,
    opponentName: String,
    isPlayerTurn: Boolean,
    roundCount: Int,
    playerIconData: IconUIModel,
    opponentIconData: IconUIModel,
    results: List<GameResult>,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TopbarSide(
            modifier = Modifier.weight(1f),
            name = playerName,
            results = results,
            maxPoints = roundCount,
            side = ButtonType.LeftSide,
            icon = playerIconData,
            isPlayerTurn = isPlayerTurn,
            isPlayer = true,
            isInverted = false
        )

        Box(modifier = Modifier.weight(.1f))

        TopbarSide(
            modifier = Modifier.weight(1f),
            name = opponentName,
            results = results,
            maxPoints = roundCount,
            side = ButtonType.RightSide,
            icon = opponentIconData,
            isPlayerTurn = !isPlayerTurn,
            isPlayer = false,
            isInverted = true
        )
    }
}

@Composable
fun AccessibilityRow(
    modifier: Modifier = Modifier,
    code: String?,
    onGoBack: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.ldp, Alignment.CenterHorizontally)
    ) {
        if (code != null)
            Code(
                modifier = Modifier,
                code = code
            )

        AccessibilityButtons(
            onGoBack = onGoBack
        )
    }
}

@Composable
fun GamesTopBar(
    modifier: Modifier = Modifier,
    roundCount: Int,
    playerName: String,
    opponentName: String,
    playerIconData: IconUIModel,
    opponentIconData: IconUIModel,
    code: String?,
    results: List<GameResult>,
    isPlayerTurn: Boolean,
    onGoBack: () -> Unit,
) {
    Row(
        modifier = modifier.padding(vertical = 16.ldp),
        horizontalArrangement = Arrangement.spacedBy(16.ldp, Alignment.CenterHorizontally)
    ) {
        NameCard(
            modifier = Modifier.weight(1f),
            name = playerName,
            colors = BlueList,
            results = results,
            maxPoints = roundCount,
            side = ButtonType.LeftSide,
            icon = playerIconData,
            isPlayerTurn = isPlayerTurn,
            isPlayer = true
        )

        AccessibilityActions(
            modifier = Modifier.weight(.7f),
            code = code,
            onGoBack = onGoBack
        )

        NameCard(
            modifier = Modifier.weight(1f),
            name = opponentName,
            colors = RedList,
            results = results,
            maxPoints = roundCount,
            side = ButtonType.RightSide,
            icon = opponentIconData,
            isPlayerTurn = !isPlayerTurn,
            isPlayer = false
        )
    }
}

@Composable
fun AccessibilityActions(
    modifier: Modifier = Modifier,
    code: String?,
    onGoBack: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.ldp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (code != null)
            Code(
                modifier = Modifier,
                code = code
            )

        AccessibilityButtons(
            onGoBack = onGoBack
        )
    }
}

@Composable
fun AccessibilityButtons(
    onGoBack: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ImageWithBackground(
            backgroundModifier = Modifier
                .size(40.ldp)
                .clip(RoundedCornerShape(8.ldp))
                .border(width = 1.ldp, color = white_60, shape = RoundedCornerShape(8.ldp))
                .gradientBackground(OrangeYellowList, -30f)
                .clickable { onGoBack() }
                .padding(2.ldp),
            res = Res.drawable.ic_back_rounded,
        )
    }
}