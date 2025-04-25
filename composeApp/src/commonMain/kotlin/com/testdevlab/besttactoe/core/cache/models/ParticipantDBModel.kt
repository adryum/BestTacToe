package com.testdevlab.besttactoe.core.cache.models

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantDBModel(
    val playerName: String,
    val opponentName: String,
)