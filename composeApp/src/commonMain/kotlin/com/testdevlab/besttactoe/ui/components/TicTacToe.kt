package com.testdevlab.besttactoe.ui.components

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_settings
import com.testdevlab.besttactoe.core.repositories.PieceType
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.TransparentDark
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGray
import com.testdevlab.besttactoe.ui.theme.lightGreen
import com.testdevlab.besttactoe.ui.theme.pxToDp
import com.testdevlab.besttactoe.ui.theme.white_60
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TTTPiece(
    modifier: Modifier,
    tint: ColorFilter? = null,
    icon: DrawableResource?,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {
    var targetAlpha by remember { mutableStateOf(0f) }
    val alpha by animateFloatAsState(
        targetValue = targetAlpha
    )
    LaunchedEffect(Unit) {
        targetAlpha = 1f
    }
    Box(
        modifier = modifier
            .alpha(alpha)
            .clickable(enabled = isClickable) {
                onClick()
            }
            .padding(4.ldp)
    ) {
        if (icon == null) return
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(icon),
            contentDescription = null,
            colorFilter = tint
        )
    }
}

@Composable
fun <T> ThreeByThreeGrid(
    content: List<T>,
    out: @Composable (T) -> Unit
) {
    // 0 1 2
    // 3 4 5
    // 6 7 8
    Column {
        for (i in 0..2) {
            Row {
                content.forEachIndexed { index, item ->
                    if (index in (0 + i * 3)..(2 + i * 3)) {
                        out(item)
                    }
                }
            }
        }
    }
}

@Composable
fun TicTacToeSegment(
    modifier: Modifier = Modifier,
    piecePadding: Dp,
    tileColor: Color = lightGray,
    segmentColor: Color = TransparentDark,
    selectedSegmentColor: Color = lightGreen,
    isActive: Boolean = false,
    isPlayerTurn: Boolean,
    segmentState: PieceType,
    pieces: List<PiecesUIModel>,
    opponentIcon: DrawableResource,
    playerIcon: DrawableResource,
    onPieceClick: (Int) -> Unit
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
            },
        contentAlignment = Alignment.Center
    ) {
        if (segmentState != PieceType.Empty) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    when (segmentState) {
                        PieceType.Opponent -> opponentIcon
                        PieceType.Player -> playerIcon
                        else -> Res.drawable.ic_settings
                    }
                ),
                contentDescription = null,
                colorFilter = when (segmentState) {
                    PieceType.Player -> ColorFilter.tint(Blue)
                    PieceType.Opponent -> ColorFilter.tint(Color.Red)
                    else -> null
                }
            )
            return
        }
        ThreeByThreeGrid(pieces) { piece ->

            key(piece) {
                TTTPiece(
                    isClickable = isActive && isPlayerTurn,
                    tint = when (piece.state) {
                        PieceType.Player -> ColorFilter.tint(Blue)
                        PieceType.Opponent ->  ColorFilter.tint(Color.Red)
                        else -> null
                    },
                    modifier = Modifier
                        .padding(piecePadding)
                        .size(getPieceWidth(), getPieceHeight())
                        .background(tileColor),
                    icon = when (piece.state) {
                        PieceType.Empty -> null
                        PieceType.Player -> playerIcon
                        PieceType.Opponent -> opponentIcon
                        PieceType.Draw -> null
                    },
                    onClick = {
                        if (piece.state == PieceType.Empty) {
                            onPieceClick(piece.index)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TicTacToeTable(
    modifier: Modifier = Modifier,
    isPlayerTurn: Boolean,
    playerIcon: DrawableResource,
    enemyIcon: DrawableResource,
    segments: List<SegmentUIModel>,
    boardPadding: Dp,
    onPieceClick: (MoveModel) -> Unit
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
            .onSizeChanged { tableSizePx ->
                size = tableSizePx
            },
    ) {
        ThreeByThreeGrid(segments) { segment ->
            TicTacToeSegment(
                modifier = Modifier.size(getSegmentWidth(), getSegmentHeight()),
                piecePadding = 1.dp,
                segmentColor = white_60,
                isActive = segment.isActive,
                isPlayerTurn = isPlayerTurn,
                pieces = segment.pieces,
                playerIcon = playerIcon,
                opponentIcon = enemyIcon,
                onPieceClick = { pieceIndex ->
                    onPieceClick(
                        MoveModel(
                            segmentIndex = segment.index,
                            pieceIndex = pieceIndex,
                        )
                    )
                },
                segmentState = segment.state,
            )
        }
    }
}

