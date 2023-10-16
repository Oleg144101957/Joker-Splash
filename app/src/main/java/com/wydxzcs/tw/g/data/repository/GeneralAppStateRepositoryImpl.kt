package com.wydxzcs.tw.g.data.repository

import android.content.Context
import com.wydxzcs.tw.g.domain.models.GeneralAppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GeneralAppStateRepositoryImpl(val context: Context ) {

    private val mutableStatusFlow = MutableStateFlow(GeneralAppState())
    val statusFlow: StateFlow<GeneralAppState> = mutableStatusFlow

    fun saveLink(link: String){
        mutableStatusFlow.value = mutableStatusFlow.value.copy(link = link)
    }

    fun saveAdb(adb: String){
        mutableStatusFlow.value = mutableStatusFlow.value.copy(adb = adb)
    }

    fun saveRefferer(refferer: String){
        mutableStatusFlow.value = mutableStatusFlow.value.copy(refferer = refferer)
    }

    fun saveDeeplink(deeplink: String){
        mutableStatusFlow.value = mutableStatusFlow.value.copy(deeplink = deeplink)
    }

    fun saveIsModer(isModer: Boolean){
        mutableStatusFlow.value = mutableStatusFlow.value.copy(isModer = isModer)
    }
}