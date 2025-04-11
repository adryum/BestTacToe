package com.testdevlab.besttactoe.core.cache.models

import com.testdevlab.besttactoe.ui.Segment
import kotlinx.serialization.Serializable

@Serializable
data class SegmentDBModel(
    val index: Int,
    val state: Segment,
    val isActive: Boolean,
    val pieces: List<PieceDBModel>,
)