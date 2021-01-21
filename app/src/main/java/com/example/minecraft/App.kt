package com.example.minecraft

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}