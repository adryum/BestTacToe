package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_cross
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.OpponentUIModel
import com.testdevlab.besttactoe.ui.PlayerUIModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.components.DarkBackground
import com.testdevlab.besttactoe.ui.components.DarkBackgroundWithDarkTop
import com.testdevlab.besttactoe.ui.components.GamesTopBar
import com.testdevlab.besttactoe.ui.components.MultipleStepDecorations
import com.testdevlab.besttactoe.ui.components.TicTacToeTable
import com.testdevlab.besttactoe.ui.components.TurnShower
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkGreen
import com.testdevlab.besttactoe.ui.theme.Green
import com.testdevlab.besttactoe.ui.theme.ldp
import de.drick.compose.hotpreview.HotPreview
import org.jetbrains.compose.resources.painterResource

@Composable
fun GameView(
    gameHandler: GameHandler = GameHandler
) {
    val boardData by gameHandler.tableData.collectAsState(initial = emptyList())
    val playerData by gameHandler.playerData.collectAsState()
    val opponentData by gameHandler.opponentData.collectAsState()
    val winner by gameHandler.gameWinner.collectAsState()

    GameViewContent(
        boardData = boardData,
        playerData = playerData,
        opponentData = opponentData,
        winner = winner,
        onPieceClick = gameHandler::makeAMove
    )
}

@Composable
fun GameViewContent(
    boardData: List<SegmentUIModel>,
    playerData: PlayerUIModel,
    opponentData: OpponentUIModel,
    winner: OpponentUIModel?,
    onPieceClick: (MoveModel) -> Unit
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
        Box {
            TicTacToeTable(
                modifier = Modifier
                    .zIndex(1f)
                    .aspectRatio(1f)
                    .fillMaxHeight(),
                segments = boardData,
                boardPadding = 5.ldp,
                onPieceClick = onPieceClick,
                isPlayerTurn = playerData.hasTurn,
                playerIcon = playerData.icon,
                enemyIcon = opponentData.icon,
            )

            if (winner != null)
            DarkBackground(
                modifier = Modifier
                    .zIndex(2f)
                    .aspectRatio(1f)
                    .fillMaxHeight(),
            ) {
                Column {
                    Text("${winner.name} is Victorious!")
                    Image(painter = painterResource(winner.icon), contentDescription = null)
                }
            }
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
