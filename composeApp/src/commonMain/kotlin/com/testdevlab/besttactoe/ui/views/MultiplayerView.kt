package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.components.Button
import com.testdevlab.besttactoe.ui.components.ButtonType
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorationsWithDarkContentAndColumn
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.DarkBlueList
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import com.testdevlab.besttactoe.ui.theme.slideInOutLeft
import de.drick.compose.hotpreview.HotPreview

@Composable
fun MultiplayerView(
    navigationObject: NavigationObject = NavigationObject,
    gameHandler: GameHandler = GameHandler
) {
    val isShown by navigationObject.isViewLoadingIn.collectAsState()

    MultiplayerViewContent(
        isThereSavedGameFor = gameHandler::isThereASavedGame,
        loadGame = gameHandler::loadGame,
        startGame = gameHandler::startGame,
        isShown = isShown,
        goToDelayed = navigationObject::delayedGoTo
    )
}

@Composable
fun MultiplayerViewContent(
    startGame: (GameMode) -> Unit,
    isThereSavedGameFor: (GameMode) -> Boolean,
    isShown: Boolean,
    loadGame: (GameMode) -> Unit,
    goToDelayed: (Views, Long, () -> Unit) -> Unit
) {

//    TwoChoicePopUp(
//        isShown = isStartGameClicked,
//        title = "Start new game?",
//        description = "Previous save will be erased!",
//        leftButtonText = "New game",
//        rightButtonText = "Load game",
//        colors = DarkOrangeOrangeList,
//        onCancelClick = {
//            isStartGameClicked = false
//        },
//        onLeftChoiceClick = {
//            goToWrapped(
//                view = Views.GameView,
//                additionalAction = { startGame(GameMode.HotSeat)}
//            )
//            isStartGameClicked = false
//        },
//        onRightChoiceClick = {
//            goToWrapped(
//                view = Views.GameView,
//                additionalAction = {  loadGame(GameMode.HotSeat) }
//            )
//            isStartGameClicked = false
//        }
//    )

    val loadOutDelay = 600L

    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        Button(
            containerModifier = Modifier
                .fillMaxWidth(.7f)
                .slideInOutLeft(duration = 300, isShown = isShown),
            text = "Join",
            colorGradient = OrangeYellowList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToDelayed(Views.JoinLobbyView, loadOutDelay, {})
            }
        )
        Button(
            containerModifier = Modifier
                .fillMaxWidth(.7f)
                .slideInOutLeft(duration = 400, isShown = isShown),
            text = "Create",
            colorGradient = DarkOrangeOrangeList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToDelayed(Views.CreateLobbyView, loadOutDelay, {})
            }
        )
        Button(
            containerModifier = Modifier
                .fillMaxWidth(.7f)
                .slideInOutLeft(duration = 500, isShown = isShown),
            text = "Hot-seat",
            colorGradient = DarkBlueList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                if (isThereSavedGameFor(GameMode.HotSeat)) {
//                    isStartGameClicked = true
                } else {
                    goToDelayed(Views.GameView, loadOutDelay) { startGame(GameMode.HotSeat)}
                }
            }
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun MultiplayerViewPreview() {
    MultiplayerViewContent(
        startGame = {},
        isThereSavedGameFor = { _ -> false },
        loadGame = {},
        isShown = false,
        goToDelayed = {_,_,_ -> }
    )
}
