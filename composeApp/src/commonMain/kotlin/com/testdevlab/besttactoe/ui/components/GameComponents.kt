package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import com.testdevlab.besttactoe.ui.GameResultModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.theme.BlueList
import com.testdevlab.besttactoe.ui.theme.DarkOrangeOrangeList
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import com.testdevlab.besttactoe.ui.theme.RedList
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.isDraw
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.pxToDp
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.textMedium
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun VictoryPopUp(
    modifier: Modifier = Modifier,
    gameResult: GameResultModel,
    onGoBackClick: () -> Unit,
    onPlayAgainClick: () -> Unit,
) {
    DarkBackground(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.ldp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = gameResult.name + if (gameResult.result.isDraw()) "" else " is Victorious!",
                style = textLarge,
                fontFamily = getSportFontFamily()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.ldp)
                    .height(80.ldp),
                horizontalArrangement = Arrangement.spacedBy(32.ldp)
            ) {
                Button(
                    containerModifier = Modifier.weight(1f),
                    colorGradient = OrangeYellowList,
                    text = "Rematch",
                    buttonType = ButtonType.Center,
                    onClick = onPlayAgainClick,
                    textStyle = textMedium
                )
                Button(
                    containerModifier = Modifier.weight(1f),
                    colorGradient = DarkOrangeOrangeList,
                    text = "Leave",
                    buttonType = ButtonType.Center,
                    onClick = onGoBackClick,
                    textStyle = textMedium
                )
            }
        }
    }
}

@Composable
fun TurnShower(
    modifier: Modifier = Modifier,
    colorList: List<Color>,
    playerIcon: DrawableResource,
    playerIconColor: Color,
    opponentIconColor: Color,
    isPlayerTurn: Boolean,
    opponentIcon: DrawableResource,
) {
    var width by remember { mutableStateOf(0) }
    Row(
        modifier = modifier
            .padding(vertical = 16.ldp)
            .fillMaxWidth()
            .onSizeChanged {
                width = it.width
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MoveShower(
            text = "Your turn",
            colorGradient = colorList,
            isPlayerTurn = isPlayerTurn,
            icon = playerIcon,
            iconPadding = 15.ldp,
            iconColor = playerIconColor,
            textStyle = textMedium,
            showerType = MoveShowerType.LeftSide,
            width = width.pxToDp(),
            contentAlignment = Alignment.CenterEnd,
            horizontalPadding = 2.ldp
        )
        MoveShower(
            text = "'s turn",
            colorGradient = colorList,
            isPlayerTurn = !isPlayerTurn,
            icon = opponentIcon,
            iconColor = opponentIconColor,
            iconPadding = 15.ldp,
            textStyle = textMedium,
            showerType = MoveShowerType.RightSide,
            width = width.pxToDp(),
            contentAlignment = Alignment.CenterStart,
            horizontalPadding = 2.ldp
        )
    }
}

@Composable
fun GamesTopBar(
    modifier: Modifier = Modifier,
    playerName: String,
    opponentName: String,
    score: ScoreModel
) {
    Row(
        modifier = modifier.padding(vertical = 16.ldp),
        horizontalArrangement = Arrangement.spacedBy(16.ldp, Alignment.CenterHorizontally)
    ) {
        Button(
            containerModifier = Modifier.weight(1f),
            colorGradient = BlueList,
            text = playerName,
            isClickable = false,
            onClick = {},
            textStyle = textMedium,
            verticalPadding = 12.ldp,
            horizontalPadding = 10.ldp,
            buttonType = ButtonType.LeftSide,
            contentAlignment = Alignment.Center
        )

        Column (
            modifier = Modifier.weight(.7f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Score",
                style = textMedium,
                textAlign = TextAlign.Center
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${score.playerScore}",
                    style = textMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.weight(.05f),
                    text = "|",
                    style = textMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${score.opponentScore}",
                    style = textMedium,
                    textAlign = TextAlign.Center
                )
            }

        }

        Button(
            containerModifier = Modifier.weight(1f),
            colorGradient = RedList,
            text = opponentName,
            isClickable = false,
            onClick = {},
            textStyle = textMedium,
            verticalPadding = 12.ldp,
            horizontalPadding = 10.ldp,
            buttonType = ButtonType.RightSide,
            contentAlignment = Alignment.Center
        )
    }
}