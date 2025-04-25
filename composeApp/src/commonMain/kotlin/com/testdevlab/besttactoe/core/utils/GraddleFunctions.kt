package com.testdevlab.besttactoe.core.utils

import com.testdevlab.besttactoe.BuildKonfig

@Suppress("KotlinConstantConditions")
fun isRelease() = BuildKonfig.flavor == "r"