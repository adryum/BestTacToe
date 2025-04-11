package com.testdevlab.besttactoe.core.cache.models

import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.GameResult
import kotlinx.serialization.Serializable

@Serializable
data class GameResultDBModel(
    val playerName: String,
    val opponentName: String,
    val gameMode: GameMode,
    val matches: List<GameResult>
)
