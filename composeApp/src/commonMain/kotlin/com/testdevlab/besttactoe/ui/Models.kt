package com.testdevlab.besttactoe.ui

import com.testdevlab.besttactoe.ui.components.PieceStates
import org.jetbrains.compose.resources.DrawableResource

data class ScoreModel(val playerScore: Int, val enemyScore: Int)
data class IconTeamModel(val icon: DrawableResource, val team: PieceStates)

data class SegmentUIModel(
    val index: Int,
    val isActive: Boolean,
    val state: PieceStates,
    val pieces: Array<PiecesUIModel>
)

data class PiecesUIModel(
    val index: Int,
    val state: PieceStates
)