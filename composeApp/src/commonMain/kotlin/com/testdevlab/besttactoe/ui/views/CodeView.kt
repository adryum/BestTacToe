package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.idleRotateClamped
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.popInOut
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.textTitle
import de.drick.compose.hotpreview.HotPreview

@Composable
fun CodeView(
    gameHandler: GameHandler = GameHandler,
    navigationObject: NavigationObject = NavigationObject
) {
    val code by gameHandler.code.collectAsState()
    val roundCount by gameHandler.roundCount.collectAsState()
    val isLoadingView by navigationObject.isViewLoadingIn.collectAsState()

    CodeViewContent(
        codeString = code,
        roundCount = roundCount,
        isLoadingView = isLoadingView
    )
}

@Composable
fun CodeViewContent(
    codeString: String?,
    roundCount: Int,
    isLoadingView: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(72.ldp, Alignment.CenterVertically)
    ) {
        Text(
            modifier = Modifier
                .idleRotateClamped(
                    maxAngle = 10f,
                    oneCycleDurationMills = 2000
                )
                .popInOut(
                    delay = 200L,
                    isShown = isLoadingView
                ),
            text = codeString ?: "Loading...",
            style = textTitle.copy(fontFamily = getSportFontFamily())
        )

        Text(
            modifier = Modifier.popInOut(
                delay = 300L,
                isShown = isLoadingView
            ),
            text = "Rounds: $roundCount",
            style = textLarge.copy(fontFamily = getSportFontFamily())
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun CodeViewPreview() {
    CodeView()
}
