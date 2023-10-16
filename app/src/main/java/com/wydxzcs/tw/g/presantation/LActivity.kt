package com.wydxzcs.tw.g.presantation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.wydxzcs.tw.g.JApp
import com.wydxzcs.tw.g.databinding.ActivityLBinding
import com.wydxzcs.tw.g.domain.featurecases.FirstTimeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLBinding


    @Inject
    lateinit var firstTimeUseCase: FirstTimeUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLBinding.inflate(layoutInflater)

        (applicationContext as JApp).appComponent.inject(this)

        setContentView(binding.root)
        navigateToTheMenu()

        val result = firstTimeUseCase.execute()

    }

    private fun navigateToTheMenu() {
        val intentToTheMenu = Intent(this, MActivity::class.java)

        lifecycleScope.launch {
            delay(2000)
            startActivity(intentToTheMenu)
        }
    }
}