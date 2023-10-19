package com.wydxzcs.tw.g.presantation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.databinding.ActivityNBinding

class NActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.butonRefreshScreen.setOnClickListener {
            val intentToTheStartScreen = Intent(this, LActivity::class.java)
            startActivity(intentToTheStartScreen)
        }
    }
}