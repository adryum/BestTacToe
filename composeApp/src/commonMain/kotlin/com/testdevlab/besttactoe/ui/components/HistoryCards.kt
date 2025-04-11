package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.GameResult
import com.testdevlab.besttactoe.ui.theme.Black
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.DarkGreen
import com.testdevlab.besttactoe.ui.theme.DarkOrange
import com.testdevlab.besttactoe.ui.theme.Red
import com.testdevlab.besttactoe.ui.theme.White
import com.testdevlab.besttactoe.ui.theme.color
import com.testdevlab.besttactoe.ui.theme.getLatinFontFamily
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.icon
import com.testdevlab.besttactoe.ui.theme.isLoss
import com.testdevlab.besttactoe.ui.theme.isVictory
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.textMedium

@Composable
fun HistoryCard(
    containerModifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    gameMode: GameMode,
    gameResults: List<GameResult>,
    colorGradient: List<Color>,
) {
    val shape = RoundedCornerShape(
        topStartPercent = 0, topEndPercent = 10,
        bottomStartPercent = 0, bottomEndPercent = 10
    )
    var buttonHeight by remember { mutableStateOf(0) }

    Box(
        modifier = containerModifier
            .fillMaxWidth(.9f)
            .height(200.ldp)
            .clip(shape)
            .background(Black)
    ) {
        // button
        Box(
            modifier = buttonModifier
                .zIndex(2f)
                .fillMaxWidth()
                .clip(shape)
                .background(Brush.linearGradient(colorGradient))
                .onSizeChanged { buttonHeight = it.height },
        ) {
            Column(
                modifier = Modifier.padding(16.ldp)
            ) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        color = when (gameMode) {
                            GameMode.VS_AI -> Blue
                            GameMode.HotSeat -> DarkGreen
                            GameMode.Multiplayer -> DarkOrange
                            GameMode.RoboRumble -> White
                            GameMode.None -> Black
                        },
                        modifier = Modifier.fillMaxWidth(.2f),
                        text = when (gameMode) {
                            GameMode.VS_AI -> "VS AI"
                            GameMode.HotSeat -> "Hot-Seat"
                            GameMode.Multiplayer -> "Multiplayer"
                            GameMode.RoboRumble -> "RoboRumble"
                            GameMode.None -> "NONE"
                        },
                        style = textLarge,
                        fontFamily = getSportFontFamily()
                    )
                    TurnAmountShower(
                        modifier = Modifier.padding(8.ldp),
                        results = gameResults
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(2f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(.2f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        // players
                        SpacedBetweenTexts(
                            modifier = Modifier.padding(end = 16.ldp),
                            leftText = "Adrians",
                            rightText = "${gameResults.count { gameResult ->
                                gameResult.isVictory()
                            }}",
                            style = textMedium,
                            fontFamily = getSportFontFamily()
                        )
                        Text(
                            color = Red,
                            text = "vs",
                            style = textMedium,
                            fontFamily = getSportFontFamily()
                        )
                        SpacedBetweenTexts(
                            modifier = Modifier.padding(end = 16.ldp),
                            leftText = "Nauris",
                            rightText = "${gameResults.count { gameResult ->
                                gameResult.isLoss()
                            }}",
                            style = textMedium,
                            fontFamily = getSportFontFamily()
                        )
                    }
                    TurnResultGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .clip(RoundedCornerShape(10))
                            .background(color = Black35)
                            .padding(8.ldp),
                        results = gameResults
                    )
                }
            }
        }
    }
}

@Composable
fun TurnAmountShower(
    modifier: Modifier = Modifier,
    results: List<GameResult>
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        results.forEachIndexed { index, _ ->
            Text(
                modifier = Modifier.weight(1f),
                text = "${index + 1}",
                style = textMedium,
                fontFamily = getLatinFontFamily(),
                textAlign = TextAlign.Center
            )

        }
    }
}

@Composable
fun TurnResultGrid(
    modifier: Modifier,
    results: List<GameResult>
) {
    Column(
        modifier = modifier,
    ) {
        for (type in GameResult.entries) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                for (result in results) {
                    if (result == type) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            TicTacToePiece(
                                tint = ColorFilter.tint(result.color()),
                                icon = result.icon(),
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .fillMaxHeight(),
                                isClickable = false,
                                onClick = {}
                            )
                        }
                    } else {
                        // spacer
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}