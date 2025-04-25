package com.testdevlab.besttactoe.core.cache

import androidx.compose.ui.graphics.toArgb
import com.russhwolf.settings.Settings
import com.testdevlab.besttactoe.core.cache.models.ChosenVisualDBModel
import com.testdevlab.besttactoe.core.cache.models.GameResultDBModel
import com.testdevlab.besttactoe.core.cache.models.HistoryDBModel
import com.testdevlab.besttactoe.core.repositories.GameMode
import com.testdevlab.besttactoe.ui.theme.Blue
import com.testdevlab.besttactoe.ui.theme.CircleType
import com.testdevlab.besttactoe.ui.theme.CrossType
import com.testdevlab.besttactoe.ui.theme.Red
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object Preferences {
    private val _settings: Settings = Settings()
    // vars are delegating (giving) their functionalities, e.g. get() and set(), to
    // delegated (given) delegations (in this situation delegations are properties)
    // so when this property (var) is called it uses delegated functionality.
    var playerName by stringPreference()
    var chosenVisuals by stringPreference() // x or o  icons
    var hotSeat by stringPreference()
    var vsAI by stringPreference()
    var history by stringPreference()
        private set
    var isSoundEnabled by stringPreference()
    var isAnimationEnabled by stringPreference()
    var resolution by stringPreference()

    init {
//        chosenVisuals = null
        if (chosenVisuals.isNullOrEmpty())
            chosenVisuals = ChosenVisualDBModel(
                opponentIcon = CircleType.Circle,
                opponentTint = Red.toArgb(),
                playerIcon = CrossType.Cross,
                playerTint = Blue.toArgb()
            ).toJson()

//        history = null
//        hotSeat = null
//        vsAI = null
    }

    /**(Example) var playerName by stringPreference()
     *
     * here by calling this property it takes delegated get function which is getting
     * string value from "database" with key which is property.name "playerName"
     *
     * so get result will be string value from "database" with key "playerName"
     **/
    private fun stringPreference()
    // object : ReadWriteProperty<Any?, String?>
    // (object) is an anonymous object this is used when you don't want to create a new class
    // (ReadWriteProperty<Any?, String?>) provide getValue and setValue methods "READ" and "WRITE"
    = object : ReadWriteProperty<Any?, String?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) =
            _settings.getString(key = property.name, defaultValue = "").takeIf { it.isNotBlank() }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
            _settings.putString(key = property.name, value = value ?: "")
        }
    }

    fun addGameToHistory(game: GameResultDBModel) {
        // add to list or create new list
        history = history?.let { stringJson ->
            val newResults = stringJson
                .toObject<HistoryDBModel>()
                .results
                .toMutableList()
                .apply { add(game) }
                .toList()

            HistoryDBModel(results = newResults).toJson()
        } ?: HistoryDBModel(results = listOf(game)).toJson()
    }

    fun isThereASavedGame(gameMode: GameMode) = when (gameMode) {
        GameMode.VS_AI -> !vsAI.isNullOrEmpty()
        GameMode.HotSeat -> !hotSeat.isNullOrEmpty()
        GameMode.None,
        GameMode.Multiplayer,
        GameMode.RoboRumble -> false
    }
}
