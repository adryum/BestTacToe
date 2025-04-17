package com.testdevlab.besttactoe.core.networking

import kotlinx.serialization.Serializable

sealed class SocketEvent {
    data class GameCreated(val response: CodeSocketResponseModel) : SocketEvent()
    data class GameStarted(val response: GameStartedEventSocketResponseModel) : SocketEvent()
    data class GameEnded(val response: CodeSocketResponseModel) : SocketEvent()
    data class GameTurn(val response: CodeIdEventSocketResponseModel) : SocketEvent()
    data class RoundEnded(val response: CodeIdEventSocketResponseModel) : SocketEvent()
    data class MoveMade(val response: MadeMoveEventSocketResponseModel) : SocketEvent()
    data class Rematch(val response: CodeSocketResponseModel) : SocketEvent()
    data class Error(val socketError: SocketError) : SocketEvent()
}

@Serializable
data class CodeSocketResponseModel(
    val code: String
)

@Serializable
data class MadeMoveEventSocketResponseModel(
    val id: Int,
    val code: String,
    val segment: Int,
    val piece: Int,
)

@Serializable
data class CodeIdEventSocketResponseModel(
    val id: Int,
    val code: String,
)

@Serializable
data class GameStartedEventSocketResponseModel(
    val code: String,
    val selfId: Int,
    val selfName: String,
    val opponentId: Int,
    val opponentName: String
)