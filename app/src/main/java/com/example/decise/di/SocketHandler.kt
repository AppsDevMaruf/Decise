package com.example.decise.di

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    private lateinit var mSocket: Socket
    @Synchronized
    fun setSocket() {
        try {
// "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
// "http://localhost:3000/" will not work
// If you want to use your physical phone you could use your ip address plus :3000
// This will allow your Android Emulator and physical device at your home to connect to the server
            mSocket = IO.socket("http://170.64.137.92:7777/decise-websocket")
        } catch (e: URISyntaxException) {
            Log.d("TAG", "setSocketError: $e")

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        Log.d("TAG", "getSocket: ")
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        Log.d("TAG", "establishConnection: ")
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}