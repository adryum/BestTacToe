package com.testdevlab.besttactoe.core.cache.models

import kotlinx.serialization.Serializable

@Serializable
data class ScoreDBModel(
    val opponentName: String,
    val playerName: String,
    val opponentScore: Int,
    val playerScore: Int
)