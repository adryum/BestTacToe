package com.testdevlab.besttactoe.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_cross
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.ScoreModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import com.testdevlab.besttactoe.ui.components.GamesTopBar
import com.testdevlab.besttactoe.ui.components.TicTacToeGrid
import com.testdevlab.besttactoe.ui.components.PieceStates
import com.testdevlab.besttactoe.ui.components.TicTacToeBoard
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.lightGreen
import com.testdevlab.besttactoe.ui.theme.lightRed
import de.drick.compose.hotpreview.HotPreview

@Composable
fun GameView() {
    GameViewContent()
}

@Composable
fun GameViewContent() {
    Column(
        modifier = Modifier.fillMaxSize().background(lightRed).padding(top = 30.ldp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GamesTopBar(
            playerIcon = Res.drawable.ic_cross,
            score = ScoreModel(123, 1453),
            time = "12:52"
        )

        TicTacToeBoard(
            modifier = Modifier.aspectRatio(1f).fillMaxHeight(),
            segments = arrayOf(
                SegmentUIModel(
                    index = 0,
                    isActive = true,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                ),
                SegmentUIModel(
                    index = 1,
                    isActive = false,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                ),
                SegmentUIModel(
                    index = 2,
                    isActive = false,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                ),
                SegmentUIModel(
                    index = 3,
                    isActive = false,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                ),
                SegmentUIModel(
                    index = 4,
                    isActive = false,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                ),
                SegmentUIModel(
                    index = 5,
                    isActive = false,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                ),
                SegmentUIModel(
                    index = 6,
                    isActive = false,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                ),
                SegmentUIModel(
                    index = 7,
                    isActive = false,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                ),
                SegmentUIModel(
                    index = 8,
                    isActive = false,
                    state = PieceStates.None,
                    pieces = arrayOf(
                        PiecesUIModel(
                            index = 0,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 1,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 2,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 3,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 4,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 5,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 6,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 7,
                            state = PieceStates.None
                        ),
                        PiecesUIModel(
                            index = 8,
                            state = PieceStates.None
                        )
                    )
                )
            ),
            onGridsCompletion = {},
            boardPadding = 5.ldp,
            onPieceClick = {_,_ ->},
        )
    }
}

@HotPreview(name = "Menu", widthDp = 540, heightDp = 1020)
@Composable
private fun GameViewPreview() {
    GameView()
}
