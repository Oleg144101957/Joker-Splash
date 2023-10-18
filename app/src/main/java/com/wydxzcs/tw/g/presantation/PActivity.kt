package com.wydxzcs.tw.g.presantation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.data.storage.JStorageBool
import com.wydxzcs.tw.g.databinding.ActivityPBinding


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


        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        policyView.layoutParams = layoutParams

        setBackClicks(policyView)

        binding.root.addView(policyView)
        policyView.startPolicyUI(getContent)

        val storage = JStorageBool(this)

        val g = intent.getStringExtra(JConstants.gKey) ?: JConstants.emptyData
        val a = intent.getStringExtra(JConstants.aKey) ?: JConstants.emptyData
        val r = intent.getStringExtra(JConstants.rKey) ?: JConstants.emptyData
        val f = intent.getStringExtra(JConstants.fKey) ?: JConstants.emptyData



        if (a != JConstants.emptyData){
            val policyMap : MutableMap<String, String> = mutableMapOf()

            //gadid
            policyMap.put("mvhcbjmte", g)

            //referrer
            policyMap.put("39nrmknm", r)

            policyView.loadUrl(storage.readLink(), policyMap)
        }


//        //facebook
//        policyMap.put("39nrmknm", r)




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