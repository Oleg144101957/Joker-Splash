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
import com.wydxzcs.tw.g.databinding.ActivityLBinding
import com.wydxzcs.tw.g.domain.featurecases.CollectDataFromAllSourcesUseCase
import com.wydxzcs.tw.g.domain.models.GeneralAppState
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import kotlinx.coroutines.delay
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

        lifecycleScope.launch {
            (generalAppStateRepository as GeneralAppStateRepositoryImpl).statusFlow.collect {
                Log.d("123123", "Data in generalAppStateRepo $it")
                //if it != empty_data go to the menu screen with intent and extras
                checkAppState(it)
            }
        }

        lifecycleScope.launch {
            collectDataFromAllSourcesUseCase.getDataFromAllSources()
        }
    }

    private fun checkAppState(state: GeneralAppState) {
        if (state.adb != JConstants.emptyData && state.gaid != JConstants.emptyData &&
                state.deeplink != JConstants.emptyData && state.refferer != JConstants.emptyData && !state.isModer
        ) {
            // go to the menu (First time use case)
            navigateToTheMenu(navigateMode = NavigateMode.FirstTime)
        } else if (state.link.startsWith("htt") && !state.isModer){
            //go to the menu (have link in memory)
            navigateToTheMenu(navigateMode = NavigateMode.NotFirstTimeUser)
        } else if(state.isModer){
            //go to the menu Moderator mode
            navigateToTheMenu(navigateMode = NavigateMode.NotFirstTimeModerator)
        }
    }

    private fun navigateToTheMenu(navigateMode: NavigateMode) {
        val intentToTheMenu = Intent(this, MActivity::class.java)

        when (navigateMode){
            is NavigateMode.NotFirstTimeUser -> {
                intentToTheMenu.putExtra(JConstants.goToMenuModeKey, NavigateMode.NotFirstTimeUser.mode)
                startActivity(intentToTheMenu)
            }

            is NavigateMode.FirstTime -> {
                intentToTheMenu.putExtra(JConstants.goToMenuModeKey, NavigateMode.FirstTime.mode)
                startActivity(intentToTheMenu)
            }

            is NavigateMode.NotFirstTimeModerator -> {
                lifecycleScope.launch {
                    intentToTheMenu.putExtra(JConstants.goToMenuModeKey, NavigateMode.NotFirstTimeModerator.mode)
                    delay(2500)
                    startActivity(intentToTheMenu)
                }
            }
        }
    }
}


sealed class NavigateMode(val mode: String){
    data object FirstTime : NavigateMode(mode = "first_time")
    data object NotFirstTimeUser : NavigateMode(mode = "not_first_time_user")
    data object NotFirstTimeModerator : NavigateMode(mode = "not_first_time_moderator")

}