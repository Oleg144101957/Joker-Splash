package com.wydxzcs.tw.g.presantation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import com.wydxzcs.tw.g.JConstants
import com.wydxzcs.tw.g.data.repository.GeneralAppStateRepositoryImpl
import com.wydxzcs.tw.g.data.storage.JStorageBool
import com.wydxzcs.tw.g.domain.repository.GeneralAppStateRepository
import javax.inject.Inject

@SuppressLint("ViewConstructor")
class PolicyWebView(
    context: Context,
    val documentPicker: DocumentPicker,
    private val liveDeli: MutableLiveData<String>
) : WebView(context) {

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

                Log.d("123123", "The URL is $url")

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

        var end: List<String>? = listOf("privacyp", "olicy/", "https://joke", "rsplash.online/", "kj")
        val peru = end?.get(2) + end?.get(3)

        if (url == peru){
            //save WARNING and Navigate to Menu
            jStorageBool.saveLink(JConstants.warning)
            liveDeli.value = JConstants.warning
        } else {
            //just save link
            val currentDest = jStorageBool.readLink()

            if (currentDest.startsWith(end!![2])){
                //save
                jStorageBool.saveLink(url)
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
    }

    fun String.trashRemover() : String{
        return this.replace("; wv", "")
    }
}