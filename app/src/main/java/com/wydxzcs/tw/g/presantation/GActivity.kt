package com.wydxzcs.tw.g.presantation

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wydxzcs.tw.g.R
import com.wydxzcs.tw.g.databinding.ActivityGBinding
import com.wydxzcs.tw.g.domain.models.GameStatus
import com.wydxzcs.tw.g.presantation.vm.DecreasePoints
import com.wydxzcs.tw.g.presantation.vm.IncreasePoints
import com.wydxzcs.tw.g.presantation.vm.JViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import kotlin.random.Random

class GActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGBinding
    private lateinit var jViewModel: JViewModel
    private var displayWidthInFloat by Delegates.notNull<Float>()
    private var displayHeightInFloat by Delegates.notNull<Float>()
    private var delayBetweenElements = 2000L
    private var fallingDuration = 1500L

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGBinding.inflate(layoutInflater)
        jViewModel = ViewModelProvider(this)[JViewModel::class.java]
        setContentView(binding.root)
        displayWidthInFloat = resources.displayMetrics.widthPixels.toFloat()
        displayHeightInFloat = resources.displayMetrics.heightPixels.toFloat()

        jViewModel.stateGame.observe(this){
            binding.pointsTextField.text = "Points: ${it.points}"

            if (it.gameStatus == GameStatus.GAME_OVER){
                finishGame("Game over")
            }
        }

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

                if ((jViewModel.stateGame.value?.gameStatus
                        ?: GameStatus.PLAY_GAME) == GameStatus.PLAY_GAME
                ){
                    createFallingElement()
                    if (i == 20){
                        val points = jViewModel.stateGame.value?.points
                        finishGame("You win, you have $points points")
                    }
                }
            }
        }
    }

    private fun finishGame(msg: String) {
        val finishGameLayout = FrameLayout(this)
        finishGameLayout.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK)
        }

        //msg
        val textView = TextView(this)
        textView.apply {
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            layoutParams = params
            setTextColor(Color.WHITE)
            text = msg
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        }

        //btn close
        val imageClose = ImageView(this)
        imageClose.setImageResource(R.drawable.baseline_close_24)

        val imageCloseParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END // This sets the ImageView to the top right corner.
        }

        imageClose.setOnClickListener {
            goToTheMenuScreen()
        }

        imageClose.apply {
            layoutParams = imageCloseParams
        }

        binding.root.addView(finishGameLayout)
        finishGameLayout.addView(textView)
        finishGameLayout.addView(imageClose)
    }

    private fun goToTheMenuScreen() {
        val intentToMenu = Intent(this, MActivity::class.java)
        startActivity(intentToMenu)
    }

    private fun createFallingElement() {
        val element = ImageView(this)
        binding.root.addView(element)

        element.apply {
            layoutParams.width = 150
            layoutParams.height = 150
            setImageResource(listOfImages[Random.nextInt(listOfImages.size)])
            x = Random.nextFloat()*(displayWidthInFloat-150)
            y = 0F

            animate().apply {
                duration = fallingDuration

                if (fallingDuration>200){
                    fallingDuration -= 100L
                }
                //x(displayWidthInFloat/2)
                y(displayHeightInFloat-60F)

                withEndAction {
                    //check collisions here
                    if (checkElementsCollision(element)){
                        jViewModel.sendData(IncreasePoints())
                        binding.root.removeView(element)
                    } else {
                        //decrease scores
                        jViewModel.sendData(DecreasePoints())
                        binding.root.removeView(element)
                    }
                }
            }
        }
    }

    private fun checkElementsCollision(element: View) : Boolean{
        val elementRect = Rect()
        element.getGlobalVisibleRect(elementRect)

        val movableElementRect = Rect()
        binding.movableJoker.getGlobalVisibleRect(movableElementRect)

        return Rect.intersects(elementRect, movableElementRect)
    }

    private val listOfImages = listOf(
        R.drawable.zxzxf,
        R.drawable.zxzxfi,
        R.drawable.zxzxt,
        R.drawable.zxzxth
    )
}