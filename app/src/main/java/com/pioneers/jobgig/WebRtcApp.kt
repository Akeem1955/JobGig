package com.pioneers.jobgig

import android.app.Application
import io.getstream.log.android.AndroidStreamLogger

class WebRtcApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidStreamLogger.installOnDebuggableApp(this)
    }
}