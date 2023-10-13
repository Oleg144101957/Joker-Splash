package com.wydxzcs.tw.g.presantation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.databinding.ActivityMBinding

class MActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}