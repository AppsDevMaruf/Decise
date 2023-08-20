package com.example.decise.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.decise.di.SocketHandler
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DeciseApplication : Application() {


    init {
        instance = this
    }

    companion object {
        private var instance: DeciseApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = applicationContext()

        SocketHandler.setSocket()

        SocketHandler.establishConnection()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)


 /*       val mSocket = SocketHandler.getSocket()
        mSocket.on("topic/decision-chat") { args ->
            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("TAG", counter.toString())

            }
        }*/

    }



}