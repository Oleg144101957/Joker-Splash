package com.wydxzcs.tw.g.data.storage

import android.content.Context

const val spName = "spName"
const val boolKey = "boolKey"

class JStorageBool(context: Context) {

    private val sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    fun readBool(): Boolean {
        return sp.getBoolean(boolKey, false)
    }

    fun saveBoolTrueModer() {
        //Moder
        sp.edit().putBoolean(boolKey, true).apply()
    }
}