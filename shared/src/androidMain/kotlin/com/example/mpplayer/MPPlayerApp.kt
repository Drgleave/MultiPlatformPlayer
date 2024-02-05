package com.example.mpplayer


import android.app.Application
import android.content.Context

class MPPlayerApp : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}