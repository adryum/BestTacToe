package com.testdevlab.besttactoe.core.cache.models

import kotlinx.serialization.Serializable

@Serializable
data class HistoryDBModel(
    val results: List<GameResultDBModel>
)
