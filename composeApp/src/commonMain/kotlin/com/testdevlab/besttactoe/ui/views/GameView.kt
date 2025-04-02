package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_cross
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.SetPieceValueModel
import com.testdevlab.besttactoe.ui.components.DarkBackgroundWithDarkTop
import com.testdevlab.besttactoe.ui.components.GamesTopBar
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorations
import com.testdevlab.besttactoe.ui.components.TicTacToeTable
import com.testdevlab.besttactoe.ui.theme.ldp
import de.drick.compose.hotpreview.HotPreview

@Composable
fun GameView(
    gameHandler: GameHandler = GameHandler
) {
    val boardData by gameHandler.tableData.collectAsState(initial = emptyList())

    GameViewContent(
        boardData = boardData,
        onPieceClick = gameHandler::makeAMove
    )
}

@Composable
fun GameViewContent(
    boardData: List<SegmentUIModel>,
    onPieceClick: (SetPieceValueModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GamesTopBar(
            playerIcon = Res.drawable.ic_cross,
            score = ScoreModel(123, 1453),
            time = "12:52"
        )
        MultipleStepDecorations(2)
        TicTacToeTable(
            modifier = Modifier.aspectRatio(1f).fillMaxHeight(),
            segments = boardData,
            boardPadding = 5.ldp,
            onPieceClick = onPieceClick,
        )
        DarkBackgroundWithDarkTop {  }
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun GameViewPreview() {
    GameView()
}
