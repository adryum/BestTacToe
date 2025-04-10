package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.components.ButtonSlideInHorizontally
import com.testdevlab.besttactoe.ui.components.ButtonType
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorationsWithDarkContentAndColumn
import com.testdevlab.besttactoe.ui.components.TwoChoicePopUp
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.DarkGreenGreenList
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import com.testdevlab.besttactoe.ui.theme.YellowList
import de.drick.compose.hotpreview.HotPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainView(
    navigationObject: NavigationObject = NavigationObject,
    gameHandler: GameHandler = GameHandler
) {
    MainViewContent(
        onGameStart = gameHandler::startGame,
        goTo = navigationObject::goTo,
        isThereSavedGameFor = gameHandler::isThereASavedGame,
        loadGame = gameHandler::loadGame
    )
}

@Composable
fun MainViewContent(
    onGameStart: (GameMode) -> Unit,
    isThereSavedGameFor: (GameMode) -> Boolean,
    loadGame: (GameMode) -> Unit,
    goTo: (Views) -> Unit
) {
    val scope = rememberCoroutineScope()
    var isCoroutineStarted by remember { mutableStateOf(false) }
    var isShown by remember { mutableStateOf(false)}

    fun goToWrapped(view: Views, additionalAction: () -> Unit = {}) {
        isShown = false
        if (!isCoroutineStarted)
            scope.launch {
                isCoroutineStarted = true
                delay(500)
                goTo(view)
                additionalAction()
            }
    }

    LaunchedEffect(Unit) {
        isShown = true
    }

    var isStartGameClicked by remember { mutableStateOf(false) }

    TwoChoicePopUp(
        isShown = isStartGameClicked,
        title = "Start new game?",
        description = "Previous save will be erased!",
        leftButtonText = "New game",
        rightButtonText = "Load game",
        colors = DarkOrangeOrangeList,
        onCancelClick = {
            isStartGameClicked = false
        },
        onLeftChoiceClick = {
            goToWrapped(
                view = Views.GameView,
                additionalAction = { onGameStart(GameMode.VS_AI)}
            )
            isStartGameClicked = false
        },
        onRightChoiceClick = {
            goToWrapped(
                view = Views.GameView,
                additionalAction = {  loadGame(GameMode.VS_AI) }
            )
            isStartGameClicked = false
        }
    )

    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        ButtonSlideInHorizontally(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "VS AI",
            colorGradient = DarkOrangeOrangeList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                if (isThereSavedGameFor(GameMode.VS_AI)) {
                    isStartGameClicked = true
                } else {
                    goToWrapped(
                        view = Views.GameView,
                        additionalAction = { onGameStart(GameMode.VS_AI)}
                    )
                }
            },
            isShown = isShown,
            delay = 100
        )
        ButtonSlideInHorizontally(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Multiplayer",
            colorGradient = DarkGreenGreenList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToWrapped(view = Views.MultiplayerView)
            },
            isShown = isShown,
            delay = 200
        )
        ButtonSlideInHorizontally(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Game history",
            colorGradient = OrangeYellowList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToWrapped(view = Views.HistoryView)
            },
            isShown = isShown,
            delay = 300
        )
        ButtonSlideInHorizontally(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Settings",
            colorGradient = YellowList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToWrapped(view = Views.SettingsView)
             },
            isShown = isShown,
            delay = 400
        )
    }
}

@HotPreview(name = "Menu",  widthDp = 540, heightDp = 1020)
@Composable
private fun MainViewPreview() {
    MainViewContent(
        goTo = {},
        isThereSavedGameFor = {_ -> true},
        loadGame = {},
        onGameStart = {}
    )
}
