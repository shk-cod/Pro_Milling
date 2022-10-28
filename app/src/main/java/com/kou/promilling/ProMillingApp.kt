package com.kou.promilling

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ProMillingApp : Application() {
    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}