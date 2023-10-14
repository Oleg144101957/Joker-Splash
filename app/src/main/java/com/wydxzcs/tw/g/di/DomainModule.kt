package com.wydxzcs.tw.g.di

import com.wydxzcs.tw.g.domain.featurecases.FirstTimeUseCase
import dagger.Module


@Module
class DomainModule {
    fun provideFirstTimeUseCase() : FirstTimeUseCase{
        return FirstTimeUseCase()
    }

}