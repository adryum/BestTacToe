package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.ui.SetPieceValueModel
import com.testdevlab.besttactoe.ui.PiecesUIModel
import com.testdevlab.besttactoe.ui.SegmentUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.testdevlab.besttactoe.AppLogger

object GameHandler {
    private val _boardData = MutableStateFlow<List<SegmentUIModel>>(
                listOf(
                SegmentUIModel(
                    index = 0,
                    isActive = true,
                    state = PieceStates.None,
                    pieces = listOf(
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
                    pieces = listOf(
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
                    pieces = listOf(
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
                    pieces = listOf(
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
                    pieces = listOf(
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
                    pieces = listOf(
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
                    pieces = listOf(
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
                    pieces = listOf(
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
                    pieces = listOf(
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
            )
    )
    val boardData = _boardData.asStateFlow()

    fun makeAMove(pieceData: SetPieceValueModel) {
        _boardData.update {
            TicTacToeManager.processMove(pieceData, _boardData.value.map { it.copy() })
        }
    }

}