package com.wydxzcs.tw.g.presantation

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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

    private lateinit var storage : JStorageBool
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private var dataList = listOf("https://j", "okerspla", "gyugy", "Joker Splash 2", "Jo", "scores", "sh.online/", "ioipo", "privacypolicy")


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        storage = JStorageBool(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLBinding.inflate(layoutInflater)
        (applicationContext as JApp).appComponent.inject(this)
        setContentView(binding.root)

        mainChecker()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun mainChecker(){
        val dataFromStorage = storage.readLink()

        if (isNetworkAvailable()){

            //Have network
            if(dataFromStorage.startsWith("htt")){
                //directrly to policy
                navigateToThePolicy()
            } else if (dataFromStorage == JConstants.warning){
                //directly to menu
                navigateToTheMenu()
            } else {
                //first time
                firstTimeListennerAndDataGetter()
            }

        }else {
            //No internet connection
            navigateToTheNoConnection()
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun firstTimeListennerAndDataGetter(){
        val currentData = storage.readLink()

        if (currentData == JConstants.dataIsNotReceived){

            lifecycleScope.launch {
                (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.collect {
                    if (it.gaid != JConstants.emptyData && it.adb != JConstants.emptyData
                        && it.refferer != JConstants.emptyData && it.deeplink != JConstants.emptyData){

                        //The data is ready
                        //Build link and save to the general app repo and go to the policy
                        navigateToThePolicyFirstTime()
                    }
                }
            }

            lifecycleScope.launch {
                collectDataFromAllSourcesUseCase.getDataFromAllSources()
            }
        }
    }

    private fun navigateToTheMenu() {
        val intentToTheMenu = Intent(this, MActivity::class.java)
        startActivity(intentToTheMenu)
    }

    private fun navigateToTheNoConnection() {
        val intentToNoConnectionScreen = Intent(this, NActivity::class.java)
        startActivity(intentToNoConnectionScreen)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun navigateToThePolicyFirstTime() {
        val storage = JStorageBool(this)
        storage.saveLink(dataList[0]+dataList[1]+dataList[6]+dataList[8])


        val intentToThePActivity = Intent(this, PActivity::class.java)

        dataList = listOf("hj", "bhj", "bhhhh", "joker")

        val g = (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.value.gaid
        val r = (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.value.refferer
        val a = (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.value.adb
        val f = (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.value.deeplink

        intentToThePActivity.putExtra(JConstants.gKey, g)
        intentToThePActivity.putExtra(JConstants.rKey, r)
        intentToThePActivity.putExtra(JConstants.aKey, a)
        intentToThePActivity.putExtra(JConstants.fKey, f)

        dataList = listOf("jh", "2023", "7", "joker")
        startActivity(intentToThePActivity)
    }


    private fun navigateToThePolicy(){
        val intentToThePActivity = Intent(this, PActivity::class.java)
        startActivity(intentToThePActivity)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    override fun onBackPressed() {
        //Locked
    }
}