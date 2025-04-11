package com.testdevlab.besttactoe.core.cache.models

import com.testdevlab.besttactoe.ui.Piece
import kotlinx.serialization.Serializable

@Serializable
data class PieceDBModel(
    val index: Int,
    val state: Piece
)
