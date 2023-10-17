package com.wydxzcs.tw.g.domain.featurecases

import com.wydxzcs.tw.g.domain.repository.AdbProvider
import com.wydxzcs.tw.g.domain.repository.FbProvider
import com.wydxzcs.tw.g.domain.repository.GaidProvider
import com.wydxzcs.tw.g.domain.repository.RefProvider
import javax.inject.Inject

class CollectDataFromAllSourcesUseCase @Inject constructor(
    val refProvider: RefProvider,
    val fbProvider: FbProvider,
    val adbProvider: AdbProvider,
    val gaidProvider: GaidProvider
) {
    suspend fun getDataFromAllSources(){
        refProvider.provideRef()
        fbProvider.provideFb()
        adbProvider.provideAdb()
        gaidProvider.provideGaid()
    }
}