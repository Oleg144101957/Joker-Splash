package com.wydxzcs.tw.g.di

import android.content.Context
import com.wydxzcs.tw.g.data.repository.AdbProviderImpl
import com.wydxzcs.tw.g.data.repository.FBProviderImpl
import com.wydxzcs.tw.g.data.repository.GaidProviderImpl
import com.wydxzcs.tw.g.data.repository.GeneralAppStateRepositoryImpl
import com.wydxzcs.tw.g.data.repository.RefProviderImpl
import com.wydxzcs.tw.g.domain.repository.AdbProvider
import com.wydxzcs.tw.g.domain.repository.FbProvider
import com.wydxzcs.tw.g.domain.repository.GaidProvider
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import com.wydxzcs.tw.g.domain.repository.RefProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideGeneralAppStateRepository(): GeneralAppStateRepository {
        return GeneralAppStateRepositoryImpl(context = context)
    }

    @Provides
    fun provideAdbProvider(
        context: Context,
        generalAppStateRepository: GeneralAppStateRepository
    ): AdbProvider {
        return AdbProviderImpl(
            context = context,
            generalAppStateRepository = generalAppStateRepository
        )
    }

    @Provides
    fun provideFBProvider(
        context: Context,
        generalAppStateRepository: GeneralAppStateRepository
    ): FbProvider {
        return FBProviderImpl(
            context = context,
            generalAppStateRepository = generalAppStateRepository
        )
    }

    @Provides
    fun provideGaidProvider(
        context: Context,
        generalAppStateRepository: GeneralAppStateRepository
    ): GaidProvider {
        return GaidProviderImpl(
            context = context,
            generalAppStateRepository = generalAppStateRepository
        )
    }

    @Provides
    fun provideRefProvider(
        context: Context,
        generalAppStateRepository: GeneralAppStateRepository
    ): RefProvider {
        return RefProviderImpl(
            context = context,
            generalAppStateRepository = generalAppStateRepository
        )
    }
}