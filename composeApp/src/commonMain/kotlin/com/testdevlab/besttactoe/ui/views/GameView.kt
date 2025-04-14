package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.GameResultModel
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.ParticipantUIModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.TableOuterPadding
import com.testdevlab.besttactoe.ui.components.GamesTopBar
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorations
import com.testdevlab.besttactoe.ui.components.TicTacToeTable
import com.testdevlab.besttactoe.ui.components.TurnShower
import com.testdevlab.besttactoe.ui.components.VictoryPopUp
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.YellowList
import com.testdevlab.besttactoe.ui.theme.ldp
import de.drick.compose.hotpreview.HotPreview
import kotlinx.coroutines.delay

@Composable
fun GameView(
    gameHandler: GameHandler = GameHandler,
    navigationObject: NavigationObject = NavigationObject
) {
    val boardData by gameHandler.tableData.collectAsState(initial = emptyList())
    val playerData by gameHandler.playerData.collectAsState()
    val opponentData by gameHandler.opponentData.collectAsState()
    val isRoundTimeout by gameHandler.isRoundTimeout.collectAsState()
    val gameMode by gameHandler.gameMode.collectAsState()
    val gameScore by gameHandler.score.collectAsState()
    val hasGameEnded by gameHandler.hasGameEnded.collectAsState()
    val gameResult by gameHandler.gameResult.collectAsState()
    val isPlayerTurn by gameHandler.isPlayerTurn.collectAsState()

    if (!hasGameEnded)
        LaunchedEffect(isRoundTimeout) {
            if (!isRoundTimeout) return@LaunchedEffect

            println("TIMEOUT")
            delay(2000)
            gameHandler.proceedToTheNextMatch()
        }

    GameViewContent(
        boardData = boardData,
        playerData = playerData,
        opponentData = opponentData,
        isRoundTimeout = isRoundTimeout,
        isPlayerTurn = isPlayerTurn,
        gameMode = gameMode,
        score = gameScore,
        gameResult = gameResult,
        onGoBack = navigationObject::goBack,
        exitGame = gameHandler::saveAndClearGame,
        onRematchClick = gameHandler::rematch,
        onPieceClick = gameHandler::makeAMove
    )
}

@Composable
fun GameViewContent(
    boardData: List<SegmentUIModel>,
    gameResult: GameResultModel?,
    playerData: ParticipantUIModel?,
    opponentData: ParticipantUIModel?,
    isRoundTimeout: Boolean,
    isPlayerTurn: Boolean,
    gameMode: GameMode?,
    score: ScoreModel?,
    onGoBack: () -> Unit,
    onRematchClick: () -> Unit,
    exitGame: () -> Unit,
    onPieceClick: (MoveModel) -> Unit
) {
    if (playerData == null || opponentData == null) return  // 1000% fool-proof null data solution

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
        Box(
            modifier = Modifier.aspectRatio(1f)
        ) {
            TicTacToeTable(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f),
                segments = boardData,
                padding = TableOuterPadding(
                    tablePadding = 2.ldp,
                    segmentPadding = 2.ldp,
                    piecePadding = 1.ldp
                ),
                onPieceClick = onPieceClick,
                isGameEnded = isRoundTimeout,
                isPlayerTurn = isPlayerTurn,
                playerIcon = playerData.icon,
                enemyIcon = opponentData.icon,
                gameMode = gameMode,
            )
            if (gameResult != null)
                VictoryPopUp(
                    modifier = Modifier
                        .zIndex(2f)
                        .fillMaxSize(),
                    gameResult = gameResult,
                    onPlayAgainClick = onRematchClick,
                    onGoBackClick = {
                        onGoBack()
                        exitGame()
                    }
                )
        }
        TurnShower(
            colorList = YellowList,
            playerIcon = playerData.icon,
            playerIconColor = Blue,
            opponentIcon = opponentData.icon,
            opponentIconColor = Color.Red,
            isPlayerTurn = isPlayerTurn
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun GameViewPreview() {
    GameView()
}
