package com.testdevlab.besttactoe.core.cache.models

import com.testdevlab.besttactoe.ui.theme.Icon
import kotlinx.serialization.Serializable

@Serializable
data class ChosenIconDBModel(
    val opponentIcon: Icon,
    val opponentTint: Int,
    val playerIcon: Icon,
    val playerTint: Int
)
