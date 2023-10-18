package com.wydxzcs.tw.g.presantation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.databinding.ActivityMBinding
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import javax.inject.Inject
import kotlin.system.exitProcess

class MActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMBinding

    @Inject
    lateinit var generalAppStateRepository: GeneralAppStateRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListennersForTheButtons()
    }

//    private fun checkModeStatus(mode: String) {
//        when(mode){
//            NavigateMode.FirstTime.mode -> {
//                // build link and open WebView here
//                buildLink()
//            }
//
//            NavigateMode.NotFirstTimeUser.mode -> {
//                // read link and open webview
//                readSavedLinkAndOpenPolicy()
//            }
//
//            NavigateMode.NotFirstTimeModerator.mode -> {
//                // just open menu, no Web View
//                //do nothing
//            }
//        }
//    }

    private fun setClickListennersForTheButtons() {
        binding.btnPlay.setOnClickListener {
            playGame()
        }

        binding.btnExit.setOnClickListener {
            exitJokerSplash()
        }
    }

    private fun exitJokerSplash() {
        val intentToExitApp = Intent(Intent.ACTION_MAIN)
        intentToExitApp.addCategory(Intent.CATEGORY_HOME)
        intentToExitApp.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intentToExitApp)
        finish()
        exitProcess(0)
    }

    private fun playGame() {
        val intentToPlayGame = Intent(this, GActivity::class.java)
        startActivity(intentToPlayGame)
    }
}