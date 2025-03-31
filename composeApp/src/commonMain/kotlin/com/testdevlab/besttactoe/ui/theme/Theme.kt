package com.testdevlab.besttactoe.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable


private val colorScheme = darkColorScheme(
    primary = Black,
    secondary = PurpleGrey80,
    surface = Black,
    onPrimary = White,
    onSecondary = White,
    onSurface = Black60
)

/**
 * Just puts content inside composable which gives default colors aka theme to content - MaterialTheme
 */
@Composable
fun BestTacToeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme,
        content = content
    )
}