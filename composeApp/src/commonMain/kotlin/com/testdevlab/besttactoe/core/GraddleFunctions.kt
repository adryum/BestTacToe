package com.testdevlab.besttactoe.core

import com.testdevlab.besttactoe.BuildKonfig

@Suppress("KotlinConstantConditions")
fun isRelease() = BuildKonfig.flavor == "r"