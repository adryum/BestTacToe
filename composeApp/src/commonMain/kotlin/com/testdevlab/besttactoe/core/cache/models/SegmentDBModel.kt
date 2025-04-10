package com.testdevlab.besttactoe.core.cache.models

import com.testdevlab.besttactoe.ui.SegmentType
import kotlinx.serialization.Serializable

@Serializable
data class SegmentDBModel(
    val index: Int,
    val state: SegmentType,
    val isActive: Boolean,
    val pieces: List<PieceDBModel>,
)