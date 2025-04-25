package com.testdevlab.besttactoe

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BestTacToe",
    ) {
//        window.minimumSize = Dimension(1000, 700)

        App()
    }
}