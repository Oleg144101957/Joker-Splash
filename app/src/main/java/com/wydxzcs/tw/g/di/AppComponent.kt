package com.wydxzcs.tw.g.di

import com.wydxzcs.tw.g.presantation.LActivity
import dagger.Component


@Component(modules = [DomainModule::class])
interface AppComponent {
    fun inject(lActivity: LActivity)

}