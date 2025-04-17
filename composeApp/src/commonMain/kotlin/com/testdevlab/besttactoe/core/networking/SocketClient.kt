package com.testdevlab.besttactoe.core.networking

import com.testdevlab.besttactoe.BuildKonfig
import com.testdevlab.besttactoe.core.cache.toObject
import com.testdevlab.besttactoe.core.repositories.GameHandler
import com.testdevlab.besttactoe.ui.theme.log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.client.SocketOptionBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject

object SocketClient {
    private var _socket: Socket? = null
    private val _events = MutableSharedFlow<SocketEvent>(1)
    val events = _events.asSharedFlow()

    fun disconnect() {
        _socket?.disconnect()
    }

    private fun isDisconnected(): Boolean {
        if (_socket == null) connect()

        return _socket == null
    }

    fun connect() {
        try {
            // makes socket
            _socket = IO.socket(
                BuildKonfig.SOCKET_URL,
                SocketOptionBuilder.builder().setReconnectionDelay(30000).build()
            )

            // might throw error
            _socket!!.connect()

            listenForEvents()
        } catch (e: Exception) {
            log("Failed to connect to socket: $e")
            _events.tryEmit(SocketEvent.Error(SocketError.FAILED_TO_CONNECT))
        }
    }

    fun sendMessage(requestType: RequestType, message: String) {
        if (isDisconnected()) {
            _events.tryEmit(SocketEvent.Error(SocketError.FAILED_TO_SEND_MESSAGE))
            return
        }

        // Converts to JSON before sending to socket.
        val json = JSONObject(message)

        // Sends request to socket.
        _socket?.emit(requestType.request, json)
    }

    // listens to socket
    fun listenForEvents() {
        // dataArr -> [0] response name; -> [1] json
        // to use json.toObject() this must be called in @Serialized data class model
        _socket?.on(SocketResponse.GAME_CREATED.response) { data ->
            log("${data[0]}  ${data[1]}")
            data[1].toString().let { response ->
                _events.tryEmit(SocketEvent.GameCreated(response.toObject()))
            }
        }
        _socket?.on(SocketResponse.GAME_STARTED.response) { data ->
            log("${data[0]}  ${data[1]}")
            data[1].toString().let { response ->
                _events.tryEmit(SocketEvent.GameStarted(response.toObject()))
            }
        }
        _socket?.on(SocketResponse.GAME_TURN.response) { data ->
            log("${data[0]}  ${data[1]}")
            data[1].toString().let { response ->
                _events.tryEmit(SocketEvent.GameTurn(response.toObject()))
            }
        }
        _socket?.on(SocketResponse.MOVE_MADE.response) { data ->
            log("${data[0]}  ${data[1]}")
            data[1].toString().let { response ->
                _events.tryEmit(SocketEvent.MoveMade(response.toObject()))
            }
        }
        _socket?.on(SocketResponse.GAME_ENDED.response) { data ->
            log("${data[0]}  ${data[1]}")
            data[1].toString().let { response ->
                _events.tryEmit(SocketEvent.GameEnded(response.toObject()))
            }
        }
        _socket?.on(SocketResponse.ROUND_ENDED.response) { data ->
            log("${data[0]}  ${data[1]}")
            data[1].toString().let { response ->
                _events.tryEmit(SocketEvent.RoundEnded(response.toObject()))
            }
        }
        _socket?.on(SocketResponse.ERROR.response) { data ->
            log("${data[0]}  ${data[1]}")
            log("${GameHandler.code.value}")
        }
        _socket?.on(SocketResponse.REMATCH.response) { data ->
            log("${data[0]}  ${data[1]}")
            data[1].toString().let { response ->
                _events.tryEmit(SocketEvent.Rematch(response.toObject()))
            }
        }
    }
}