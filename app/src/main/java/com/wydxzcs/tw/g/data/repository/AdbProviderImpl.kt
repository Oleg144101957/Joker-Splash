package com.wydxzcs.tw.g.data.repository

import android.content.Context
import android.provider.Settings
import com.wydxzcs.tw.g.domain.repository.AdbProvider
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import javax.inject.Inject

class AdbProviderImpl @Inject constructor(
    val context: Context,
    val generalAppStateRepository: GeneralAppStateRepository
) : AdbProvider {
    override fun provideAdb() {
        generalAppStateRepository.saveAdb(
            Settings.Global.getString(
                context.contentResolver,
                Settings.Global.ADB_ENABLED
            )
        )
    }
}