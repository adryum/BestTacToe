package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.testdevlab.besttactoe.core.utils.isWindows
import com.testdevlab.besttactoe.ui.PopUpModel
import com.testdevlab.besttactoe.ui.navigation.NavigationObject
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.DarkList
import com.testdevlab.besttactoe.ui.theme.RedList
import com.testdevlab.besttactoe.ui.theme.YellowList
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.gradientBackground
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.popInOut
import com.testdevlab.besttactoe.ui.theme.textLarge
import com.testdevlab.besttactoe.ui.theme.textMedium
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TwoChoicePopUp(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    content: PopUpModel,
) {
    var isActionPressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    fun delayAction(action: () -> Unit) {
        if (isActionPressed) return
        isActionPressed = true

        scope.launch {
            delay(timeMillis = 300L)
            action()
        }
    }

    Box(
        modifier
            .popInOut(isShown = !isActionPressed)
//            .fillMaxWidth(.8f)
//            .fillMaxHeight(.5f)
            .padding(if (isWindows()) 64.ldp else 16.ldp)
            .aspectRatio(2f/1f)
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
                    textAlign = TextAlign.Center
                )
                RandomWrappedImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .padding(12.ldp)
                        .clickable {
                            delayAction{
                                NavigationObject.hidePopUp()
                            }
                       },
                )
            }

            Column(modifier = Modifier.padding(16.ldp)) {
                Text(
                    text = content.description,
                    style = textMedium,
                    textAlign = TextAlign.Center
                )

                // spacer
                Box(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.ldp, Alignment.CenterHorizontally)
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        text = content.buttonOneText,
                        colorGradient = YellowList,
                        buttonType = ButtonType.Center,
                        onClick = {
                            delayAction {
                                content.onActionOne()
                                NavigationObject.hidePopUp()
                            }
                        },
                        textStyle = textMedium,
                        horizontalPadding = 10.ldp
                    )
                    Button(
                        modifier = Modifier.weight(1f),
                        text = content.buttonTwoText,
                        colorGradient = RedList,
                        buttonType = ButtonType.Center,
                        onClick = {
                            delayAction {
                                content.onActionTwo()
                                NavigationObject.hidePopUp()
                            }
                        },
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
                            modifier = Modifier.weight(1f),
                            text = "I'm Sure",
                            colorGradient = YellowList,
                            buttonType = ButtonType.Center,
                            onClick = onYesClick,
                            textStyle = textMedium,
                            horizontalPadding = 10.ldp
                        )
                        Button(
                            modifier = Modifier.weight(1f),
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