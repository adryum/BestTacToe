package com.testdevlab.besttactoe.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val text = TextStyle(
    fontSize = 48.sp,
    fontWeight = FontWeight(500),
    letterSpacing = 15.sp,
    color = GrayDark
)

val textNormal = text.copy(fontSize = 36.sp, letterSpacing = 0.sp)
val textMedium = text.copy(fontSize = 24.sp, letterSpacing = .1.sp, color = White, lineHeight = 20.sp)
val textSmall = text.copy(fontSize = 16.sp, fontWeight = FontWeight(100), letterSpacing = .1.sp)

val textTitle = text.copy(
    fontSize = 96.sp,
    letterSpacing = .1.sp,
    fontWeight = FontWeight(500),
    color = White,
    lineHeight = 80.sp
)

val buttonStyle = text.copy(
    fontSize = 36.sp,
    letterSpacing = 0.1.sp,
    fontWeight = FontWeight(400),
    color = White
)
