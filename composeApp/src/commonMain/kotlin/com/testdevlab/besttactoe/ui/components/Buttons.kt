package com.testdevlab.besttactoe.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.ui.theme.Black
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.DarkGreen
import com.testdevlab.besttactoe.ui.theme.DarkGreenList
import com.testdevlab.besttactoe.ui.theme.DarkOrange
import com.testdevlab.besttactoe.ui.theme.Green
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import com.testdevlab.besttactoe.ui.theme.RedList
import com.testdevlab.besttactoe.ui.theme.buttonStyle
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.pxToDp
import de.drick.compose.hotpreview.HotPreview
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    text: String = "abc",
    isEnabled: Boolean,
    leftGradientColor: Color,
    rightGradient: Color,
    enableShadow: Boolean = true,
    shadowOpacity: Float = .65f,
    shadowOffset: Dp = 8.ldp,
    textStyle: TextStyle = buttonStyle,
    onClick: () -> Unit
) {
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .fillMaxWidth(.7f)
            .clip(RoundedCornerShape(50))
            .background(Black)
    ) {
        // button
        Box(
            modifier = modifier
                .zIndex(2f)
                .height(75.ldp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Brush.linearGradient(listOf(leftGradientColor, rightGradient)))
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 40.ldp, vertical = 12.ldp),
                text = text,
                style = textStyle,
                textAlign = TextAlign.Left
            )
            // on off LED light
            Box(
                modifier = Modifier
                    .padding(12.ldp)
                    .align(Alignment.CenterEnd)
                    .aspectRatio(1f)
                    .fillMaxHeight(6f)
                    .clip(RoundedCornerShape(50))
                    .background(if (isEnabled) DarkGreen else DarkOrange)
                    .onSizeChanged { buttonSize = it },
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .size(
                            width = buttonSize.width.pxToDp() - 4.ldp,
                            height = buttonSize.height.pxToDp() - 4.ldp
                        )
                        .clip(RoundedCornerShape(50))
                        .background(if (isEnabled) Green else Orange)
                )
            }
        }

        if (!enableShadow) return
        // shadow
        Box(
            modifier = Modifier
                .zIndex(1f)
                .padding(top = shadowOffset)
                .height(75.ldp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(
                    Brush.linearGradient(listOf(leftGradientColor, rightGradient)),
                    alpha = shadowOpacity
                )
        )
    }
}

enum class MoveShowerType {
    LeftSide,
    RightSide
}

@Composable
fun MoveShower(
    containerModifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    text: String = "abc",
    showerType: MoveShowerType,
    isPlayerTurn: Boolean,
    width: Dp,
    // shadow
    enableShadow: Boolean = true,
    shadowOpacity: Float = .65f,
    shadowOffset: Dp = 8.ldp,
    // style
    textStyle: TextStyle = buttonStyle,
    horizontalPadding: Dp = 20.ldp,
    verticalPadding: Dp = 12.ldp,
    colorGradient: List<Color>,
    contentAlignment: Alignment? = null,
    // icon
    iconPadding: Dp,
    iconColor: Color,
    icon: DrawableResource,
) {
    var buttonHeight by remember { mutableStateOf(0) }
    val shape = when (showerType) {
        MoveShowerType.LeftSide ->
            RoundedCornerShape(0, 50, 50, 0)
        MoveShowerType.RightSide ->
            RoundedCornerShape(50, 0, 0, 50)
    }

    val animatedWidth by animateDpAsState(
        targetValue = if (isPlayerTurn) width * 7 / 10 else width * 2 / 10,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Box(
        modifier = containerModifier
            .width(animatedWidth)
            .clip(shape)
            .background(Black)
    ) {
        // button
        Box(
            modifier = buttonModifier
                .zIndex(2f)
                .fillMaxWidth()
                .clip(shape)
                .background(Brush.linearGradient(colorGradient))
                .onSizeChanged { buttonHeight = it.height },
            contentAlignment =
                contentAlignment
                    ?: when (showerType) {
                        MoveShowerType.LeftSide ->  Alignment.CenterStart
                        MoveShowerType.RightSide -> Alignment.CenterEnd
                    }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showerType == MoveShowerType.RightSide)
                    Image(
                        modifier = Modifier.padding(iconPadding).requiredSize(24.ldp),
                        painter = painterResource(icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(iconColor)
                    )
                if (isPlayerTurn)
                    Text(
                        modifier = Modifier
                            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
                        text = text,
                        style = textStyle,
                        textAlign = when (showerType) {
                            MoveShowerType.LeftSide -> TextAlign.Left
                            MoveShowerType.RightSide -> TextAlign.Right
                        },
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    )
                if (showerType == MoveShowerType.LeftSide)
                    Image(
                        modifier = Modifier.padding(iconPadding).requiredSize(24.ldp),
                        painter = painterResource(icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(iconColor)
                    )
            }

        }

        if (!enableShadow) return
        // shadow
        Box(
            modifier = buttonModifier
                .zIndex(1f)
                .padding(top = shadowOffset)
                .height(buttonHeight.pxToDp())
                .fillMaxWidth()
                .clip(shape)
                .background(
                    Brush.linearGradient(colorGradient),
                    alpha = shadowOpacity
                )
        )
    }
}

enum class ButtonType {
    LeftSide,
    Center,
    RightSide
}

@Composable
fun Button(
    containerModifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    text: String = "abc",
    buttonType: ButtonType,
    isClickable: Boolean = true,
    // shadow
    enableShadow: Boolean = true,
    shadowOpacity: Float = .65f,
    shadowOffset: Dp = 8.ldp,
    // style
    textStyle: TextStyle = buttonStyle,
    horizontalPadding: Dp = 40.ldp,
    verticalPadding: Dp = 12.ldp,
    colorGradient: List<Color>,
    contentAlignment: Alignment? = null,
    onClick: () -> Unit
) {
    val shape = when (buttonType) {
        ButtonType.LeftSide ->
            RoundedCornerShape(
                topStartPercent = 0, topEndPercent = 50,
                bottomEndPercent = 50, bottomStartPercent = 0
            )
        ButtonType.Center ->
            RoundedCornerShape(
                topStartPercent = 50, topEndPercent = 50,
                bottomEndPercent = 50, bottomStartPercent = 50
            )
        ButtonType.RightSide ->
            RoundedCornerShape(
                topStartPercent = 50, topEndPercent = 0,
                bottomEndPercent = 0, bottomStartPercent = 50
            )
    }
    var buttonHeight by remember { mutableStateOf(0) }

    Box(
        modifier = containerModifier
            .clip(shape)
            .background(Black)
    ) {
        // button
        Box(
            modifier = buttonModifier
                .zIndex(2f)
                .fillMaxWidth()
                .clip(shape)
                .background(Brush.linearGradient(colorGradient))
                .clickable(enabled = isClickable) { onClick() }
                .onSizeChanged { buttonHeight = it.height },
            contentAlignment =
                contentAlignment
                    ?: when (buttonType) {
                        ButtonType.LeftSide -> Alignment.CenterStart
                        ButtonType.Center -> Alignment.Center
                        ButtonType.RightSide -> Alignment.CenterEnd
                    }
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding, vertical = verticalPadding),
                text = text,
                style = textStyle,
                textAlign = when (buttonType) {
                    ButtonType.LeftSide -> TextAlign.Left
                    ButtonType.Center -> TextAlign.Center
                    ButtonType.RightSide -> TextAlign.Right
                },
                fontFamily = getSportFontFamily()
            )
        }

        if (!enableShadow) return
        // shadow
        Box(
            modifier = buttonModifier
                .zIndex(1f)
                .padding(top = shadowOffset)
                .height(buttonHeight.pxToDp())
                .fillMaxWidth()
                .clip(shape)
                .background(
                    Brush.linearGradient(colorGradient),
                    alpha = shadowOpacity
                )
        )
    }
}

@HotPreview(name = "button", widthDp = 500, heightDp = 1000)
@Composable
fun ButtonPreview() {
    Surface(color = Black35) {
        Column {
            Button(
                colorGradient = OrangeYellowList,
                enableShadow = true,
                buttonType = ButtonType.LeftSide,
                onClick = {}
            )
            Button(
                colorGradient = RedList,
                enableShadow = true,
                buttonType = ButtonType.Center,
                onClick = {}
            )
            Button(
                colorGradient = DarkGreenList,
                enableShadow = true,
                buttonType = ButtonType.RightSide,
                onClick = {}
            )
        }
    }
}