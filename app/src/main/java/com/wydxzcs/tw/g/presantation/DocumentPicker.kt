package com.wydxzcs.tw.g.presantation

import android.net.Uri
import android.webkit.ValueCallback

interface DocumentPicker {
    fun pickDocuments(pickedDocs : ValueCallback<Array<Uri>>?)

}