package com.testdevlab.besttactoe.core.cache

import kotlinx.serialization.json.Json

val jsonConfig = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
}

inline fun <reified T> T.toJson() = jsonConfig.encodeToString(this)
inline fun <reified T> String.toObject(): T = jsonConfig.decodeFromString(this)