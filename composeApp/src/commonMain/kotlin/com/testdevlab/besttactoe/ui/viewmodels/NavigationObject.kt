package com.testdevlab.besttactoe.ui.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// viewModels are android specific stuff. Can't use it in multiplatform
data object NavigationObject {

    // Idea: MainView -> MultiplayerView -> JoinLobbyGame and so on
    private val _path = mutableListOf(Views.MainView)
    private val _currentView = MutableStateFlow(_path.last())
    val currentView = _currentView.asStateFlow()

    private fun updateCurrentView() = _currentView.update { _path.last() }

    fun goTo(view: Views) {
        _path.add(view)
        updateCurrentView()
    }

    fun goBack() {
        _path.removeLast()
        updateCurrentView()
    }

    /**
     * Removes last item till gets to needed view.
     * Just bonus functionality - returns false if path doesn't contain that view, returns true otherwise.
     */
    fun goBackTill(view: Views): Boolean {
        if (!_path.contains(view)) return false

        while (_path.last() != view) {
            _path.removeLast()
        }

        updateCurrentView()

        return true
    }
}

enum class Views {
    MainView,
    GameView,
    MultiplayerView,
    JoinLobbyView,
    CreateLobbyView,
    SettingsView
}