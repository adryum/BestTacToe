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
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.core.utils.isAndroid
import com.testdevlab.besttactoe.ui.GameResult
import com.testdevlab.besttactoe.ui.IconUIModel
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.ParticipantUIModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.TableOuterPadding
import com.testdevlab.besttactoe.ui.components.AccessibilityRow
import com.testdevlab.besttactoe.ui.components.GamesTopBar
import com.testdevlab.besttactoe.ui.components.GamesTopBarPhone
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorations
import com.testdevlab.besttactoe.ui.components.TicTacToeTable
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.navigation.Views
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.log
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
    val code by gameHandler.code.collectAsState()
    val hasGameEnded by gameHandler.hasGameEnded.collectAsState()
    val isPlayerTurn by gameHandler.isPlayerTurn.collectAsState()
    val roundResults by gameHandler.roundResults.collectAsState()
    val roundCount by gameHandler.roundCount.collectAsState()

    log("isplayer turn : $isPlayerTurn")

    if (!hasGameEnded)
        LaunchedEffect(isRoundTimeout) {
            if (!isRoundTimeout) return@LaunchedEffect

            gameHandler.setAnimation(true)
            delay(1800)
            gameHandler.setAnimation(false)
            delay(200)
            gameHandler.proceedToTheNextRound()
        }

    GameViewContent(
        roundCount = roundCount,
        boardData = boardData,
        playerData = playerData,
        opponentData = opponentData,
        isRoundTimeout = isRoundTimeout,
        isPlayerTurn = isPlayerTurn,
        gameMode = gameMode,
        roundResults = roundResults,
        code = code,
        goBack = navigationObject::goBack,
        goBackTill = navigationObject::goBackTill,
        handleGoBack = gameHandler::handleGoingBack,
        onPieceClick = gameHandler::makeAMove
    )
}

@Composable
fun GameViewContent(
    roundCount: Int,
    boardData: List<SegmentUIModel>,
    playerData: ParticipantUIModel?,
    opponentData: ParticipantUIModel?,
    roundResults: List<GameResult>,
    isRoundTimeout: Boolean,
    isPlayerTurn: Boolean,
    gameMode: GameMode?,
    code: String?,
    goBack: () -> Unit,
    goBackTill: (Views) -> Unit,
    handleGoBack: (Views) -> Unit,
    onPieceClick: (MoveModel) -> Unit
) {
    if (playerData == null || opponentData == null) return  // 1000% fool-proof null data solution

    fun goBackWrapper() {
        // local -> go back
        // multi -> To multi view
        if (gameMode == GameMode.Multiplayer) {
            goBackTill(Views.MultiplayerView)
        } else {
            goBack()
        }
        handleGoBack(Views.GameView)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.ldp, Alignment.CenterVertically)
    ) {
        if (isAndroid())
            GamesTopBarPhone(
                playerName = playerData.name,
                playerIconData = IconUIModel(res = playerData.icon, tint = playerData.tint),
                opponentIconData = IconUIModel(res = opponentData.icon, tint = opponentData.tint),
                opponentName = opponentData.name,
                isPlayerTurn = isPlayerTurn,
                results = roundResults,
                roundCount = roundCount
            )
        else
            GamesTopBar(
                playerName = playerData.name,
                playerIconData = IconUIModel(res = playerData.icon, tint = playerData.tint),
                opponentIconData = IconUIModel(res = opponentData.icon, tint = opponentData.tint),
                opponentName = opponentData.name,
                code = code,
                onGoBack = {
                    goBackWrapper()
                },
                isPlayerTurn = isPlayerTurn,
                results = roundResults,
                roundCount = roundCount
            )
        MultipleStepDecorations(2)
        Box(
            modifier = Modifier.aspectRatio(1f)
        ) {
            TicTacToeTable(
                modifier = Modifier
                    .fillMaxSize(),
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
                playerTint = playerData.tint,
                opponentTint = opponentData.tint,
                gameMode = gameMode,
            )
        }

        if (isAndroid())
            AccessibilityRow(
                code = code,
                onGoBack = {
                    goBackWrapper()
                },
            )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun GameViewPreview() {
    GameView()
}
