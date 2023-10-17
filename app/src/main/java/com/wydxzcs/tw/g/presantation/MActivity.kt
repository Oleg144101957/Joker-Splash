package com.wydxzcs.tw.g.presantation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.databinding.ActivityMBinding
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import javax.inject.Inject
import kotlin.system.exitProcess

class MActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMBinding

    @Inject
    lateinit var generalAppStateRepository: GeneralAppStateRepository


    private var chooseCallback: ValueCallback<Array<Uri>>? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            chooseCallback?.onReceiveValue(it.toTypedArray())
        }

    private lateinit var policyView: PolicyWebView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mode = intent.getStringExtra(JConstants.goToMenuModeKey)
        if (mode != null){
            checkModeStatus(mode)
        }


        setClickListennersForTheButtons()
    }

    private fun checkModeStatus(mode: String) {
        when(mode){

            NavigateMode.FirstTime.mode -> {
                // build link and open WebView here
                buildLinkAndOpenPolicy()
            }

            NavigateMode.NotFirstTimeUser.mode -> {
                // read link and open webview
                readSavedLinkAndOpenPolicy()
            }

            NavigateMode.NotFirstTimeModerator.mode -> {
                // just open menu, no Web View
                //do nothing
            }
        }
    }

    private fun readSavedLinkAndOpenPolicy() {

    }

    private fun buildLinkAndOpenPolicy() {

        policyView = PolicyWebView(this, object : DocumentPicker{
            override fun pickDocuments(pickedDocs: ValueCallback<Array<Uri>>?) {
                chooseCallback = pickedDocs
            }
        })

        var listUrl: List<String>? = listOf("https://google.", "com/")
        binding.root.addView(policyView)
        policyView.startPolicyUI(getContent)

        val baseDest = (listUrl?.get(0) ?: "") + (listUrl?.get(1) ?: "")
        listUrl = null

        policyView.loadUrl(baseDest)

        if (listUrl == null){
            Log.d("123123", "List is null")
        } else {
            Log.d("123123", "List is not null")
        }
    }

    private fun setClickListennersForTheButtons() {

        binding.btnPlay.setOnClickListener {
            playGame()
        }

        binding.btnExit.setOnClickListener {
            exitJokerSplash()
        }
    }

    private fun exitJokerSplash() {
        val intentToExitApp = Intent(Intent.ACTION_MAIN)
        intentToExitApp.addCategory(Intent.CATEGORY_HOME)
        intentToExitApp.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intentToExitApp)
        finish()
        exitProcess(0)
    }

    private fun playGame() {
        val intentToPlayGame = Intent(this, GActivity::class.java)
        startActivity(intentToPlayGame)
    }
}