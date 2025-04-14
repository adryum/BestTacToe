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
import com.testdevlab.besttactoe.ui.theme.DarkGreenGreenList
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import com.testdevlab.besttactoe.ui.theme.YellowList
import com.testdevlab.besttactoe.ui.theme.slideInOutLeft
import de.drick.compose.hotpreview.HotPreview

@Composable
fun MainView(
    navigationObject: NavigationObject = NavigationObject,
    gameHandler: GameHandler = GameHandler
) {
    val isLoadingView by navigationObject.isViewLoadingIn.collectAsState()
    
    MainViewContent(
        onGameStart = gameHandler::startGame,
        goToDelayed = navigationObject::delayedGoTo,
        isLoadingView = isLoadingView,
        isThereSavedGameFor = gameHandler::isThereASavedGame,
        loadGame = gameHandler::loadGame,
        showPopUp = navigationObject::showPopUp,
        hidePopUp = navigationObject::hidePopUp
    )
}

@Composable
fun MainViewContent(
    onGameStart: (GameMode) -> Unit,
    isThereSavedGameFor: (GameMode) -> Boolean,
    isLoadingView: Boolean,
    loadGame: (GameMode) -> Unit,
    showPopUp: (PopUpModel) -> Unit,
    hidePopUp: () -> Unit,
    goToDelayed: (Views, Long, () -> Unit) -> Unit
) {
    val loadOutDelay = 500L
    val popUpContent = PopUpModel(
        title = "Start new game?",
        description = "Previous save will be erased!",
        buttonOneText = "New game",
        onActionOne = {
            goToDelayed(
                Views.GameView,
                loadOutDelay
            ) { onGameStart(GameMode.VS_AI) }
        },
        buttonTwoText = "Load game",
        onActionTwo = {
            goToDelayed(
                Views.GameView,
                loadOutDelay
            ) { loadGame(GameMode.VS_AI) }
        },
        onCancel = hidePopUp
    )

    MultipleStepDecorationsWithDarkContentAndColumn(2) {
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f).slideInOutLeft(duration = 300, isShown = isLoadingView),
            text = "VS AI",
            colorGradient = DarkOrangeOrangeList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                if (isThereSavedGameFor(GameMode.VS_AI)) {
                    showPopUp(popUpContent)
                } else {
                    goToDelayed(
                        Views.GameView,
                        loadOutDelay
                    ) { onGameStart(GameMode.VS_AI) }
                }
            }
        )
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f).slideInOutLeft(duration = 400, isShown = isLoadingView),
            text = "Multiplayer",
            colorGradient = DarkGreenGreenList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToDelayed(Views.MultiplayerView, loadOutDelay, {})
            }
        )
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f).slideInOutLeft(duration = 500, isShown = isLoadingView),
            text = "Game history",
            colorGradient = OrangeYellowList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToDelayed(Views.HistoryView, loadOutDelay, {})
            }
        )
        Button(
            containerModifier = Modifier.fillMaxWidth(.7f).slideInOutLeft(duration = 500, isShown = isLoadingView),
            text = "Settings",
            colorGradient = YellowList,
            buttonType = ButtonType.LeftSide,
            onClick = {
                goToDelayed(Views.SettingsView, loadOutDelay, {})
             }
        )
    }
}

@HotPreview(name = "Menu",  widthDp = 540, heightDp = 1020)
@Composable
private fun MainViewPreview() {
    MainViewContent(
        goToDelayed = { _, _, _ -> },
        isThereSavedGameFor = { _ -> true },
        loadGame = {},
        onGameStart = {},
        isLoadingView = false,
        showPopUp = { },
        hidePopUp = {},
    )
}
