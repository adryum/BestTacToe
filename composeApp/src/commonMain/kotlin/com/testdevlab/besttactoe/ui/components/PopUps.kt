package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_cross
import com.testdevlab.besttactoe.ui.PopUpModel
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.DarkList
import com.testdevlab.besttactoe.ui.theme.Red
import com.testdevlab.besttactoe.ui.theme.RedList
import com.testdevlab.besttactoe.ui.theme.YellowList
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.textMedium

@Composable
fun TwoChoicePopUp(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    content: PopUpModel,
) {
    Box(
        modifier
            .fillMaxWidth(.8f)
            .fillMaxHeight(.5f)
            .clip(shape = RoundedCornerShape(5))
            .border(width = 2.ldp, color = Black35, shape = RoundedCornerShape(5))
            .gradientBackground(colors = colors, angle = 45f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)
                    .gradientBackground(colors = DarkList, angle = 0f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.ldp),
                    text = content.title,
                    style = textLarge,
                    fontFamily = getSportFontFamily(),
                    textAlign = TextAlign.Center
                )
                TicTacToePiece(
                    modifier = Modifier.fillMaxHeight().aspectRatio(1f).padding(12.ldp),
                    icon = Res.drawable.ic_cross,
                    tint = ColorFilter.tint(Red),
                    onClick = content.onCancel
                )
            }

            Column(modifier = Modifier.padding(16.ldp)) {
                Text(
                    text = content.description,
                    style = textMedium,
                    fontFamily = getSportFontFamily(),
                    textAlign = TextAlign.Center
                )

                // spacer
                Box(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.ldp, Alignment.CenterHorizontally)
                ) {
                    Button(
                        containerModifier = Modifier.weight(1f),
                        text = content.buttonOneText,
                        colorGradient = YellowList,
                        buttonType = ButtonType.Center,
                        onClick = content.onActionOne,
                        textStyle = textMedium,
                        horizontalPadding = 10.ldp
                    )
                    Button(
                        containerModifier = Modifier.weight(1f),
                        text = content.buttonTwoText,
                        colorGradient = RedList,
                        buttonType = ButtonType.Center,
                        onClick = content.onActionTwo,
                        textStyle = textMedium,
                        horizontalPadding = 10.ldp
                    )
                }
            }
        }
    }

}

@Composable
fun AreYourSurePopUp(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    title: String,
    description: String,
    onCancelClick: () -> Unit,
    onYesClick: () -> Unit,
) {
    Dialog(onDismissRequest = onCancelClick) {
        Box(
            modifier
                .fillMaxWidth(.7f)
                .fillMaxHeight(.5f)
                .clip(shape = RoundedCornerShape(5))
                .border(width = 2.ldp, color = Black35, shape = RoundedCornerShape(5))
                .gradientBackground(colors = colors, angle = 45f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)
                    .gradientBackground(colors = DarkList, angle = 0f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = textLarge,
                        fontFamily = getSportFontFamily(),
                        textAlign = TextAlign.Center
                    )
                }

                Column(modifier = Modifier.padding(16.ldp)) {
                    Text(
                        text = description,
                        style = textMedium,
                        fontFamily = getSportFontFamily(),
                        textAlign = TextAlign.Center
                    )

                    // spacer
                    Box(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.ldp, Alignment.CenterHorizontally)
                    ) {
                        Button(
                            containerModifier = Modifier.weight(1f),
                            text = "I'm Sure",
                            colorGradient = YellowList,
                            buttonType = ButtonType.Center,
                            onClick = onYesClick,
                            textStyle = textMedium,
                            horizontalPadding = 10.ldp
                        )
                        Button(
                            containerModifier = Modifier.weight(1f),
                            text = "No",
                            colorGradient = RedList,
                            buttonType = ButtonType.Center,
                            onClick = onCancelClick,
                            textStyle = textMedium,
                            horizontalPadding = 10.ldp
                        )
                    }
                }
            }
        }
    }
}