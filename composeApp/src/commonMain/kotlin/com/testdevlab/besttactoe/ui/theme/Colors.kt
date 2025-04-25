package com.testdevlab.besttactoe.ui.theme

import androidx.compose.ui.graphics.Color

fun getRandomColor(): Color = ColorList[ColorList.indices.random()]

// new oldie
val SelectedSegmentColor = Color(0xff29cb6b)
val lightBlue = Color(0xFF95D7F1)
val lightRed = Color(0xFFF38484)
val GrayDark = Color(0xFF383838)
val GrayLight = Color(0xff868686)
val white_60 = Color(0x99ffffff)
val lightGray = Color(0xFFD9D9D9)
val White = Color(0xfff1f1f1)
val Black = Color(0xff000000)

val DarkerOrange = Color(0xffff5e00)
val Orange = Color(0xFFFF8400)
val DarkOrange = Color(0xFFEB3204)
val Red = Color(0xfff81616)
val Yellow = Color(0xFFFFCC3F)
val DarkBlue = Color(0xFF075D97)
val Blue = Color(0xFF0383D9)
val Green = Color(0xFF37B207)
val DarkGreen = Color(0xFF128F04)
val Black35 = Color(0x59000000)
val TransparentDark = Color(0x00000000)

val RedDarkOrangeList = listOf(Red, DarkerOrange)
val DarkOrangeOrangeList = listOf(DarkerOrange, Orange)
val OrangeList = listOf(Orange, Orange)
val OrangeYellowList = listOf(Orange, Yellow)
val YellowList = listOf(Yellow, Yellow)
val GreenList = listOf(Green, Green)
val DarkGreenGreenList = listOf(DarkGreen, Green)
val DarkGreenList = listOf(DarkGreen, DarkGreen)
val DarkList = listOf(Black35, Black35)
val BlueList = listOf(Blue, Blue)
val DarkBlueList = listOf(DarkBlue, DarkBlue)
val DarkBlueBlueList = listOf(DarkBlue, Blue)
val RedList = listOf(Red, Red)
val WhiteToLightGray = listOf(White, GrayLight)

val ColorList = listOf(
    SelectedSegmentColor,
    lightBlue,
    lightRed,
    GrayDark,
    Black,
    Orange,
    DarkOrange,
    Red,
    Yellow,
    DarkBlue,
    Blue,
    Green,
    DarkGreen,
)

fun getRainbowColorMutableList(colorCount: Int): MutableList<Color> {
    val degreesPerColor = 360f / colorCount
    val colorList = mutableListOf<Color>()

    for (i in 0..<colorCount) {
        colorList.add(
            Color.hsv(
                hue = degreesPerColor * i,
                value = 1f,
                saturation = 1f,
                alpha = 1f
            )
        )
    }

    return colorList
}