package com.wydxzcs.tw.g.presantation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.databinding.ActivityMBinding
import kotlin.system.exitProcess

class MActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMBinding

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