package com.wydxzcs.tw.g.di

import dagger.Component


@Component(modules = [DataModule::class, DomainModule::class])
interface AppComponent {

}