package com.testdevlab.besttactoe.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val textLarge = TextStyle(
    fontSize = 48.sp,
    fontWeight = FontWeight(500),
    letterSpacing = 15.sp,
    color = GrayDark
)

val textNormal = textLarge.copy(fontSize = 36.sp, letterSpacing = 0.sp)
val textMedium = textLarge.copy(fontSize = 24.sp, letterSpacing = 0.sp)
val textSmall = textLarge.copy(fontSize = 16.sp, fontWeight = FontWeight(100), letterSpacing = .1.sp)

val textIconButton = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight(500),
    letterSpacing = .1.sp
)

val textTitle = textLarge.copy(
    fontSize = 96.sp,
    letterSpacing = .1.sp,
    fontWeight = FontWeight(500),
    color = White,
    lineHeight = 80.sp
)

val buttonStyle = textLarge.copy(
    fontSize = 36.sp,
    letterSpacing = 0.1.sp,
    fontWeight = FontWeight(400),
    color = White
)

const val avgButtonWidth = 200
const val avgButtonHeight = 50
