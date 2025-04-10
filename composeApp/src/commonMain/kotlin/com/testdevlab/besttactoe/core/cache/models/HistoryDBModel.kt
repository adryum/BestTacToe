package com.testdevlab.besttactoe.core.cache.models

import com.testdevlab.besttactoe.ui.GamesResultType
import kotlinx.serialization.Serializable

@Serializable
data class HistoryDBModel(
    val results: List<List<GamesResultType>>
)
