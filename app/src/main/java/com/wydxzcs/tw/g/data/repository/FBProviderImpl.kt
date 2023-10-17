package com.wydxzcs.tw.g.data.repository

import android.content.Context
import com.facebook.applinks.AppLinkData
import com.wydxzcs.tw.g.domain.repository.FbProvider
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import javax.inject.Inject
import kotlin.coroutines.resume

class FBProviderImpl @Inject constructor(
    val context: Context,
    val generalAppStateRepository: GeneralAppStateRepository
) : FbProvider {
    override suspend fun provideFb() {
        AppLinkData.fetchDeferredAppLinkData(context){
            generalAppStateRepository.saveDeeplink(it?.targetUri.toString())
        }
    }
}