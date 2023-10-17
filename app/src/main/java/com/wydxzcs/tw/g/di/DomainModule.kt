package com.wydxzcs.tw.g.di

import com.wydxzcs.tw.g.domain.featurecases.CollectDataFromAllSourcesUseCase
import com.wydxzcs.tw.g.domain.featurecases.FirstTimeUseCase
import com.wydxzcs.tw.g.domain.repository.AdbProvider
import com.wydxzcs.tw.g.domain.repository.FbProvider
import com.wydxzcs.tw.g.domain.repository.GaidProvider
import com.wydxzcs.tw.g.domain.repository.RefProvider
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideFirstTimeUseCase(
        refProvider: RefProvider,
        fbProvider: FbProvider,
        adbProvider: AdbProvider,
        gaidProvider: GaidProvider
    ) : CollectDataFromAllSourcesUseCase{
        return CollectDataFromAllSourcesUseCase(
            refProvider = refProvider,
            fbProvider = fbProvider,
            adbProvider = adbProvider,
            gaidProvider = gaidProvider
        )
    }
}