package com.kou.promilling

import android.app.Application
import timber.log.Timber

class ProMillingApp : Application() {
    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}