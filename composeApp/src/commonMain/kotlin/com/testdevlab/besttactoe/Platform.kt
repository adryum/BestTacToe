package com.testdevlab.besttactoe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform