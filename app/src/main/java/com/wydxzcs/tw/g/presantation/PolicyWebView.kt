package com.wydxzcs.tw.g.presantation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.data.storage.JStorageBool

@SuppressLint("ViewConstructor")
class PolicyWebView(private val context: Context, val documentPicker: DocumentPicker) : WebView(context) {

    private val jStorageBool = JStorageBool(context)

    fun startPolicyUI(activityLauncher: ActivityResultLauncher<String>){
        setPolicySettings(settings = settings)
        webChromeClient = getWCC(activityLauncher)
        webViewClient = getWVC()
    }

    private fun getWVC() : WebViewClient {
        return object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()

                if (url != null){
                    checkLoadedUrl(url)
                }
            }
        }
    }


    private fun getWCC(launcher: ActivityResultLauncher<String>) : WebChromeClient {
        return object : WebChromeClient(){
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                val content = JConstants.docsContentList[0]+JConstants.docsContentList[1]
                documentPicker.pickDocuments(filePathCallback)
                launcher.launch(content)
                return true
            }
        }
    }

    private fun checkLoadedUrl(url: String) {
        //check link
        //save link logic

        var end: List<String>? = listOf("privacyp", "olicy/")
        val endUrl = (end?.get(0) ?: "") + (end?.get(1) ?: "")

        val peru = WolfConstants.constList1[2]+WolfConstants.constList1[3]+WolfConstants.constList2[2]+WolfConstants.constList2[3]+endUrl

        if (url == peru){
            //save WARNING and Navigate to Menu
            jStorageBool.saveBoolTrueModer()
            //navigate to menu
            GreatWolfApp.buttsonsAssccept.postValue(WolfConstants.constList1[4])
        } else {
            //just save link
            val currentDest = vm.readDestinationFromTheStorage()

            if (currentDest.startsWith("https://greatwol") || currentDest == WolfConstants.constList1[1]){
                //save
                vm.saveDestinationInToTheStorage(url)
            }
        }

        end = null
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setPolicySettings(settings: WebSettings){
        with(settings){
            userAgentString = userAgentString.trashRemover()
            domStorageEnabled = true
            loadWithOverviewMode = false
            javaScriptEnabled = true
        }
        setBackgroundColor(Color.BLACK)
    }

    fun String.trashRemover() : String{
        return this.replace("; wv", "")
    }
}