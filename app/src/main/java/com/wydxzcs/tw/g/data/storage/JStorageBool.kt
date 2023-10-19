package com.wydxzcs.tw.g.data.storage

import android.content.Context
import com.wydxzcs.tw.g.JConstants

const val spName = "spName"
const val boolKey = "boolKey"
const val linkKey = "linkKey"
const val isShowDialogKey = "isShowDialog"
const val activeMinutesKey = "activeMinutesKey"

class JStorageBool(context: Context) {

    private val sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    fun readBool(): Boolean {
        return sp.getBoolean(boolKey, false)
    }

    fun saveBoolTrueModer() {
        //Moder
        sp.edit().putBoolean(boolKey, true).apply()
    }

    fun saveLink(link: String){
        sp.edit().putString(linkKey, link).apply()
    }

    fun readLink() : String{
        return sp.getString(linkKey, JConstants.dataIsNotReceived) ?: JConstants.dataIsNotReceived
    }

    fun saveIsShowDialog(isShow: Boolean){
        sp.edit().putBoolean(isShowDialogKey, isShow).apply()
    }

    fun readDataIsShow() : Boolean{
        return sp.getBoolean(isShowDialogKey, true)
    }

    fun saveActiveMinutes(minutes: Long){
        sp.edit().putLong(activeMinutesKey, minutes).apply()
    }

    fun readActiveMinutes(): Long{
        return sp.getLong(activeMinutesKey, 0L)
    }

}