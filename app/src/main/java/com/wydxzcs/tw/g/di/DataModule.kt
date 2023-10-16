package com.wydxzcs.tw.g.di

import android.content.Context
import com.wydxzcs.tw.g.data.repository.GeneralAppStateRepositoryImpl
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule(val context: Context){

    @Provides
    fun provideContext() : Context{
        return context
    }

    @Provides
    @Singleton
    fun provideGeneralAppStateRepository(): GeneralAppStateRepository{
        return GeneralAppStateRepositoryImpl(context = context)
    }

}