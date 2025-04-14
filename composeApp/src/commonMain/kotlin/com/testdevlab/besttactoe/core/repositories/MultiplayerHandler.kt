package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.core.cache.Preferences
import com.testdevlab.besttactoe.core.cache.toJson
import com.testdevlab.besttactoe.core.networking.RequestEndRound
import com.testdevlab.besttactoe.core.networking.RequestJoin
import com.testdevlab.besttactoe.core.networking.RequestMakeMove
import com.testdevlab.besttactoe.core.networking.RequestString
import com.testdevlab.besttactoe.core.networking.SocketClient
import com.testdevlab.besttactoe.core.networking.SocketRequest

object MultiplayerHandler {
    fun CreateLobby() {
        SendCreateGame(name = Preferences.playerName ?: "RobloxBlaster22")
    }

    fun JoinLobby(code: String) {
        SendGameJoin(
            code = code,
            name = Preferences.playerName ?: "RobloxBlaster22"
        )
    }

    fun SendCreateGame(name: String) {
        val content = SocketRequest.GameCreated(
            content = RequestString(name)
        )

        SocketClient.sendMessage(
            requestType = content.request,
            message = content.content.toJson()
        )
    }

    fun SendGameJoin(code: String, name: String) {
        val content = SocketRequest.GameJoin(
            content = RequestJoin(
                code = code,
                name = name
            )
        )

        SocketClient.sendMessage(
            requestType = content.request,
            message = content.content.toJson()
        )
    }

    fun SendGameLeave(code: String) {
        val request = SocketRequest.GameLeave(
            content = RequestString(argString = code)
        )

        SocketClient.sendMessage(
            requestType = request.request,
            message = request.content.toJson()
        )
    }

    fun SendRematch(code: String) {
        val request = SocketRequest.GameRematch(
            content = RequestString(argString = code)
        )

        SocketClient.sendMessage(
            requestType = request.request,
            message = request.content.toJson()
        )
    }

    fun SendRoundEnd(code: String, id: Int) {
        val request = SocketRequest.RoundEnd(
            content = RequestEndRound(
                id = id,
                code = code,
            )
        )

        SocketClient.sendMessage(
            requestType = request.request,
            message = request.content.toJson()
        )
    }

    fun SendMakeMove(code: String, id: Int, segment: Int, piece: Int) {
        val request = SocketRequest.MoveMake(
            content = RequestMakeMove(
                id = id,
                code = code,
                segment = segment,
                piece = piece
            )
        )

        SocketClient.sendMessage(
            requestType = request.request,
            message = request.content.toJson()
        )
    }
}