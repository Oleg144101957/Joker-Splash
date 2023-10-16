package com.wydxzcs.tw.g.di

import com.wydxzcs.tw.g.data.repository.GeneralAppStateRepositoryImpl
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import dagger.Module
import dagger.Provides


@Module
class DataModule {


    @Provides
    fun provideGeneralAppStateRepository(): GeneralAppStateRepository{
        return GeneralAppStateRepositoryImpl()
    }

}