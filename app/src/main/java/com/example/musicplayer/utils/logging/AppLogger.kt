package com.example.musicplayer.utils.logging

import android.util.Log

private const val TAG = "AppLogger"

interface AppLogger {
    fun log(message: String)

    fun error(
        message: String
    )

    companion object {
        val instance: AppLogger by lazy {
            DefaultAppLogger()
        }
    }
}

class DefaultAppLogger() : AppLogger {
    override fun log(message: String) {
        Log.d(TAG, message)
    }

    override fun error(message: String) {
        Log.e(TAG, message)
    }
}