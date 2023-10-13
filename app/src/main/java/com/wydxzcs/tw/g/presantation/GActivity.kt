package com.wydxzcs.tw.g.presantation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.databinding.ActivityGBinding

class GActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}