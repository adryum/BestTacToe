package com.testdevlab.besttactoe.ui.navigation

import com.testdevlab.besttactoe.core.common.launchDefault
import com.testdevlab.besttactoe.ui.PopUpModel
import com.testdevlab.besttactoe.ui.theme.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// viewModels are android specific stuff. Can't use it in multiplatform
data object NavigationObject {
    // Idea: MainView -> MultiplayerView -> JoinLobbyGame and so on
    private val _path = mutableListOf(Views.MainView)
    private val _currentView = MutableStateFlow(_path.last())
    private val _isViewLoadingIn = MutableStateFlow(true)
    private val _isPopUpShown = MutableStateFlow(false)
    private val _popUpContent = MutableStateFlow<PopUpModel?>(null)
    private val _isLoadingShown = MutableStateFlow(false)
    val isLoadingShown = _isLoadingShown.asStateFlow()
    val popUpContent = _popUpContent.asStateFlow()
    val isPopUpShown = _isPopUpShown.asStateFlow()
    val isViewLoadingIn = _isViewLoadingIn.asStateFlow()
    val currentView = _currentView.asStateFlow()

    private fun updateCurrentView() = _currentView.update { _path.last() }

    fun goTo(view: Views) {
        _path.add(view)
        updateCurrentView()
    }

    fun goBack() {
        log("goBack")

        _path.removeAt(_path.size - 1)
        updateCurrentView()
    }

    /**
     * Removes last item till gets to needed view.
     * Just bonus functionality - returns false if path doesn't contain that view, returns true otherwise.
     */
    fun goBackTill(view: Views): Boolean {
        if (!_path.contains(view)) return false

        log("goBackTill: $view")

        while (_path.last() != view) {
            _path.removeAt(_path.size - 1)
        }

        updateCurrentView()

        return true
    }

    fun delayedGoTo(view: Views, duration: Long = 500, afterDelayAction: () -> Unit = {}) {
        if (!_isViewLoadingIn.value) return
        log("delayed goTo")

        launchDefault {
            _isViewLoadingIn.update { false }
            delay(duration)
            goTo(view)
            afterDelayAction()
            _isViewLoadingIn.update { true }
        }
    }

    fun delayedGoBack(duration: Long = 500) {
        if (!_isViewLoadingIn.value) return
        log("delayed goBack")

        launchDefault {
            _isViewLoadingIn.update { false }
            delay(duration)
            goBack()
            _isViewLoadingIn.update { true }
        }
    }

    fun setLoading(isLoading: Boolean) { _isLoadingShown.update { isLoading } }

    fun showPopUp(content: PopUpModel) {
        _isPopUpShown.update { true }
        _popUpContent.update { content }
    }

    fun hidePopUp() {
        _isPopUpShown.update {   false }
        _popUpContent.update { null }
    }
}

enum class Views {
    MainView,
    GameView,
    MultiplayerView,
    JoinLobbyView,
    CreateLobbyView,
    SettingsView,
    HistoryView,
    CodeView,
    Customization
}