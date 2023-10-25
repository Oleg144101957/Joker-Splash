package com.wydxzcs.tw.g.presantation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.review.ReviewManagerFactory
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.data.storage.JStorageBool
import com.wydxzcs.tw.g.databinding.ActivityPBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject


class PActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPBinding
    private var chooseCallback: ValueCallback<Array<Uri>>? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            chooseCallback?.onReceiveValue(it.toTypedArray())
        }

    private lateinit var policyView: PolicyWebView
    private lateinit var storage: JStorageBool
    private lateinit var liveDeli: MutableLiveData<String>


    //Timer
    private lateinit var liveTimer: MutableLiveData<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPBinding.inflate(layoutInflater)
        storage = JStorageBool(this)
        setContentView(binding.root)

        liveTimer = MutableLiveData(storage.readActiveMinutes())
        liveDeli = MutableLiveData(storage.readADBStatus())

        Log.d("123123", "onCreate method POLICY ACTIVITY")

        liveDeli.observe(this) {
            if (it == JConstants.warning) {
                addButtonAgreeToTheScreen()
            }
        }

        liveTimer.observe(this){
            if (it%2L == 0L){
                storage.saveIsShowDialog(true)
            }
        }

        policyView = PolicyWebView(this, object : DocumentPicker {
            override fun pickDocuments(pickedDocs: ValueCallback<Array<Uri>>?) {
                chooseCallback = pickedDocs
            }
        }, liveDeli)

        setBackClicks(policyView)

        binding.root.addView(policyView)
        policyView.startPolicyUI(getContent)

        val a = intent.getStringExtra(JConstants.aKey) ?: JConstants.emptyData

        if (a != JConstants.emptyData) {
            //First time case
            firstTimeCase(a)
        } else {
            notFirstTime()
        }

        checkTimer()

    }

    private fun checkTimer() {
        val currentSpendTime = storage.readActiveMinutes()

        if (currentSpendTime > 1){
            showRatingDialog()
        }
    }

    private fun showRatingDialog() {
        val revManager = ReviewManagerFactory.create(applicationContext)

        val isDialog = storage.readDataIsShow()
        val checkBox = CheckBox(this)

        if (isDialog) {
            //Show fake Rate us
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.setPadding(48, 48, 48, 48)

            val ratingBar = RatingBar(this)
            ratingBar.numStars = 5

            val ratingBarLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            ratingBarLayoutParams.gravity = Gravity.CENTER_HORIZONTAL

            ratingBar.layoutParams = ratingBarLayoutParams
            ratingBar.stepSize = 1f
            linearLayout.addView(ratingBar)


            //don't show again
            checkBox.text = "I don't want to see it again"
            val checkBoxLayoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            checkBoxLayoutParams.gravity = Gravity.CENTER_HORIZONTAL

            checkBox.layoutParams = checkBoxLayoutParams

            linearLayout.addView(checkBox)


            val builder = AlertDialog.Builder(this)


            val title = TextView(this)
            title.text = "Please, Rate Our App"
            title.textSize = 20f  // Adjust text size to your preference
            title.gravity = Gravity.CENTER_HORIZONTAL  // Center the text horizontally

            builder.setCustomTitle(title)

            builder.setView(linearLayout)

            builder.setPositiveButton("Submit") { dialog, which ->
                val rating = ratingBar.rating

                if (rating > 3f) {
                    storage.saveIsShowDialog(false)

                    //Show original Rate us
                    revManager.requestReviewFlow().addOnCompleteListener { toDo ->
                        if (toDo.isSuccessful) {
                            revManager.launchReviewFlow(this, toDo.result)
                        }
                    }
                } else {
                    //never show dialog
                    storage.saveIsShowDialog(false)
                }
            }

            builder.setNegativeButton("Dismiss") { dialog, which ->

            }

            val dialog = builder.create()

            dialog.show()
            //Submit false

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false

            ratingBar.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { _, starsCount, _ ->
                    if (starsCount > 0) positiveButton.isEnabled = true
                }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    storage.saveIsShowDialog(false)
                } else {
                    storage.saveIsShowDialog(true)
                }
            }
        }

        storage.saveIsShowDialog(false)
    }


    private fun notFirstTime() {
        val storage = JStorageBool(this)
        policyView.loadUrl(storage.readLink())
    }

    private fun firstTimeCase(a: String) {

        val storage = JStorageBool(this)
        val g = intent.getStringExtra(JConstants.gKey) ?: JConstants.emptyData
        val r = intent.getStringExtra(JConstants.rKey) ?: JConstants.emptyData
        val f = intent.getStringExtra(JConstants.fKey) ?: JConstants.emptyData
        val adb = intent.getStringExtra(JConstants.aKey) ?: JConstants.emptyData

        Log.d("123123", "The FB from intent is $f")

        val jsonObject = JSONObject()

        //change before release 1
        if (adb == "1"){
            //url https://jokersplash.online/privacypolicy
            policyView.loadUrl(storage.readLink())
        } else {

            val policyMap: MutableMap<String, String> = mutableMapOf()

            //gadid
            jsonObject.put("mvhcbjmte", g)

            if (f != "null") {
                //facebook
                jsonObject.put("39nrmknm", f)
            } else {
                //referrer
                //cmpgn или fb4a
                if (r.contains("cmpgn") || r.contains("fb4a")) {
                    jsonObject.put("39nrmknm", r)
                }
                //url https://jokersplash.online/privacypolicy
            }

            policyMap.put("41eddh9", jsonObject.toString())
            policyView.loadUrl(storage.readLink(), policyMap)

        }


        //add button
        if (a == "1") {
            addButtonAgreeToTheScreen()
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            for (i in 0..100000){
                delay(60000)
                val currentMinutes = liveTimer.value ?: 0L
                liveTimer.value = currentMinutes + 1L
                storage.saveActiveMinutes(currentMinutes + 1)
            }
        }
    }

//    fun makeCurl(url: String, headers: Map<String, String>) {
//        val curl = "curl --location \"$url\""
//        val headersString = headers.map { "--header \"${it.key}: ${it.value}\"" }.joinToString(" \\\n")
//        val command = "$curl \\\n$headersString"
//
//        Log.d("123123", command)
//        println(command)
//    }

    private fun addButtonAgreeToTheScreen() {
        val buttonAgree = Button(this)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity =
                Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM // Center horizontally and place at the bottom
            bottomMargin = 16  // Optional, in case you want some margin from the bottom
        }

        buttonAgree.layoutParams = layoutParams

        buttonAgree.text = "Agree"
        buttonAgree.setOnClickListener {
            storage.saveLink(JConstants.warning)
            navigateToMenu()
        }
        binding.root.addView(buttonAgree)
    }

    fun navigateToMenu() {
        val intentToTheMenu = Intent(this, MActivity::class.java)
        startActivity(intentToTheMenu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        policyView.saveState(bundle)
        outState.putBundle("scores", bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        policyView.restoreState(savedInstanceState)
    }

    private fun setBackClicks(w: WebView) {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (w.canGoBack()) {
                    w.goBack()
                }
            }
        })
    }
}