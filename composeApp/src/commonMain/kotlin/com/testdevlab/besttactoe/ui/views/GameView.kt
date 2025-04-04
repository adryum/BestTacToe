package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.GameEndModel
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.OpponentUIModel
import com.testdevlab.besttactoe.ui.PlayerUIModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.TableOuterPadding
import com.testdevlab.besttactoe.ui.components.DarkBackgroundWithDarkTop
import com.testdevlab.besttactoe.ui.components.GamesTopBar
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorations
import com.testdevlab.besttactoe.ui.components.TicTacToeTable
import com.testdevlab.besttactoe.ui.components.TurnShower
import com.testdevlab.besttactoe.ui.components.VictoryPopUp
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkGreen
import com.testdevlab.besttactoe.ui.theme.Green
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.viewmodels.NavigationObject
import de.drick.compose.hotpreview.HotPreview

@Composable
fun GameView(
    gameHandler: GameHandler = GameHandler,
    navigationObject: NavigationObject = NavigationObject
) {
    val boardData by gameHandler.tableData.collectAsState(initial = emptyList())
    val playerData by gameHandler.playerData.collectAsState()
    val opponentData by gameHandler.opponentData.collectAsState()
    val gameResult by gameHandler.gameResult.collectAsState()
    val gameMode by gameHandler.gameMode.collectAsState()
    val gameScore by gameHandler.gameScore.collectAsState()

    GameViewContent(
        boardData = boardData,
        playerData = playerData,
        opponentData = opponentData,
        gameResult = gameResult,
        gameMode = gameMode,
        score = gameScore,
        onGoBack = navigationObject::goBack,
        onPlayAgainClick = gameHandler::playAgain,
        onPieceClick = gameHandler::makeAMove
    )
}

@Composable
fun GameViewContent(
    boardData: List<SegmentUIModel>,
    playerData: PlayerUIModel,
    opponentData: OpponentUIModel,
    gameResult: GameEndModel?,
    gameMode: GameMode?,
    score: ScoreModel?,
    onGoBack: () -> Unit,
    onPlayAgainClick: () -> Unit,
    onPieceClick: (MoveModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GamesTopBar(
            playerName = playerData.name,
            opponentName = opponentData.name,
            score = score ?: ScoreModel(0,0),
        )
        MultipleStepDecorations(2)
        Box {
            TicTacToeTable(
                modifier = Modifier
                    .zIndex(1f)
                    .aspectRatio(1f),
                segments = boardData,
                padding = TableOuterPadding(
                    tablePadding = 1.ldp,
                    segmentPadding = 1.ldp,
                    piecePadding = 1.ldp
                ),
                onPieceClick = onPieceClick,
                isGameEnded = gameResult != null,
                isPlayerTurn = playerData.hasTurn,
                playerIcon = playerData.icon,
                enemyIcon = opponentData.icon,
                gameMode = gameMode,
            )

            if (gameResult != null)
            VictoryPopUp(
                modifier = Modifier
                    .zIndex(2f)
                    .aspectRatio(1f)
                    .fillMaxHeight(),
                gameResult = gameResult,
                onPlayAgainClick = onPlayAgainClick,
                onGoBackClick = onGoBack
            )
        }
        TurnShower(
            leftGradientColor = DarkGreen,
            rightGradientColor = Green,
            playerIcon = playerData.icon,
            playerIconColor = Blue,
            opponentIcon = opponentData.icon,
            opponentIconColor = Color.Red,
            isPlayerTurn = playerData.hasTurn
        )
        DarkBackgroundWithDarkTop {  }
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun GameViewPreview() {
    GameView()
}
