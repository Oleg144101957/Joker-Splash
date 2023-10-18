package com.wydxzcs.tw.g.presantation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.data.storage.JStorageBool
import com.wydxzcs.tw.g.databinding.ActivityPBinding
import org.json.JSONObject


class PActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPBinding
    private var chooseCallback: ValueCallback<Array<Uri>>? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            chooseCallback?.onReceiveValue(it.toTypedArray())
        }

    private lateinit var policyView: PolicyWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPBinding.inflate(layoutInflater)
        setContentView(binding.root)

        policyView = PolicyWebView(this, object : DocumentPicker {
            override fun pickDocuments(pickedDocs: ValueCallback<Array<Uri>>?) {
                chooseCallback = pickedDocs
            }
        })

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

        val jsonObject = JSONObject()

        if (f != "null") {
            //facebook
            jsonObject.put("39nrmknm", f)
        } else {
            //referrer
            //cmpgn или fb4a
            if (r.contains("cmpgn") || r.contains("fb4a")){
                jsonObject.put("39nrmknm", r)
            }
        }

        //gadid
        jsonObject.put("mvhcbjmte", g)

        val policyMap: MutableMap<String, String> = mutableMapOf()
        policyMap.put("41eddh9", jsonObject.toString())

        policyView.loadUrl(storage.readLink(), policyMap)

        //add button
        if (a == "1") {
            addButtonAgreeToTheScreen()
        }
    }

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
            navigateToMenu()
        }
        binding.root.addView(buttonAgree)
    }

    fun navigateToMenu() {
        val intentToTheMenu = Intent(this, MActivity::class.java)
        startActivity(intentToTheMenu)
    }

    fun setBackClicks(w: WebView) {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (w.canGoBack()) {
                    w.goBack()
                }
            }
        })
    }
}