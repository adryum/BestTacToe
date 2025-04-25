package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import com.testdevlab.besttactoe.ui.theme.Black
import com.testdevlab.besttactoe.ui.theme.DarkBlue
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.White
import com.testdevlab.besttactoe.ui.theme.getRandomColor
import com.testdevlab.besttactoe.ui.theme.getRandomIconRes
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.textMedium
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun WrappedImage(
    modifier: Modifier = Modifier,
    res: DrawableResource,
    tint: Color = Black
) {
    Image(
        modifier = modifier,
        painter = painterResource(res),
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint)
    )
}

@Composable
fun RandomWrappedImage(modifier: Modifier = Modifier) {
    val randImage = remember { getRandomIconRes() }
    val randColor = remember { getRandomColor() }

    WrappedImage(
        modifier = modifier,
        res = randImage,
        tint = randColor
    )
}

@Composable
fun Code(
    modifier: Modifier = Modifier,
    code: String
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.ldp))
            .background(White)
            .border(color = DarkBlue, width = 2.ldp, shape = RoundedCornerShape(8.ldp))
            .padding(4.ldp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            text = code,
            style = textMedium.copy(color = GrayDark),
            textAlign = TextAlign.Center,
            fontFamily = getSportFontFamily()
        )
    }
}