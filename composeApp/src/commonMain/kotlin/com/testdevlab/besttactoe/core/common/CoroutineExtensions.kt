package com.testdevlab.besttactoe.core.common

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import com.testdevlab.besttactoe.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
private val defaultScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

fun launchMain(block: suspend CoroutineScope.() -> Unit) = mainScope.launch(
    context = CoroutineExceptionHandler { _, e -> AppLogger.i("err", "Coroutine failed: ${e.localizedMessage}") },
    block = block
)

fun launchDefault(block: suspend CoroutineScope.() -> Unit) = defaultScope.launch(
    context = CoroutineExceptionHandler { _, e -> AppLogger.i("err", "Coroutine failed: ${e.localizedMessage}") },
    block = block
)
