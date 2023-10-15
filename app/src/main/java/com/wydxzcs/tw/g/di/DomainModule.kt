package com.wydxzcs.tw.g.di

import com.wydxzcs.tw.g.domain.featurecases.FirstTimeUseCase
import dagger.Module
import dagger.Provides


@Module
class DomainModule {

    @Provides
    fun provideFirstTimeUseCase() : FirstTimeUseCase{
        return FirstTimeUseCase()
    }
}