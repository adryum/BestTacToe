package com.testdevlab.besttactoe.core.cache.models

import com.testdevlab.besttactoe.ui.theme.CircleType
import com.testdevlab.besttactoe.ui.theme.CrossType
import kotlinx.serialization.Serializable

@Serializable
data class ChosenVisualDBModel(
    val opponentIcon: CircleType,
    val opponentTint: Int,
    val playerIcon: CrossType,
    val playerTint: Int
)
