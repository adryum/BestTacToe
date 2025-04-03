package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.ui.CornerRadius
import com.testdevlab.besttactoe.ui.theme.Black
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.DarkGreen
import com.testdevlab.besttactoe.ui.theme.DarkOrange
import com.testdevlab.besttactoe.ui.theme.Green
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.buttonStyle
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.pxToDp
import de.drick.compose.hotpreview.HotPreview
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

//
//@Composable
//fun TextButton(
//    modifier: Modifier = Modifier,
//    textModifier: Modifier = Modifier,
//    text: String,
//    onClick: () -> Unit,
//) {
//    Box(
//        modifier = modifier
//            .width(avgButtonWidth.ldp)
//            .height(avgButtonHeight.ldp)
//            .clip(shape = RoundedCornerShape(100.ldp))
//            .clickable { onClick() }
//            .background(color = lightRed)
//            .border(width = 4.ldp, color = white_60, shape = RoundedCornerShape(100.ldp))
//        ,
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            modifier = textModifier,
//            text = text,
//            style = textMedium
//        )
//    }
//}
//
//@Composable
//fun IconTextButton(
//    modifier: Modifier = Modifier,
//    textModifier: Modifier = Modifier,
//    isBad: Boolean = false,
//    text: String,
//    @DrawableRes icon: DrawableResource,
//    onClick: () -> Unit,
//) {
//    Box(
//        modifier = modifier
//            .width(avgButtonWidth.ldp)
//            .height(avgButtonHeight.ldp)
//            .clip(shape = RoundedCornerShape(100.ldp))
//            .clickable { onClick() }
//            .border(width = 4.ldp, color = white_60, shape = RoundedCornerShape(100.ldp))
//            .background(color = if (isBad) lightRed else lightBlue)
//            .padding(12.ldp)
//        ,
//        contentAlignment = Alignment.Center
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.ldp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                modifier = Modifier.size(24.ldp),
//                imageVector = vectorResource(icon),
//                contentDescription = null,
//                colorFilter = ColorFilter.tint(Black)
//            )
//            Text(
//                modifier = textModifier.weight(1f),
//                text = text,
//                style = textIconButton
//            )
//        }
//
//    }
//}
//
//
//@Composable
//fun IconButton(
//    modifier: Modifier = Modifier,
//    @DrawableRes icon: DrawableResource?,
//    borderRadius: Dp = 0.ldp,
//    onClick: () -> Unit,
//) {
//    Box(
//        modifier = modifier
//            .aspectRatio(1f/1f)
//            .clip(shape = RoundedCornerShape(borderRadius))
//            .clickable { onClick() }
//        ,
//        contentAlignment = Alignment.Center
//    ) {
//        if (icon != null)
//        Image(
//            modifier = Modifier.fillMaxSize(),
//            imageVector = vectorResource(icon),
//            contentDescription = null,
//            colorFilter = ColorFilter.tint(Black)
//        )
//    }
//}
//
//@Composable
//fun ToggleButton(
//    modifier: Modifier = Modifier,
//    textModifier: Modifier = Modifier,
//    isEnabled: Boolean = false,
//    text: String,
//    @DrawableRes icon: DrawableResource = Res.drawable.ic_checkmark,
//    onClick: () -> Unit,
//) {
//    Box(
//        modifier = modifier
//            .width(avgButtonWidth.ldp)
//            .height(avgButtonHeight.ldp)
//            .clip(shape = RoundedCornerShape(20.ldp))
//            .border(width = 4.ldp, color = white_60, shape = RoundedCornerShape(20.ldp))
//            .clickable {
//                onClick()
//            }
//            .background( if (isEnabled) lightBlue else lightRed)
//            .padding(12.ldp)
//        ,
//        contentAlignment = Alignment.Center
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.ldp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier.background(white_60).size(24.ldp),
//                contentAlignment = Alignment.Center
//            ) {
//                if (isEnabled)
//                Image(
//                    modifier = Modifier.size(24.ldp),
//                    imageVector = vectorResource(icon),
//                    contentDescription = null,
//                    colorFilter = ColorFilter.tint(Black)
//                )
//            }
//
//            Text(
//                modifier = textModifier.weight(1f),
//                text = text,
//                style = textIconButton
//            )
//        }
//
//    }
//}
//
//
//@Composable
//fun CubeButton(
//    modifier: Modifier = Modifier,
//    textModifier: Modifier = Modifier,
//    isBad: Boolean = false,
//    text: String,
//    @DrawableRes icon: DrawableResource,
//    onClick: () -> Unit,
//) {
//    Box(
//        modifier = modifier
//            .size(100.ldp)
//            .clip(shape = RoundedCornerShape(20.ldp))
//            .clickable { onClick() }
//            .border(width = 4.ldp, color = white_60, shape = RoundedCornerShape(20.ldp))
//            .background(color = if (isBad) lightRed else lightBlue)
//            .padding(12.ldp)
//        ,
//        contentAlignment = Alignment.Center
//    ) {
//        Column (
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                modifier = Modifier.size(24.ldp),
//                imageVector = vectorResource(icon),
//                contentDescription = null,
//                colorFilter = ColorFilter.tint(Black)
//            )
//            Text(
//                modifier = textModifier,
//                text = text,
//                style = textIconButton
//            )
//        }
//
//    }
//}

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

@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String = "abc",
    height: Dp = 75.ldp,
    width: Dp? = null,
    leftGradientColor: Color,
    rightGradient: Color,
    enableShadow: Boolean = true,
    shadowOpacity: Float = .65f,
    shadowOffset: Dp = 8.ldp,
    textStyle: TextStyle = buttonStyle,
    onClick: () -> Unit
) {
    Box(
        modifier = if (width == null) Modifier.fillMaxWidth(.7f) else Modifier.width(width)
    ) {
        // button
        Box(
            modifier = modifier
                .zIndex(2f)
                .height(height)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Brush.linearGradient(listOf(leftGradientColor, rightGradient)))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = text,
                style = textStyle,
                textAlign = TextAlign.Center
            )
        }

        if (!enableShadow) return
        // shadow
        Box(
            modifier = modifier
                .zIndex(1f)
                .padding(top = shadowOffset)
                .height(height)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(
                    Brush.linearGradient(listOf(leftGradientColor, rightGradient)),
                    alpha = shadowOpacity
                )
        )
    }
}

@Composable
fun SideButtonWithImage(
    modifier: Modifier = Modifier,
    text: String = "abc",
    icon: DrawableResource,
    cornerRadius: CornerRadius,
    iconColor: Color = Black,
    iconPadding: Dp = 0.dp,
    isEnabled: Boolean,
    leftGradientColor: Color,
    rightGradient: Color,
    enableShadow: Boolean = true,
    shadowOpacity: Float = .65f,
    shadowOffset: Dp = 8.ldp,
    textStyle: TextStyle = buttonStyle,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        // button
        Box(
            modifier = Modifier
                .zIndex(2f)
                .height(75.ldp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(
                    cornerRadius.topLeft,
                    cornerRadius.topRight,
                    cornerRadius.bottomRight,
                    cornerRadius.bottomLeft
                ))
                .background(Brush.linearGradient(listOf(leftGradientColor, rightGradient)))
                .clickable(enabled = isEnabled) { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                modifier = Modifier.padding(iconPadding),
                painter = painterResource(icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(iconColor)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 40.ldp, vertical = 12.ldp),
                text = text,
                style = textStyle,
                textAlign = TextAlign.Left
            )
        }

        if (!enableShadow) return
        // shadow
        Box(
            modifier = Modifier
                .zIndex(1f)
                .padding(top = shadowOffset)
                .height(75.ldp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(0, 50, 50))
                .background(
                    Brush.linearGradient(listOf(leftGradientColor, rightGradient)),
                    alpha = shadowOpacity
                )
        )
    }
}

@Composable
fun LeftSideButton(
    modifier: Modifier = Modifier,
    text: String = "abc",
    leftGradientColor: Color,
    rightGradient: Color,
    enableShadow: Boolean = true,
    shadowOpacity: Float = .65f,
    shadowOffset: Dp = 8.ldp,
    textStyle: TextStyle = buttonStyle,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(.7f)
    ) {
        // button
        Box(
            modifier = modifier
                .zIndex(2f)
                .height(75.ldp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(0, 50, 50))
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
        }

        if (!enableShadow) return
        // shadow
        Box(
            modifier = Modifier
                .zIndex(1f)
                .padding(top = shadowOffset)
                .height(75.ldp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(0, 50, 50))
                .background(
                    Brush.linearGradient(listOf(leftGradientColor, rightGradient)),
                    alpha = shadowOpacity
                )
        )
    }
}

@HotPreview(name = "long button", widthDp = 500, heightDp = 1000)
@Composable
fun LongButtonPreview() {
    Surface(color = Black35) {
        Column {
            LeftSideButton(
                leftGradientColor = Orange,
                rightGradient = Yellow,
                enableShadow = true,
                onClick = {}
            )
        }
    }
}