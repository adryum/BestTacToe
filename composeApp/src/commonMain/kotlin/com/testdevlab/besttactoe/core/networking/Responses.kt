package com.testdevlab.besttactoe.core.networking

enum class SocketResponse(val response: String) {
    GAME_CREATED("game_created"),
    GAME_STARTED("game_started"),
    GAME_ENDED("game_ended"),
    GAME_TURN("game_turn"),
    ROUND_ENDED("round_ended"),
    MOVE_MADE("move_made"),
    ERROR("error")
}