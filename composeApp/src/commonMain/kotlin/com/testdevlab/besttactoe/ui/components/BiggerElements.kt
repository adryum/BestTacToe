package com.testdevlab.besttactoe.ui.components

import TextInputWithIcon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_back
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightBlue
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.textMedium
import com.testdevlab.besttactoe.ui.theme.textNormal
import com.testdevlab.besttactoe.ui.theme.textSmall
import com.testdevlab.besttactoe.ui.theme.white_60
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun TopBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    backgroundColor: Color = white_60,
    height: Dp = 80.ldp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.height(height).padding(top = 20.ldp).background(backgroundColor),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                icon = Res.drawable.ic_back,
                onClick = onClick
            )
            Text(
                text = "Back",
                style = textMedium
            )
        }
    }
}

@Composable
fun MoveShower(
    modifier: Modifier = Modifier.fillMaxWidth(),
    backgroundColor: Color = white_60,
    @DrawableRes playerIcon: DrawableResource
) {
    // IDEA: on player move, this text scrolls to left and other players text scrolls in from the left
    Box(
        modifier = modifier.height(30.ldp).background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.aspectRatio(1f/1f),
                painter = painterResource(playerIcon),
                contentDescription = null
            )
            Text(
                text = "'s Move",
                style = textNormal
            )
        }
    }
}

@Composable
fun GamesTopBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    backgroundColor: Color = white_60,
    @DrawableRes playerIcon: DrawableResource,
    height: Dp = 80.ldp,
    score: ScoreModel,
    time: String
) {
    Box(modifier = modifier.height(height).background(backgroundColor)) {
        Row {
            GamesTopBarElement(
                text = "You are",
                content = {
                    Image(
                        modifier = Modifier.aspectRatio(1f/1f),
                        painter = painterResource(playerIcon),
                        contentDescription = null,
                    )
                }
            )
            GamesTopBarElement(
                text = "Score",
                content = {
                    Row {
                        // 3 different text components, so they space out evenly when someone gets really high score
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${score.playerScore}",
                            style = textSmall
                        )
                        Text(
                            modifier = Modifier.weight(.3f),
                            text = "|",
                            style = textSmall
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${score.enemyScore}",
                            style = textSmall
                        )
                    }
                }
            )
            GamesTopBarElement(
                text = "You are",
                content = {
                    Text(time, style = textSmall)
                }
            )
        }
    }
}

@Composable
fun GamesTopBarElement(
    modifier: Modifier = Modifier,
    text: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = textSmall,
            textAlign = TextAlign.Center
        )
        content()
    }
}

@Composable
fun ViewTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier.height(200.ldp).fillMaxWidth().background(white_60),
        contentAlignment = Alignment.Center
    ) {
        Text(text= text, style = textLarge, textAlign = TextAlign.Center)
    }
}

@Composable
fun CodeInputField(
    columnModifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    textValue: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit
) {
    Column(
        modifier = columnModifier
            .clip(RoundedCornerShape(8.ldp))
            .border(4.ldp, white_60, RoundedCornerShape(8.ldp))
            .background(lightBlue),
        verticalArrangement = Arrangement.spacedBy(4.ldp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = textModifier,
            text = "Lobby code:",
            textAlign = TextAlign.Center,
            style = textNormal
        )
        TextInputWithIcon(
            hint = "code here",
            modifier = textInputModifier.padding(12.ldp),
            text = textValue,
            onValueChanged = onValueChanged,
            borderWidth = 0
        )
    }
}


@Composable
fun LobbyCodeShower(
    modifier: Modifier = Modifier,
    codeString: String
) {
    Box(
        modifier = modifier.height(200.ldp).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier= Modifier
                .clip(RoundedCornerShape(8.ldp))
                .border(4.ldp, white_60, RoundedCornerShape(8.ldp))
                .background(lightBlue)
                .padding(12.ldp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.ldp)
        ) {
            Text(
                text = "Lobby code:",
                textAlign = TextAlign.Center,
                style = textMedium
            )

            Box(
                modifier = modifier.clip(RoundedCornerShape(8.ldp)).background(white_60),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = modifier.padding(8.ldp),
                    text = codeString,
                    style = textLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
