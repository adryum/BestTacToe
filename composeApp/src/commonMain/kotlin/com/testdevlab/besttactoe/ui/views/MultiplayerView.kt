package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.PopUpModel
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
        onGameStart = gameHandler::startLocalGame,
        isShown = isShown,
        showPupUp = navigationObject::showPopUp,
        goToDelayed = navigationObject::delayedGoTo
    )
}

@Composable
fun MultiplayerViewContent(
    onGameStart: (GameMode) -> Unit,
    isThereSavedGameFor: (GameMode) -> Boolean,
    isShown: Boolean,
    showPupUp: (PopUpModel) -> Unit,
    loadGame: (GameMode) -> Unit,
    goToDelayed: (Views, Long, () -> Unit) -> Unit
) {
    val loadOutDelay = 600L
    val popUpContent = PopUpModel(
        title = "Start new game?",
        description = "Previous save will be erased!",
        buttonOneText = "New game",
        onActionOne = {
            goToDelayed(
                Views.GameView,
                loadOutDelay
            ) { onGameStart(GameMode.HotSeat) }
        },
        buttonTwoText = "Load game",
        onActionTwo = {
            goToDelayed(
                Views.GameView,
                loadOutDelay
            ) { loadGame(GameMode.HotSeat) }
        }
    )

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
                    showPupUp(popUpContent)
                } else {
                    goToDelayed(Views.GameView, loadOutDelay) { onGameStart(GameMode.HotSeat)}
                }
            }
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun MultiplayerViewPreview() {
    MultiplayerViewContent(
        onGameStart = {},
        isThereSavedGameFor = { _ -> false },
        loadGame = {},
        isShown = false,
        goToDelayed = {_,_,_ -> },
        showPupUp = {}
    )
}
