package com.example.decise.di

import android.content.Context
import android.util.Log
import com.google.android.material.internal.ContextUtils.getActivity
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
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

        mSocket.on("/topic/decision-chat") { args -> println(args.contentToString())
            Log.d("TAG", "${args.contentToString()}:")
        }

        mSocket.connect()


      /*  mSocket.on("topic/decision-chat",    Emitter.Listener { args ->

            val data = args[0] as JSONObject
            val username: String
            val message: String
            Log.d("TAG", "Emitter.Listener: ")
        } );*/
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }


}