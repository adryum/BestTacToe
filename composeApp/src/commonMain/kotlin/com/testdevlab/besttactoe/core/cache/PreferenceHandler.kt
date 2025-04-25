package com.testdevlab.besttactoe.core.cache

import com.testdevlab.besttactoe.core.cache.models.ChosenVisualDBModel
import com.testdevlab.besttactoe.ui.theme.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object PreferenceHandler {
    private val _chosenVisuals = MutableStateFlow<ChosenVisualDBModel?>(null)
    private val _userName = MutableStateFlow<String?>(null)
    val chosenVisuals = _chosenVisuals.asStateFlow()
    val userName = _userName.asStateFlow()

    init {
        _userName.update { Preferences.playerName ?: "IOS user" }
        refreshVisuals()
    }

    fun setUserName(name: String, callback: () -> Unit = {}) {
        Preferences.playerName = name
        _userName.update { name }
        callback()
    }

    fun refreshVisuals(callback: () -> Unit = {}) {
        log("refreshed visuals")
        _chosenVisuals.update { Preferences.chosenVisuals?.toObject() }
        callback()
    }
}