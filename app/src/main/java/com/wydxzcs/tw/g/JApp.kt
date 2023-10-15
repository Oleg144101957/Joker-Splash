package com.wydxzcs.tw.g

import android.app.Application
import android.util.Log
import com.wydxzcs.tw.g.di.AppComponent
import com.wydxzcs.tw.g.di.DaggerAppComponent
import com.wydxzcs.tw.g.di.DomainModule
import com.wydxzcs.tw.g.domain.featurecases.FirstTimeUseCase
import javax.inject.Inject

class JApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .domainModule(DomainModule())
            .build()
    }
}