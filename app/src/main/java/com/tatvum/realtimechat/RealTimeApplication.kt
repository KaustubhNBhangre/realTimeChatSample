package com.tatvum.realtimechat

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import timber.log.Timber

class RealTimeApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}