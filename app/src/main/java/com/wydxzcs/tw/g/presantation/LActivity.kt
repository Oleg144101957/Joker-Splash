package com.wydxzcs.tw.g.presantation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.wydxzcs.tw.g.JApp
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.data.repository.GeneralAppStateRepositoryImpl
import com.wydxzcs.tw.g.data.storage.JStorageBool
import com.wydxzcs.tw.g.databinding.ActivityLBinding
import com.wydxzcs.tw.g.domain.featurecases.CollectDataFromAllSourcesUseCase
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLBinding

    @Inject
    lateinit var generalAppStateRepository: GeneralAppStateRepository

    @Inject
    lateinit var collectDataFromAllSourcesUseCase: CollectDataFromAllSourcesUseCase




    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLBinding.inflate(layoutInflater)

        (applicationContext as JApp).appComponent.inject(this)

        setContentView(binding.root)
        firstTimeListennerAndDataGetter()

    }

    private fun firstTimeListennerAndDataGetter(){
        lifecycleScope.launch {
            (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.collect {
                Log.d("123123", "Data in generalAppStateRepo $it")
                if (it.gaid != JConstants.emptyData && it.adb != JConstants.emptyData
                    && it.refferer != JConstants.emptyData && it.deeplink != JConstants.emptyData){
                    //The data is ready
                    //Build link and save to the general app repo and go to the policy
                    navigateToThePolicy()
                }
            }
        }

        lifecycleScope.launch {
            collectDataFromAllSourcesUseCase.getDataFromAllSources()
        }
    }

    private fun navigateToTheMenu() {
        val intentToTheMenu = Intent(this, MActivity::class.java)
        startActivity(intentToTheMenu)
    }

    private fun navigateToThePolicy() {
        val storage = JStorageBool(this)
        //storage.saveLink("https://jokersplash.online/privacypolicy")
        storage.saveLink("https://jokersplash.online/")
        val intentToThePActivity = Intent(this, PActivity::class.java)

        val g = (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.value.gaid
        val r = (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.value.refferer
        val a = (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.value.adb
        val d = (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.value.deeplink

        intentToThePActivity.putExtra(JConstants.gKey, g)
        intentToThePActivity.putExtra(JConstants.rKey, r)
        intentToThePActivity.putExtra(JConstants.aKey, a)
        intentToThePActivity.putExtra(JConstants.fKey, d)

        startActivity(intentToThePActivity)
    }
}