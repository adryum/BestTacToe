package com.testdevlab.besttactoe.core.cache.models

import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.GameResult
import kotlinx.serialization.Serializable

@Serializable
data class GameDBModel(
    val score: ScoreDBModel,
    val table: List<SegmentDBModel>,
    val isPlayerTurn: Boolean,
    val gameMode: GameMode,
    val matchHistory: List<GameResult>
)
