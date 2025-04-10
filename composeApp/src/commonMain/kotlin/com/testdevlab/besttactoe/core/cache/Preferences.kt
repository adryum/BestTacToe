package com.testdevlab.besttactoe.core.cache

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.serialization.removeValue
import com.testdevlab.besttactoe.core.cache.models.GameDBModel
import com.testdevlab.besttactoe.core.cache.models.HistoryDBModel
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.GamesResultType
import com.testdevlab.besttactoe.ui.theme.toHistoryDBModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.ExperimentalSerializationApi

object Preferences {
    private val _settings: Settings = Settings()
    private val _AIGameDataKey = "GAME_DATA_AI"
    private val _hotSeatGameDataKey = "GAME_DATA_HOT_SEAT"
    private val _gameHistoryKey = "GAME_HISTORY_LIST"
    private val _history = MutableStateFlow<List<List<GamesResultType>>>(emptyList())
    val history = _history.asStateFlow()

    init {
        _history.update { getHistory()?.results ?: emptyList() }
    }

    private fun getKeyFromGameMode(gameMode: GameMode) = when (gameMode) {
        GameMode.VS_AI -> _AIGameDataKey
        GameMode.HotSeat -> _hotSeatGameDataKey
        GameMode.Multiplayer -> ""
        GameMode.RoboRumble -> ""
    }

    // store values
    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    fun saveGameData(gameData: GameDBModel) {
        _settings.encodeValue(
            serializer = GameDBModel.serializer(),
            key = getKeyFromGameMode(gameData.gameMode),
            value = gameData
        )
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    fun addGameToHistory(gameResult: List<GamesResultType>) {
        // history -> List<List<GamesResultType>>
        // idea: get -> result, add, store
        // idea: get -> null, make
        val result = _settings.decodeValueOrNull(
            serializer = HistoryDBModel.serializer(),
            key = _gameHistoryKey
        )

        val historyList = if (result == null) {
                listOf(gameResult).toHistoryDBModel()
            } else {
                 result.results.toMutableList()
                    .apply { this.add(gameResult) }
                    .toList()
                    .toHistoryDBModel()
            }

        _settings.encodeValue(
            serializer =  HistoryDBModel.serializer(),
            key = _gameHistoryKey,
            value = historyList
        )

        _history.update { historyList.results }
    }

    fun isThereASaveFor(gameMode: GameMode) = getGameData(gameMode) != null

    // get values
    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    fun getGameData(gameMode: GameMode) = _settings.decodeValueOrNull(
        serializer = GameDBModel.serializer(),
        key = getKeyFromGameMode(gameMode)
    )

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    fun getHistory() = _settings.decodeValueOrNull(
        serializer = HistoryDBModel.serializer(),
        key = _gameHistoryKey
    )

    // delete values
    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    fun dropGameData(gameMode: GameMode) = _settings.removeValue(
        serializer = GameDBModel.serializer(),
        key = getKeyFromGameMode(gameMode)
    )
}