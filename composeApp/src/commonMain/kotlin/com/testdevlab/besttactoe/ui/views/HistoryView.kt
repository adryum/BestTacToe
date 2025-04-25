package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.cache.models.HistoryDBModel
import com.testdevlab.besttactoe.core.cache.toObject
import com.testdevlab.besttactoe.ui.GameResultUIModel
import com.testdevlab.besttactoe.ui.components.HistoryCard
import com.testdevlab.besttactoe.ui.theme.OrangeList
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.slideInFromLeft
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.toGameResultUIModelList
import de.drick.compose.hotpreview.HotPreview

@Composable
fun HistoryView(
    preferences: Preferences = Preferences
) {
    var gameHistory by remember { mutableStateOf<List<GameResultUIModel>>(emptyList()) }
    LaunchedEffect(Unit) { println("Pulled history")
        gameHistory = preferences
            .history
            ?.toObject<HistoryDBModel>()
            ?.results
            ?.toGameResultUIModelList() ?: emptyList()
    }

    HistoryViewContent(
        gameResults = gameHistory
    )
}

@Composable
fun HistoryViewContent(
    gameResults: List<GameResultUIModel>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(32.ldp)
    ) {
        if (gameResults.isEmpty()) {
            item {
                Text(
                    text = "No history jet has been made",
                    style = textLarge,
                    textAlign = TextAlign.Center,
                    fontFamily = getSportFontFamily()
                )
            }
        } else {
            items(items = gameResults) { result ->
                HistoryCard(
                    modifier = Modifier.slideInFromLeft(600),
                    opponentName = result.opponentName,
                    playerName = result.playerName,
                    gameMode = result.gameMode,
                    gameResults = result.matches,
                    colorGradient = OrangeList
                )
            }
        }
    }
}

@HotPreview(name = "History", widthDp = 540, heightDp = 1020)
@Composable
private fun HistoryViewPreview() {
    HistoryViewContent(
        gameResults = emptyList()
    )
}
