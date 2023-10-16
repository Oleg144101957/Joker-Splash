package com.wydxzcs.tw.g.presantation

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wydxzcs.tw.g.R
import com.wydxzcs.tw.g.databinding.ActivityGBinding
import com.wydxzcs.tw.g.presantation.vm.JViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import kotlin.random.Random

class GActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGBinding
    private lateinit var jViewModel: JViewModel
    private var displayWidthInFloat by Delegates.notNull<Float>()
    private var delayBetweenElements = 2000L
    private var fallingDuration = 1500L

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGBinding.inflate(layoutInflater)
        jViewModel = ViewModelProvider(this)[JViewModel::class.java]
        setContentView(binding.root)
        displayWidthInFloat = resources.displayMetrics.widthPixels.toFloat()

        binding.movableJoker.setOnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_MOVE -> {
                    val newXOffset = event.rawX
                    binding.movableJoker.x = newXOffset.coerceIn(0f, displayWidthInFloat - binding.movableJoker.width)
                }
            }
            true
        }

        lifecycleScope.launch {
            for (i in 0..20){
                delay(delayBetweenElements)
                delayBetweenElements -= 100L
                createFallingElement()
            }
        }
    }

    private fun createFallingElement() {
        val element = ImageView(this)
        binding.root.addView(element)

        element.apply {
            layoutParams.width = 150
            layoutParams.height = 150
            setImageResource(listOfImages[Random.nextInt(listOfImages.size)])
            x = Random.nextFloat()*displayWidthInFloat-150
            y = 0F

            animate().apply {
                duration = fallingDuration

                if (fallingDuration>200){
                    fallingDuration -= 100L
                }
                //x(displayWidthInFloat/2)
                y(1500F)

                withEndAction {

                }
            }
        }
    }

    private val listOfImages = listOf(
        R.drawable.zxzxf,
        R.drawable.zxzxfi,
        R.drawable.zxzxt,
        R.drawable.zxzxth
    )
}