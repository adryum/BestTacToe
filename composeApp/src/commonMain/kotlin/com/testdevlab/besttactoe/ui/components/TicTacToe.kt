package com.testdevlab.besttactoe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import besttactoe.composeapp.generated.resources.ic_settings
import com.testdevlab.besttactoe.core.repositories.PieceStates
import com.testdevlab.besttactoe.ui.IconTeamModel
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.SetPieceValueModel
import com.testdevlab.besttactoe.ui.theme.Black
import com.testdevlab.besttactoe.ui.theme.lightGray
import com.testdevlab.besttactoe.ui.theme.lightGreen
import com.testdevlab.besttactoe.ui.theme.pxToDp
import com.testdevlab.besttactoe.ui.theme.transparent
import com.testdevlab.besttactoe.ui.theme.white_60
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TTTPiece(
    modifier: Modifier,
    icon: DrawableResource?,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {
    Box(modifier.clickable(enabled = isClickable) { onClick() }) {
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
    tileColor: Color = lightGray,
    segmentColor: Color = transparent,
    selectedSegmentColor: Color = lightGreen,
    isActive: Boolean = false,
    segmentState: PieceStates,
    pieces: List<PiecesUIModel>,
    player: IconTeamModel = IconTeamModel(Res.drawable.ic_cross, PieceStates.Enemy),
    enemy: IconTeamModel = IconTeamModel(Res.drawable.ic_robot, PieceStates.Player),
    onPieceClick: (Int, PieceStates) -> Unit
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
            .background(if (isActive) selectedSegmentColor else segmentColor)
            .onSizeChanged { size ->
                segmentSize = size
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        if (segmentState != PieceStates.None) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    when (segmentState) {
                        PieceStates.Enemy -> enemy.icon
                        PieceStates.Player -> player.icon
                        else -> Res.drawable.ic_settings
                    }
                ),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Black)
            )
            return
        }
        // 0 1 2
        // 3 4 5
        // 6 7 8
        Column {
            for (i in 0..2) {
                Row {
                    pieces.forEach { piece ->
                        if (piece.index in (0 + i * 3)..(2 + i * 3)) {
                            TTTPiece(
                                isClickable = isActive,
                                modifier = Modifier
                                    .padding(piecePadding)
                                    .size(getPieceWidth(), getPieceHeight())
                                    .background(tileColor),
                                icon = when (piece.state) {
                                    PieceStates.None -> null
                                    PieceStates.Player -> player.icon
                                    PieceStates.Enemy -> enemy.icon
                                    PieceStates.Draw -> null
                                },
                                onClick = {
                                    if (piece.state == PieceStates.None) {
                                        onPieceClick(piece.index, PieceStates.Player)
                                    }
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
    segments: List<SegmentUIModel>,
    boardPadding: Dp,
    onPieceClick: (SetPieceValueModel) -> Unit
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
                                    .size(getSegmentWidth(), getSegmentHeight()),
                                piecePadding = 1.dp,
                                segmentColor = white_60,
                                isActive = segment.isActive,
                                pieces = segment.pieces,
                                player = player,
                                enemy = enemy,
                                onPieceClick = { pieceIndex, newValue ->
                                    onPieceClick(
                                        SetPieceValueModel(
                                            segmentIndex = segment.index,
                                            pieceIndex = pieceIndex,
                                            newValue = newValue
                                        )
                                    )
                                },
                                segmentState = segment.state,
                            )
                        }
                    }
                }
            }
        }
    }
}

