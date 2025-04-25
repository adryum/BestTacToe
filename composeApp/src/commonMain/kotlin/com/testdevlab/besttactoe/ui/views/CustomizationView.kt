package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.testdevlab.besttactoe.core.cache.PreferenceHandler
import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.cache.models.ChosenVisualDBModel
import com.testdevlab.besttactoe.core.cache.toJson
import com.testdevlab.besttactoe.ui.components.Button
import com.testdevlab.besttactoe.ui.components.ButtonType
import com.testdevlab.besttactoe.ui.components.ImageWithBackground
import com.testdevlab.besttactoe.ui.components.WrappedImage
import com.testdevlab.besttactoe.ui.theme.CircleType
import com.testdevlab.besttactoe.ui.theme.ColorList
import com.testdevlab.besttactoe.ui.theme.CrossType
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.Green
import com.testdevlab.besttactoe.ui.theme.OrangeYellowList
import com.testdevlab.besttactoe.ui.theme.White
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.textMedium
import com.testdevlab.besttactoe.ui.theme.toColor
import de.drick.compose.hotpreview.HotPreview

@Composable
fun CustomizationView() {
    val visuals by PreferenceHandler.chosenVisuals.collectAsState()
    var isLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        PreferenceHandler.refreshVisuals(callback = { isLoaded = true })
    }

    if (isLoaded)
        CustomizationViewContent(
            xIconList = CrossType.entries,
            oIconList = CircleType.entries,
            colorList = ColorList,
            playerIcon = visuals?.playerIcon ,
            opponentIcon = visuals?.opponentIcon ,
            playerTint = visuals?.playerTint?.toColor(),
            opponentTint = visuals?.opponentTint?.toColor(),
        )
}

@Composable
fun CustomizationViewContent(
    xIconList: List<CrossType>,
    oIconList: List<CircleType>,
    playerIcon: CrossType?,
    opponentIcon: CircleType?,
    playerTint: Color?,
    opponentTint: Color?,
    colorList: List<Color>,
) {
    var isCircleSelected by remember { mutableStateOf(false) }

    var xTint by remember { mutableStateOf(playerTint ?: GrayDark) }
    var xIcon by remember { mutableStateOf(playerIcon ?: CrossType.Cross) }
    var oTint by remember { mutableStateOf(opponentTint ?: GrayDark) }
    var oIcon by remember { mutableStateOf(opponentIcon ?: CircleType.Circle) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.ldp, Alignment.CenterVertically)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.ldp, Alignment.CenterHorizontally)
        ) {
            ImageWithBackground(
                backgroundModifier = Modifier
                    .size(72.ldp)
                    .clip(RoundedCornerShape(8.ldp))
                    .background(White)
                    .border(
                        width = 4.ldp,
                        color = if (isCircleSelected) GrayDark else Green,
                        shape = RoundedCornerShape(8.ldp)
                    )
                    .clickable {
                        isCircleSelected = false
                    },
                imageModifier = Modifier
                    .fillMaxSize()
                    .padding(8.ldp),
                res = xIcon.resource,
                tint = xTint
            )
            ImageWithBackground(
                backgroundModifier = Modifier
                    .size(72.ldp)
                    .clip(RoundedCornerShape(8.ldp))
                    .background(White)
                    .border(
                        width = 4.ldp,
                        color = if (!isCircleSelected) GrayDark else Green,
                        shape = RoundedCornerShape(8.ldp)
                    )
                    .clickable {
                        isCircleSelected = true
                    },
                imageModifier = Modifier
                    .fillMaxSize()
                    .padding(8.ldp),
                res = oIcon.resource,
                tint = oTint
            )

            Button(
                modifier = Modifier.width(150.ldp),
                text = "save",
                buttonType = ButtonType.Center,
                colorGradient = OrangeYellowList,
                onClick = {
                    val content = ChosenVisualDBModel(
                        opponentTint = oTint.toArgb(),
                        opponentIcon = oIcon,
                        playerTint = xTint.toArgb(),
                        playerIcon = xIcon
                    ).toJson()

                    Preferences.chosenVisuals = content
                }
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(60.ldp),
            contentPadding = PaddingValues(16.ldp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Icons",
                    style = textMedium.copy(fontFamily = getSportFontFamily())
                )
            }

            if (isCircleSelected) {
                items(oIconList) { icon ->
                    WrappedImage(
                        modifier = Modifier
                            .padding(4.ldp)
                            .size(48.ldp)
                            .clickable { oIcon = icon },
                        res = icon.resource,
                    )
                }
            } else {
                items(xIconList) { icon ->
                    WrappedImage(
                        modifier = Modifier
                            .padding(4.ldp)
                            .size(48.ldp)
                            .clickable { xIcon = icon },
                        res = icon.resource,
                    )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Colors",
                    style = textMedium.copy(fontFamily = getSportFontFamily())
                )
            }
            items(colorList) { color ->
                Box(Modifier
                    .padding(4.ldp)
                    .size(48.ldp)
                    .background(color)
                    .clickable { if (isCircleSelected) oTint = color else xTint = color },
                )
            }
        }
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun CustomizationViewPreview() {
//    CustomizationViewContent({}, {})
}
