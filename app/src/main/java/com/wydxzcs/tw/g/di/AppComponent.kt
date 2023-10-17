package com.wydxzcs.tw.g.di

import com.wydxzcs.tw.g.presantation.LActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(lActivity: LActivity)

}