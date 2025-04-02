package com.testdevlab.besttactoe.ui

import com.testdevlab.besttactoe.core.repositories.PieceStates
import org.jetbrains.compose.resources.DrawableResource

data class ScoreModel(val playerScore: Int, val enemyScore: Int)
data class IconTeamModel(val icon: DrawableResource, val team: PieceStates)

data class SegmentUIModel(
    val index: Int,
    val isActive: Boolean,
    val state: PieceStates,
    val pieces: List<PiecesUIModel>
)

data class PlayerUIModel(
    val name: String,
    val pieceType: PieceStates,
    val hasTurn: Boolean
)

data class UserUIModel(
    val name: String
)

data class PiecesUIModel(
    val index: Int,
    val state: PieceStates
)

data class SetPieceValueModel(
    val segmentIndex: Int,
    val pieceIndex: Int,
    val newValue: PieceStates,
)