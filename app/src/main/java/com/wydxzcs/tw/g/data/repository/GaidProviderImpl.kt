package com.wydxzcs.tw.g.data.repository

import android.content.Context
import com.facebook.internal.AttributionIdentifiers
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.domain.repository.GaidProvider
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GaidProviderImpl @Inject constructor(
    val context: Context,
    val generalAppStateRepository: GeneralAppStateRepository
) : GaidProvider {
    override suspend fun provideGaid() = withContext(Dispatchers.IO) {
        generalAppStateRepository.saveGaid(
            AttributionIdentifiers.getAttributionIdentifiers(context)?.androidAdvertiserId
                ?: JConstants.dataIsNotReceived
        )
    }
}