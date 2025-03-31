package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_checkmark
import besttactoe.composeapp.generated.resources.ic_cross
import besttactoe.composeapp.generated.resources.ic_robot
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.IconTeamModel
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGray
import com.testdevlab.besttactoe.ui.theme.lightGreen
import com.testdevlab.besttactoe.ui.theme.white_60
import de.drick.compose.hotpreview.HotPreview
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import com.testdevlab.besttactoe.AppLogger
import com.testdevlab.besttactoe.ui.theme.Black
import com.testdevlab.besttactoe.ui.theme.pxToDp
import com.testdevlab.besttactoe.ui.theme.transparent


@Composable
fun TTTPiece(
    modifier: Modifier,
    icon: DrawableResource?,
    onClick: () -> Unit
) {
    Box(modifier.clickable { onClick() }) {
        if (icon == null) return
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Black)
        )
    }
}


@Composable
fun TicTacToeSegment(
    modifier: Modifier = Modifier,
    piecePadding: Dp,
    isSelected: Boolean = false,
    tileColor: Color = lightGray,

    segmentColor: Color = transparent,
    selectedSegmentColor: Color = lightGreen,
    pieces: Array<PiecesUIModel>,
    player: IconTeamModel = IconTeamModel(Res.drawable.ic_cross, PieceStates.Enemy),
    enemy: IconTeamModel = IconTeamModel(Res.drawable.ic_robot, PieceStates.Player),
    onGridsCompletion: (PieceStates) -> Unit,
    onPieceClick: (PiecesUIModel) -> Unit
) {
    val piecePaddingsInSegment = 6
    var segmentSize by remember { mutableStateOf(IntSize.Zero) }

    @Composable
    fun getPieceHeight(): Dp {
        return  segmentSize.height.pxToDp() / 3 - piecePadding * piecePaddingsInSegment
    }

    @Composable
    fun getPieceWidth(): Dp {
        return  segmentSize.width.pxToDp() / 3  - piecePadding * piecePaddingsInSegment
    }

    Box(
        modifier = modifier
            .padding(piecePadding)
            .background(if (isSelected) selectedSegmentColor else segmentColor)
            .onSizeChanged { size ->
                segmentSize = size
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        // 0 1 2
        // 3 4 5
        // 6 7 8
        Column {
            for (i in 0..2) {
                Row {
                    pieces.forEach { piece ->
                        if (piece.index in (0 + i * 3)..(2 + i * 3)) {
                            var state by remember { mutableStateOf(piece.state) }
                            AppLogger.i("checking state", "in state")
                            TTTPiece(
                                modifier = Modifier
                                    .padding(piecePadding)
                                    .size(getPieceWidth(), getPieceHeight())
                                    .background(tileColor),
                                icon = when (state) {
                                    PieceStates.None -> null
                                    PieceStates.Player -> player.icon
                                    PieceStates.Enemy -> enemy.icon
                                },
                                onClick = {
                                     state = PieceStates.Player
                                    onPieceClick(piece)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TicTacToeBoard(
    modifier: Modifier = Modifier,
    player: IconTeamModel =  IconTeamModel(Res.drawable.ic_cross, PieceStates.Player),
    enemy: IconTeamModel = IconTeamModel(Res.drawable.ic_checkmark, PieceStates.Enemy),
    segments: Array<SegmentUIModel>,
    boardPadding: Dp,
    onGridsCompletion: (PieceStates) -> Unit,
    onPieceClick: (SegmentUIModel, PiecesUIModel) -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    @Composable
    fun getSegmentWidth(): Dp {
        return size.width.pxToDp() / 3
    }

    @Composable
    fun getSegmentHeight(): Dp {
       return size.height.pxToDp() / 3
    }

    Box(
        modifier = modifier
            .background(white_60)
            .padding(boardPadding)
            .onSizeChanged { containerSize ->
                size = containerSize
            }
        ,
    ) {
        // 0 1 2
        // 3 4 5
        // 6 7 8
        Column {
            for (i in 0 .. 2) {
                Row {
                    segments.forEachIndexed { index, segment ->
                        if (index in (0 + i * 3)..(2 + i * 3)) {

                            TicTacToeSegment(
                                modifier = Modifier
                                    .size(getSegmentWidth(),getSegmentHeight())
                                ,
                                piecePadding = 1.dp,
                                segmentColor = white_60,
                                isSelected = segment.isActive,
                                pieces = segment.pieces,
                                player = player,
                                enemy = enemy,
                                onPieceClick = { piecesUIModel ->
                                    onPieceClick(segment, piecesUIModel)
                                },
                                onGridsCompletion = {}
                            )
                        }
                    }
                }
            }
        }
    }}

