package com.fggc.garden_mate

import android.app.Application
import com.fggc.garden_mate.data.network.Backend
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyAppCrud : Application(){
    override fun onCreate() {
        super.onCreate()
        Backend.initialize(applicationContext)
    }
}