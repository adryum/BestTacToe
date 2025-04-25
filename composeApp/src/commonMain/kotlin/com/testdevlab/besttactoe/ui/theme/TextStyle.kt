package com.testdevlab.besttactoe.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.latin_numbers
import besttactoe.composeapp.generated.resources.sports
import com.testdevlab.besttactoe.core.utils.isWindows
import org.jetbrains.compose.resources.Font

@Composable
fun getSportFontFamily() = FontFamily(
    Font(Res.font.sports)
)

@Composable
fun getLatinFontFamily() = FontFamily(
    Font(Res.font.latin_numbers)
)

fun getSPForOS(size: TextUnit): TextUnit {
    return if (isWindows()) size
    else (size.value - 10.sp.value).sp
}

val text @Composable get() = TextStyle(
    fontSize = 48.sp,
    fontWeight = FontWeight(500),
    letterSpacing = 15.sp,
    color = GrayDark,
    fontFamily = FontFamily(Font(Res.font.sports))
)
val textLarge @Composable get() = text.copy(
    fontSize = getSPForOS(32.sp),
    letterSpacing = .1.sp,
    color = White,
    lineHeight = 40.sp,
    fontFamily = FontFamily(Font(Res.font.sports))
)
val textMedium @Composable get() = text.copy(
    fontSize = getSPForOS(24.sp),
    letterSpacing = .1.sp,
    color = White,
    lineHeight = 32.sp,
            fontFamily = FontFamily(Font(Res.font.sports))
)
val textSmall @Composable get() = text.copy(
    fontSize = getSPForOS(16.sp),
    fontWeight = FontWeight(100),
    letterSpacing = .1.sp,
    fontFamily = FontFamily(Font(Res.font.sports))
)
val textCode @Composable get() = textLarge.copy(
    fontSize = getSPForOS(48.sp),
    color = Black,
    fontFamily = FontFamily(Font(Res.font.sports))
)
val textTitle @Composable get() = text.copy(
    fontSize = getSPForOS(68.sp),
    letterSpacing = .1.sp,
    fontWeight = FontWeight(500),
    color = White,
    lineHeight = 60.sp,
    textAlign = TextAlign.Center,
    fontFamily = FontFamily(Font(Res.font.sports))
)
