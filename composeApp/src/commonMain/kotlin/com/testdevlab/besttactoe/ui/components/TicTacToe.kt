package com.testdevlab.besttactoe.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_equals
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.MoveModel
import com.testdevlab.besttactoe.ui.Piece
import com.testdevlab.besttactoe.ui.Segment
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.TableOuterPadding
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.SelectedSegmentColor
import com.testdevlab.besttactoe.ui.theme.TransparentDark
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGray
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
    var hasIcon by remember { mutableStateOf(false)}
    val scale by animateFloatAsState(
        targetValue = if (hasIcon) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    LaunchedEffect(Unit) {
        hasIcon = true
    }

    Box(
        modifier = modifier
            .clickable(enabled = isClickable) {
                onClick()
            }
            .padding(4.ldp)
    ) {
        if (icon == null) return
        Image(
            modifier = Modifier.fillMaxSize().scale(scale),
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
    segmentState: Segment,
    opponentIcon: DrawableResource,
    playerIcon: DrawableResource,
    imagePadding: Dp,
) {
    val color = when (segmentState) {
        Segment.Player -> Blue
        Segment.Opponent -> Color.Red
        Segment.Draw -> GrayDark
        else -> white_60
    }

    var showImage by remember { mutableStateOf(false)}
    val scale by animateFloatAsState(
        targetValue = if (showImage) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    LaunchedEffect(Unit) {
        showImage = true
    }

    Box(
        modifier = modifier.scale(scale),
        contentAlignment = Alignment.Center
    ) {
        // background
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
                    Segment.Opponent -> opponentIcon
                    Segment.Player -> playerIcon
                    else -> Res.drawable.ic_equals
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
    selectedSegmentColor: Color = SelectedSegmentColor,
    isPlayerTurn: Boolean,
    isGameEnded: Boolean,
    gameMode: GameMode?,
    opponentIcon: DrawableResource,
    playerIcon: DrawableResource,
    onPieceClick: (Int) -> Unit
) {
    var segmentSize by remember { mutableStateOf(IntSize.Zero) }

    @Composable
    fun getPieceHeight() = segmentSize.height.pxToDp() / 3 - 2 * piecePadding

    @Composable
    fun getPieceWidth() = segmentSize.width.pxToDp() / 3 - 2 * piecePadding

    fun isPieceClickable() =
        !isGameEnded &&
        ((segment.isActive && isPlayerTurn) || (segment.isActive && gameMode == GameMode.HotSeat))

    Box(modifier = modifier
        .background(if (segment.isActive) selectedSegmentColor else segmentColor)
        .onSizeChanged { sizePx ->
            segmentSize = sizePx
        },
    ) {
        if (segment.isAnythingButNone)
            SegmentImage(
                modifier = Modifier
                    .zIndex(2f)
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
                        Piece.Player -> ColorFilter.tint(Blue)
                        Piece.Opponent ->  ColorFilter.tint(Color.Red)
                        else -> null
                    },
                    icon = when (piece.state) {
                        Piece.Player -> playerIcon
                        Piece.Opponent -> opponentIcon
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
        return  size.height.pxToDp() / 3 - 2 * padding.segmentPadding
    }

    @Composable
    fun getSegmentWidth(): Dp {
        return  size.width.pxToDp() / 3 - 2 * padding.segmentPadding
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

