package com.testdevlab.besttactoe.core.networking

sealed class SocketEvent {
    data class GameCreated(val code: String) : SocketEvent()
    data class GameStarted(val event: GameStartedEvent) : SocketEvent()
    data class GameEnded(val code: String) : SocketEvent()
    data class GameTurn(val event: CodeIdEvent) : SocketEvent()
    data class RoundEnded(val event: CodeIdEvent) : SocketEvent()
    data class MoveMade(val event: MadeMoveEvent) : SocketEvent()
    data class Error(val socketError: SocketError) : SocketEvent()
}

data class MadeMoveEvent(
    val id: Int,
    val code: String,
    val segment: Int,
    val piece: Int,
)

data class CodeIdEvent(
    val id: Int,
    val code: String,
)

data class GameStartedEvent(
    val code: String,
    val selfId: Int,
    val selfName: String,
    val opponentId: Int,
    val opponentName: String
)