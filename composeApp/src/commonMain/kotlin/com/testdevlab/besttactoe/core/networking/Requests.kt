package com.testdevlab.besttactoe.core.networking

import kotlinx.serialization.Serializable

sealed class SocketRequest {
    data class GameCreated(val content: RequestGameCreate) : SocketRequest() {
        val request = RequestType.GAME_CREATE
    }
    data class GameJoin(val content: RequestJoin) : SocketRequest() {
        val request = RequestType.GAME_JOIN
    }
    data class GameLeave(val content: RequestCode) : SocketRequest() {
        val request = RequestType.GAME_LEAVE
    }
    data class GameRematch(val content: RequestCode) : SocketRequest() {
        val request = RequestType.GAME_REMATCH
    }
    data class RoundEnd(val content: RequestEndRound) : SocketRequest() {
        val request = RequestType.ROUND_END
    }
    data class MoveMake(val content: RequestMakeMove) : SocketRequest() {
        val request = RequestType.MOVE_MAKE
    }
}

enum class RequestType(val request: String) {
    GAME_CREATE("game_create"),
    GAME_JOIN("game_join"),
    GAME_LEAVE("game_leave"),
    GAME_REMATCH("game_rematch"),
    ROUND_END("round_end"),
    MOVE_MAKE("move_make")
}

@Serializable
data class RequestCode(val code: String)

@Serializable
data class RequestGameCreate(
    val name: String,
    val firstTo: Int
)

@Serializable
data class RequestMakeMove(
    val id: Int,
    val code: String,
    val segment: Int,
    val piece: Int,
)

@Serializable
data class RequestJoin(
    val code: String,
    val name: String,
)

@Serializable
data class RequestEndRound(
    val id: Int,
    val code: String,
)