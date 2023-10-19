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

    private fun setClickListennersForTheButtons() {
        binding.btnPlay.setOnClickListener {
            playGame()
        }

        binding.btnExit.setOnClickListener {
            exitJokerSplash()
        }
    }

    private fun exitJokerSplash() {
        finishAffinity()

//        val intent = Intent(this, LActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finish()

//        val intentToExitApp = Intent(Intent.ACTION_MAIN)
//        intentToExitApp.addCategory(Intent.CATEGORY_HOME)
//        intentToExitApp.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(intentToExitApp)
//        finish()
//        exitProcess(0)
    }

    private fun playGame() {
        val intentToPlayGame = Intent(this, GActivity::class.java)
        startActivity(intentToPlayGame)
    }
}