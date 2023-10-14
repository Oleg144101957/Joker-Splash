package com.wydxzcs.tw.g

import android.app.Application
import com.wydxzcs.tw.g.di.AppComponent
import com.wydxzcs.tw.g.di.DaggerAppComponent

class JApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().

    }
}