package com.testdevlab.besttactoe

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_back_rounded
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.utils.isAndroid
import com.testdevlab.besttactoe.ui.GameEndedAnimationModel
import com.testdevlab.besttactoe.ui.animations.DrawAnimation
import com.testdevlab.besttactoe.ui.animations.VSAnimation
import com.testdevlab.besttactoe.ui.animations.VictoryAnimation
import com.testdevlab.besttactoe.ui.components.CubeButton
import com.testdevlab.besttactoe.ui.components.DarkBackground
import com.testdevlab.besttactoe.ui.components.RandomWrappedImage
import com.testdevlab.besttactoe.ui.components.TwoChoicePopUp
import com.testdevlab.besttactoe.ui.components.ViewTitle
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkBlue
import com.testdevlab.besttactoe.ui.theme.DarkGreen
import com.testdevlab.besttactoe.ui.theme.DarkGreenGreenList
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.DarkerOrange
import com.testdevlab.besttactoe.ui.theme.Green
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.Yellow
import com.testdevlab.besttactoe.ui.theme.getViewTitle
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.idleRotate
import com.testdevlab.besttactoe.ui.theme.idleRotateClamped
import com.testdevlab.besttactoe.ui.theme.isMultiplayer
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.popInOut
import com.testdevlab.besttactoe.ui.theme.pxToDp
import com.testdevlab.besttactoe.ui.theme.showTopBar
import com.testdevlab.besttactoe.ui.theme.slideLeftToRight
import com.testdevlab.besttactoe.ui.views.CodeView
import com.testdevlab.besttactoe.ui.views.CreateLobbyView
import com.testdevlab.besttactoe.ui.views.CustomizationView
import com.testdevlab.besttactoe.ui.views.GameView
import com.testdevlab.besttactoe.ui.views.HistoryView
import com.testdevlab.besttactoe.ui.views.JoinRoomView
import com.testdevlab.besttactoe.ui.views.MainView
import com.testdevlab.besttactoe.ui.views.MultiplayerView
import com.testdevlab.besttactoe.ui.views.SettingsView
import de.drick.compose.hotpreview.HotPreview


@Composable
fun App() {
    // stuff passed here is shown on every device
    // for viewModels I will need to define here only values that I'll use from them
    val currentView by NavigationObject.currentView.collectAsState()
    val isLoadingView by NavigationObject.isViewLoadingIn.collectAsState()
    val isPopUpShown by NavigationObject.isPopUpShown.collectAsState()
    val popUpContent by NavigationObject.popUpContent.collectAsState()
    val isLoadingShown by NavigationObject.isLoadingShown.collectAsState()
    val gameResults by GameHandler.gameResult.collectAsState()
    val isGamesStart by GameHandler.isGamesStart.collectAsState()
    val isAnimationShown by GameHandler.isAnimationShown.collectAsState()

    Surface {
        AppContent(
            currentView = currentView,
            goBack = NavigationObject::delayedGoBack,
            isLoadingView = isLoadingView,
            handleGoingBack = GameHandler::handleGoingBack,
        )
    }

    if (isPopUpShown && popUpContent != null)
        DarkBackground(modifier = Modifier.fillMaxSize().clickable(enabled = false) {  }) {
            TwoChoicePopUp(colors = DarkOrangeOrangeList, content = popUpContent!!)
        }

    if (isLoadingShown)
        DarkBackground(modifier = Modifier.fillMaxSize().clickable(enabled = false) {  }) {
            RandomWrappedImage(modifier = Modifier.idleRotate(oneCycleDurationMills = 1000))
        }

    if (gameResults != null)
        DarkBackground(modifier = Modifier.fillMaxSize().clickable(enabled = false) {  }) {
            val animationData = GameEndedAnimationModel(
                name = gameResults!!.name,
                onRematch = GameHandler::handleRematch,
                onLeave = {
                    if (GameHandler.gameMode.value.isMultiplayer()) {
                        NavigationObject.goBackTill(Views.MultiplayerView)
                    } else {
                        NavigationObject.goBack()
                    }
                    GameHandler.handleGoingBack(Views.GameView)
                }
            )

            if (gameResults!!.name == "DRAW") DrawAnimation(animationData)
            else VictoryAnimation(animationData)
        }

    if (isGamesStart) {
        DarkBackground(modifier = Modifier.fillMaxSize()) {
            VSAnimation(
                modifier = Modifier.popInOut(isShown = isAnimationShown),
            )
        }
    }
}


@Composable
fun AppContent(
    currentView: Views,
    goBack: () -> Unit,
    handleGoingBack: (Views) -> Unit,
    isLoadingView: Boolean,
) {
    val isTopBarShown = currentView.showTopBar()
    val viewTitle = currentView.getViewTitle()
    val androidEdgeToEdgePadding = WindowInsets.statusBars.getTop(LocalDensity.current).pxToDp()

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val color1 by animateColorAsState(
        targetValue = when (currentView) {
            Views.MainView -> DarkOrangeOrangeList[0]
            Views.GameView -> DarkOrangeOrangeList[0]
            Views.MultiplayerView -> DarkGreenGreenList[0]
            Views.JoinLobbyView -> Yellow
            Views.CreateLobbyView -> DarkerOrange
            Views.SettingsView -> Blue
            Views.CodeView -> Green
            Views.Customization -> Blue
            Views.HistoryView -> Blue
        },
        animationSpec = tween(durationMillis = 400, easing = LinearEasing)
    )

    val color2 by animateColorAsState(
        targetValue = when (currentView) {
            Views.MainView -> DarkOrangeOrangeList[1]
            Views.GameView -> DarkOrangeOrangeList[1]
            Views.MultiplayerView -> DarkGreenGreenList[1]
            Views.JoinLobbyView -> Yellow
            Views.CreateLobbyView -> Orange
            Views.CodeView -> DarkGreen
            Views.HistoryView -> Blue
            Views.Customization -> DarkBlue
            Views.SettingsView -> Blue
        },
        animationSpec = tween(durationMillis = 400, easing = LinearEasing)
    )

    // background for the whole app. might later change with animations
    Box(
        modifier = Modifier.gradientBackground(
            colors = listOf(color1, color2),
            angle = angle
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isAndroid()) Modifier.padding(top = androidEdgeToEdgePadding)
                    else Modifier
                )
        ) {
            if (isTopBarShown)
                CubeButton(
                    modifier = Modifier
                        .padding(8.ldp)
                        .size(40.ldp)
                        .popInOut(
                            delay = 100,
                            isShown = isLoadingView
                        ),
                    resource = Res.drawable.ic_back_rounded,
                    onClick = {
                        goBack()
                        handleGoingBack(currentView)
                    }
                )

            // It gets inside, just is hidden
            if (currentView != Views.GameView && currentView != Views.CodeView)  {
                ViewTitle(
                    modifier = Modifier
                        .slideLeftToRight(
                            duration = 1000,
                            isShown = isLoadingView
                        )
                        .idleRotateClamped(
                            maxAngle = 10f,
                            oneCycleDurationMills = 2000,
                            easing = EaseInOutSine
                        ),
                    text = viewTitle,
                )
            }

            when (currentView) {
                Views.MainView -> MainView()
                Views.CreateLobbyView -> CreateLobbyView()
                Views.GameView -> GameView()
                Views.JoinLobbyView -> JoinRoomView()
                Views.MultiplayerView -> MultiplayerView()
                Views.SettingsView -> SettingsView()
                Views.HistoryView -> HistoryView()
                Views.CodeView -> CodeView()
                Views.Customization -> CustomizationView()
            }
        }
    }
}

@Composable
@HotPreview("app", widthDp = 500, heightDp = 1000)
private fun AppPreview() {
    App()
}