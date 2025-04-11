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
import com.testdevlab.besttactoe.ui.theme.DarkBlueList
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import de.drick.compose.hotpreview.HotPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MultiplayerView(
    navigationObject: NavigationObject = NavigationObject,
    gameHandler: GameHandler = GameHandler
) {

    MultiplayerViewContent(
        isThereSavedGameFor = gameHandler::isThereASavedGame,
        loadGame = gameHandler::loadGame,
        startGame = gameHandler::startGame,
        goTo = navigationObject::goTo
    )
}

@Composable
fun MultiplayerViewContent(
    startGame: (GameMode) -> Unit,
    isThereSavedGameFor: (GameMode) -> Boolean,
    loadGame: (GameMode) -> Unit,
    goTo: (Views) -> Unit
) {
    val scope = rememberCoroutineScope()
    var isCoroutineStarted by remember { mutableStateOf(false) }
    var isShown by remember { mutableStateOf(false) }

    fun goToWrapped(view: Views, additionalAction: () -> Unit = {}) {
        isShown = false
        if (!isCoroutineStarted)
            scope.launch {
                isCoroutineStarted = true
                delay(400)
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
                additionalAction = { startGame(GameMode.HotSeat)}
            )
            isStartGameClicked = false
        },
        onRightChoiceClick = {
            goToWrapped(
                view = Views.GameView,
                additionalAction = {  loadGame(GameMode.HotSeat) }
            )
            isStartGameClicked = false
        }
    )


    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        ButtonSlideInHorizontally(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Join",
            colorGradient = OrangeYellowList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToWrapped(view = Views.JoinLobbyView)
            },
            isShown = isShown,
            delay = 100
        )
        ButtonSlideInHorizontally(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Create",
            colorGradient = DarkOrangeOrangeList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToWrapped(view = Views.CreateLobbyView)
            },
            isShown = isShown,
            delay = 200
        )
        ButtonSlideInHorizontally(
            containerModifier = Modifier.fillMaxWidth(.7f),
            text = "Hot-seat",
            colorGradient = DarkBlueList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                if (isThereSavedGameFor(GameMode.HotSeat)) {
                    isStartGameClicked = true
                } else {
                    goToWrapped(
                        view = Views.GameView,
                        additionalAction = { startGame(GameMode.HotSeat)}
                    )
                }
            },
            isShown = isShown,
            delay = 300
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun MultiplayerViewPreview() {
    MultiplayerViewContent(
        goTo = {},
        startGame = {},
        isThereSavedGameFor = {_ -> false},
        loadGame = {}
    )
}
