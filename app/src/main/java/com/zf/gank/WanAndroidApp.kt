package com.zf.gank

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WanAndroidApp : Application() {

    companion object {
        lateinit var instance: WanAndroidApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}