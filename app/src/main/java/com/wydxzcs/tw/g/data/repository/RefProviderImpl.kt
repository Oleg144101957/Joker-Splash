package com.wydxzcs.tw.g.data.repository

import android.content.Context
import android.os.DeadObjectException
import android.util.Log
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import com.wydxzcs.tw.g.domain.repository.RefProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefProviderImpl @Inject constructor(
    val context: Context,
    val generalAppStateRepository: GeneralAppStateRepository
) : RefProvider {

    override suspend fun provideRef() = withContext(Dispatchers.IO) {
        val referrerClient = InstallReferrerClient.newBuilder(context).build()

        referrerClient.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(respondCode: Int) {

                if (respondCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                    try {
                        val result = referrerClient?.installReferrer?.installReferrer
                            ?: JConstants.dataIsNotReceived
                        referrerClient.endConnection()
                        generalAppStateRepository.saveRefferer(result)
                    } catch (e: DeadObjectException) {
                        Log.d("123123", "Refferer exception $e")
                        generalAppStateRepository.saveRefferer(JConstants.dataIsNotReceived)
                    }

                } else {
                    generalAppStateRepository.saveRefferer(JConstants.dataIsNotReceived)
                }
            }

            override fun onInstallReferrerServiceDisconnected() {

            }
        })
    }
}