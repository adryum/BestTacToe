package com.testdevlab.besttactoe.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.latin_numbers
import besttactoe.composeapp.generated.resources.sports
import org.jetbrains.compose.resources.Font

@Composable
fun getSportFontFamily() = FontFamily(
    Font(Res.font.sports)
)

@Composable
fun getLatinFontFamily() = FontFamily(
    Font(Res.font.latin_numbers)
)

val text = TextStyle(
    fontSize = 48.sp,
    fontWeight = FontWeight(500),
    letterSpacing = 15.sp,
    color = GrayDark,
)

val textLarge = text.copy(fontSize = 32.sp, letterSpacing = .1.sp, color = White, lineHeight = 40.sp)
val textMedium = text.copy(fontSize = 24.sp, letterSpacing = .1.sp, color = White, lineHeight = 32.sp)
val textSmall = text.copy(fontSize = 16.sp, fontWeight = FontWeight(100), letterSpacing = .1.sp)

val textTitle = text.copy(
    fontSize = 72.sp,
    letterSpacing = .1.sp,
    fontWeight = FontWeight(500),
    color = White,
    lineHeight = 60.sp,
)

val buttonStyle = text.copy(
    fontSize = 36.sp,
    letterSpacing = 0.1.sp,
    fontWeight = FontWeight(400),
    color = White
)
