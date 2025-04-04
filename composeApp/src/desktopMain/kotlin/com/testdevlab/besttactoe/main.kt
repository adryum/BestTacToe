package com.testdevlab.besttactoe

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BestTacToe",
    ) {
//        window.minimumSize = Dimension(500, 720)
        window.title = "BestTacToe"
        window.size = Dimension(300, 1000)
        App()
    }
}