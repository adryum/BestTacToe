package com.testdevlab.besttactoe.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_settings
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.core.repositories.PieceType
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.TableOuterPadding
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.TransparentDark
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGray
import com.testdevlab.besttactoe.ui.theme.lightGreen
import com.testdevlab.besttactoe.ui.theme.pxToDp
import com.testdevlab.besttactoe.ui.theme.white_60
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TicTacToePiece(
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
    modifier: Modifier = Modifier,
    content: List<T>,
    out: @Composable (T) -> Unit
) {
    // 0 1 2
    // 3 4 5
    // 6 7 8
    Column(modifier = modifier) {
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
fun SegmentImage(
    modifier: Modifier,
    segmentState: PieceType,
    opponentIcon: DrawableResource,
    playerIcon: DrawableResource,
    imagePadding: Dp,
) {
    val color = when (segmentState) {
        PieceType.Player -> Blue
        PieceType.Opponent -> Color.Red
        PieceType.Draw -> GrayDark
        else -> white_60
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .border(
                width = 4.ldp,
                shape = RoundedCornerShape(8.ldp),
                color = color
            )
            .alpha(.2f)
            .clip(RoundedCornerShape(8.ldp))
            .background(color = color)
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(imagePadding),
            painter = painterResource(
                when (segmentState) {
                    PieceType.Opponent -> opponentIcon
                    PieceType.Player -> playerIcon
                    else -> Res.drawable.ic_settings
                }
            ),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color)
        )
    }
}

@Composable
fun TicTacToeSegment(
    modifier: Modifier = Modifier,
    piecePadding: Dp,
    segment: SegmentUIModel,
    tileColor: Color = lightGray,
    segmentColor: Color = TransparentDark,
    selectedSegmentColor: Color = lightGreen,
    isPlayerTurn: Boolean,
    isGameEnded: Boolean,
    gameMode: GameMode?,
    opponentIcon: DrawableResource,
    playerIcon: DrawableResource,
    onPieceClick: (Int) -> Unit
) {
    val piecePaddingsInSegment = 6
    var segmentSize by remember { mutableStateOf(IntSize.Zero) }

    @Composable
    fun getPieceHeight() = segmentSize.height.pxToDp() / 3 - piecePadding * piecePaddingsInSegment

    @Composable
    fun getPieceWidth() = segmentSize.width.pxToDp() / 3  - piecePadding * piecePaddingsInSegment

    fun isPieceClickable() =
        !isGameEnded &&
        ((segment.isActive && isPlayerTurn) || (segment.isActive && gameMode == GameMode.HotSeat))

    var targetAlpha by remember { mutableStateOf(0f) }
    val alpha by animateFloatAsState(
        targetValue = targetAlpha
    )

    if (segment.isAnythingButEmpty)
        LaunchedEffect(Unit) {
            targetAlpha = 1f
        }

    Box(modifier = modifier
        .background(if (segment.isActive) selectedSegmentColor else segmentColor)
        .onSizeChanged { sizePx ->
            segmentSize = sizePx
        },
        contentAlignment = Alignment.Center
    ) {
        SegmentImage(
            modifier = Modifier
                .zIndex(2f)
                .alpha(alpha)
                .padding(4.ldp)
                .fillMaxSize(),
            segmentState = segment.state,
            playerIcon = playerIcon,
            opponentIcon = opponentIcon,
            imagePadding = 10.ldp,
        )
        ThreeByThreeGrid(
            modifier = Modifier.zIndex(1f),
            content = segment.pieces
        ) { piece ->
            key(piece) {
                TicTacToePiece(
                    modifier = Modifier
                        .padding(piecePadding)
                        .size(getPieceWidth(), getPieceHeight())
                        .background(tileColor),
                    isClickable = isPieceClickable(),
                    tint = when (piece.state) {
                        PieceType.Player -> ColorFilter.tint(Blue)
                        PieceType.Opponent ->  ColorFilter.tint(Color.Red)
                        else -> null
                    },
                    icon = when (piece.state) {
                        PieceType.Player -> playerIcon
                        PieceType.Opponent -> opponentIcon
                        else -> null
                    },
                    onClick = {
                        if (piece.isEmpty) {
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
    isGameEnded: Boolean,
    playerIcon: DrawableResource,
    enemyIcon: DrawableResource,
    gameMode: GameMode?,
    segments: List<SegmentUIModel>,
    padding: TableOuterPadding,
    onPieceClick: (MoveModel) -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    @Composable
    fun getSegmentHeight(): Dp {
        return  size.height.pxToDp() / 3 - padding.segmentPadding * 6
    }

    @Composable
    fun getSegmentWidth(): Dp {
        return  size.width.pxToDp() / 3  - padding.segmentPadding * 6
    }
    Box(
        modifier = modifier
            .background(white_60)
            .padding(padding.tablePadding)
            .onSizeChanged { tableSizePx ->
                size = tableSizePx
            },
    ) {
        ThreeByThreeGrid(content = segments) { segment ->
            TicTacToeSegment(
                modifier = Modifier
                    .padding(padding.segmentPadding)
                    .size(getSegmentWidth(), getSegmentHeight()),
                piecePadding = padding.piecePadding,
                segment = segment,
                segmentColor = white_60,
                isPlayerTurn = isPlayerTurn,
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
                gameMode = gameMode,
                isGameEnded = isGameEnded,
            )
        }
    }
}

