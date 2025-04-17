package com.testdevlab.besttactoe.core.repositories

import com.testdevlab.besttactoe.core.cache.toJson
import com.testdevlab.besttactoe.core.networking.RequestCode
import com.testdevlab.besttactoe.core.networking.RequestEndRound
import com.testdevlab.besttactoe.core.networking.RequestJoin
import com.testdevlab.besttactoe.core.networking.RequestMakeMove
import com.testdevlab.besttactoe.core.networking.RequestName
import com.testdevlab.besttactoe.core.networking.SocketClient
import com.testdevlab.besttactoe.core.networking.SocketRequest

object MultiplayerHandler {
    fun sendCreateGame(name: String) {
        val content = SocketRequest.GameCreated(
            content = RequestName(name)
        )

        SocketClient.sendMessage(
            requestType = content.request,
            message = content.content.toJson()
        )
    }

    fun sendGameJoin(code: String, name: String) {
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

    fun sendGameLeave(code: String) {
        val request = SocketRequest.GameLeave(
            content = RequestCode(code = code)
        )

        SocketClient.sendMessage(
            requestType = request.request,
            message = request.content.toJson()
        )
    }

    fun sendRematch(code: String) {
        val request = SocketRequest.GameRematch(
            content = RequestCode(code = code)
        )

        SocketClient.sendMessage(
            requestType = request.request,
            message = request.content.toJson()
        )
    }

    fun sendRoundEnd(code: String, id: Int) {
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

    fun sendMakeMove(code: String, id: Int, segment: Int, piece: Int) {
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