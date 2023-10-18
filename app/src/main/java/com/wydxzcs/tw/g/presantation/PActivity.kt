package com.wydxzcs.tw.g.presantation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.databinding.ActivityPBinding

class PActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}