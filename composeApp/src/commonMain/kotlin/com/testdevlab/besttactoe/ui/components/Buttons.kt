package com.testdevlab.besttactoe.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.drick.compose.hotpreview.HotPreview
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_checkmark
import besttactoe.composeapp.generated.resources.ic_human
import besttactoe.composeapp.generated.resources.ic_robot
import com.testdevlab.besttactoe.ui.theme.Black
import com.testdevlab.besttactoe.ui.theme.Black60
import com.testdevlab.besttactoe.ui.theme.avgButtonHeight
import com.testdevlab.besttactoe.ui.theme.avgButtonWidth
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightBlue
import com.testdevlab.besttactoe.ui.theme.lightRed
import com.testdevlab.besttactoe.ui.theme.textIconButton
import com.testdevlab.besttactoe.ui.theme.textMedium
import com.testdevlab.besttactoe.ui.theme.textNormal
import com.testdevlab.besttactoe.ui.theme.transparent
import com.testdevlab.besttactoe.ui.theme.white_60
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource


@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .width(avgButtonWidth.ldp)
            .height(avgButtonHeight.ldp)
            .clip(shape = RoundedCornerShape(100.ldp))
            .clickable { onClick() }
            .background(color = lightRed)
            .border(width = 4.ldp, color = white_60, shape = RoundedCornerShape(100.ldp))
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = textModifier,
            text = text,
            style = textMedium
        )
    }
}

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    isBad: Boolean = false,
    text: String,
    @DrawableRes icon: DrawableResource,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .width(avgButtonWidth.ldp)
            .height(avgButtonHeight.ldp)
            .clip(shape = RoundedCornerShape(100.ldp))
            .clickable { onClick() }
            .border(width = 4.ldp, color = white_60, shape = RoundedCornerShape(100.ldp))
            .background(color = if (isBad) lightRed else lightBlue)
            .padding(12.ldp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.ldp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.ldp),
                imageVector = vectorResource(icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Black)
            )
            Text(
                modifier = textModifier.weight(1f),
                text = text,
                style = textIconButton
            )
        }

    }
}


@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: DrawableResource?,
    borderRadius: Dp = 0.ldp,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f/1f)
            .clip(shape = RoundedCornerShape(borderRadius))
            .clickable { onClick() }
        ,
        contentAlignment = Alignment.Center
    ) {
        if (icon != null)
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = vectorResource(icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Black)
        )
    }
}

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    isEnabled: Boolean = false,
    text: String,
    @DrawableRes icon: DrawableResource = Res.drawable.ic_checkmark,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .width(avgButtonWidth.ldp)
            .height(avgButtonHeight.ldp)
            .clip(shape = RoundedCornerShape(20.ldp))
            .border(width = 4.ldp, color = white_60, shape = RoundedCornerShape(20.ldp))
            .clickable {
                onClick()
            }
            .background( if (isEnabled) lightBlue else lightRed)
            .padding(12.ldp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.ldp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.background(white_60).size(24.ldp),
                contentAlignment = Alignment.Center
            ) {
                if (isEnabled)
                Image(
                    modifier = Modifier.size(24.ldp),
                    imageVector = vectorResource(icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Black)
                )
            }

            Text(
                modifier = textModifier.weight(1f),
                text = text,
                style = textIconButton
            )
        }

    }
}


@Composable
fun CubeButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    isBad: Boolean = false,
    text: String,
    @DrawableRes icon: DrawableResource,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(100.ldp)
            .clip(shape = RoundedCornerShape(20.ldp))
            .clickable { onClick() }
            .border(width = 4.ldp, color = white_60, shape = RoundedCornerShape(20.ldp))
            .background(color = if (isBad) lightRed else lightBlue)
            .padding(12.ldp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(24.ldp),
                imageVector = vectorResource(icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Black)
            )
            Text(
                modifier = textModifier,
                text = text,
                style = textIconButton
            )
        }

    }
}


@HotPreview(name = "long button", widthDp = 500, heightDp = 1000)
@Composable
fun LongButtonPreview() {
    Surface(color = Black60) {
        Column {
            TextButton(
                text = "buttons",
                onClick = {}
            )
            IconTextButton(
                text = "Icon button",
                icon = Res.drawable.ic_human,
                isBad = true,
                onClick = {}
            )
            IconButton(
                modifier= Modifier.size(30.dp),
                icon = Res.drawable.ic_human,
                onClick = {}
            )
            ToggleButton(
                text = "toggle me!",
                icon = Res.drawable.ic_robot,
                onClick = {}
            )
            ToggleButton(
                text = "toggle me!",
                icon = Res.drawable.ic_robot,
                isEnabled = true,
                onClick = {}
            )
        }
    }
}