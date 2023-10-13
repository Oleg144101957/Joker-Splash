package com.wydxzcs.tw.g.presantation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.wydxzcs.tw.g.databinding.ActivityLBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToTheMenu()

    }

    private fun navigateToTheMenu() {
        val intentToTheMenu = Intent(this, MActivity::class.java)

        lifecycleScope.launch {
            delay(2000)
            startActivity(intentToTheMenu)
        }
    }
}