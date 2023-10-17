package com.wydxzcs.tw.g.data.repository

import android.content.Context
import com.wydxzcs.tw.g.data.storage.JStorageBool
import com.wydxzcs.tw.g.domain.models.GeneralAppState
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GeneralAppStateRepositoryImpl @Inject constructor(context: Context) :
    GeneralAppStateRepository {


    //Inject using di later
    private val jStorageBool = JStorageBool(context)









    private val mutableStatusFlow =
        MutableStateFlow(GeneralAppState(isModer = jStorageBool.readBool()))
    val statusFlow: StateFlow<GeneralAppState> = mutableStatusFlow

    override fun saveLink(link: String) {
        mutableStatusFlow.value = mutableStatusFlow.value.copy(link = link)
    }

    override fun saveAdb(adb: String) {
        mutableStatusFlow.value = mutableStatusFlow.value.copy(adb = adb)
    }

    override fun saveRefferer(refferer: String) {
        mutableStatusFlow.value = mutableStatusFlow.value.copy(refferer = refferer)
    }

    override fun saveDeeplink(deeplink: String) {
        mutableStatusFlow.value = mutableStatusFlow.value.copy(deeplink = deeplink)
    }

    override fun saveGaid(gaid: String) {
        mutableStatusFlow.value = mutableStatusFlow.value.copy(gaid = gaid)
    }

    override fun saveIsModer(isModer: Boolean) {
        mutableStatusFlow.value = mutableStatusFlow.value.copy(isModer = isModer)
    }
}