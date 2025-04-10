package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.GamesResultType
import com.testdevlab.besttactoe.ui.components.HistoryCard
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.OrangeList
import com.testdevlab.besttactoe.ui.theme.YellowList
import com.testdevlab.besttactoe.ui.theme.ldp
import de.drick.compose.hotpreview.HotPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HistoryView(
    navigationObject: NavigationObject = NavigationObject,
    preferences: Preferences = Preferences
) {
    val history by preferences.history.collectAsState()

    HistoryViewContent(
        history = history,
        goTo = navigationObject::goTo
    )
}

@Composable
fun HistoryViewContent(
    history: List<List<GamesResultType>>,
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

    val scrollState = rememberScrollState()
//    LazyColumn(
//        modifier = Modifier.verticalScroll(scrollState),
//        verticalArrangement = Arrangement.spacedBy(32.ldp)
//    ) {
//        items(history) {
//            HistoryCard(
//                gameMode = GameMode.VS_AI,
//                gameResults = listOf(GamesResultType.Draw, GamesResultType.Victory,(GamesResultType.Draw), (GamesResultType.Loss),(GamesResultType.Loss)),
//                colorGradient = OrangeList
//            )
//        }
    Column(modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.ldp)
    ) {

        HistoryCard(
            gameMode = GameMode.VS_AI,
            gameResults = listOf(GamesResultType.Draw, GamesResultType.Victory,(GamesResultType.Draw), (GamesResultType.Loss),(GamesResultType.Loss)),
            colorGradient = OrangeList
        )
        HistoryCard(
            gameMode = GameMode.Multiplayer,
            gameResults = listOf(GamesResultType.Draw, GamesResultType.Victory,(GamesResultType.Draw), (GamesResultType.Loss),(GamesResultType.Loss)),
            colorGradient = YellowList
        )
        HistoryCard(
            gameMode = GameMode.HotSeat,
            gameResults = listOf(GamesResultType.Draw, GamesResultType.Victory,(GamesResultType.Draw), (GamesResultType.Loss),(GamesResultType.Loss)),
            colorGradient = DarkOrangeOrangeList
        )
    }
//    }
}

@HotPreview(name = "History", widthDp = 540, heightDp = 1020)
@Composable
private fun HistoryViewPreview() {
    HistoryViewContent(
        history = emptyList(),
        goTo = {},
    )
}
