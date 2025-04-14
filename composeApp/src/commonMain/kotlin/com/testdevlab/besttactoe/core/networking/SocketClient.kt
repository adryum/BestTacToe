package com.testdevlab.besttactoe.core.networking

import com.testdevlab.besttactoe.BuildKonfig
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
            println("Failed to connect to socket: $e")
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

    fun listenForEvents() {
        _socket?.on(SocketResponse.GAME_CREATED.response) { data -> println("Socket Game Created " + data)

        }
        _socket?.on(SocketResponse.GAME_STARTED.response) { println("Socket Game Started " + it)

        }
        _socket?.on(SocketResponse.GAME_TURN.response) { println("Socket Game Turn " + it)

        }
        _socket?.on(SocketResponse.MOVE_MADE.response) { println("Socket Move made " + it )

        }
        _socket?.on(SocketResponse.GAME_ENDED.response) { println("Socket GameEnded " + it)

        }
        _socket?.on(SocketResponse.ROUND_ENDED.response) { println("Socket Round ended " + it)

        }
        _socket?.on(SocketResponse.ERROR.response) { println("Socket ERR " + it)
            println(it[0]) // type
            println(it[1]) // error
        }
    }
}