package com.wydxzcs.tw.g.data.storage

import android.content.Context
import com.wydxzcs.tw.g.JConstants

const val spName = "spName"
const val adbKey = "boolKey"
const val linkKey = "linkKey"
const val isShowDialogKey = "isShowDialog"
const val isShowDialogTimeKey = "isShowDialogTimeKey"
const val isShowDialogShowDontShowAgainKey = "isShowDialogShowDontShowAgainKey"
const val activeMinutesKey = "activeMinutesKey"

class JStorageBool(context: Context) {

    private val sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    fun readADBStatus(): String {
        return sp.getString(adbKey, JConstants.dataIsNotReceived) ?: JConstants.dataIsNotReceived
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


    fun saveIsTimeToShow(isShow: Boolean){
        sp.edit().putBoolean(isShowDialogTimeKey, isShow).apply()
    }

    fun readDataIsTimeToShow() : Boolean{
        return sp.getBoolean(isShowDialogTimeKey, false)
    }

    fun saveIsDialogShowDontShowAgain(isShow: Boolean){
        sp.edit().putBoolean(isShowDialogShowDontShowAgainKey, isShow).apply()
    }

    fun readIsDialogShowDontShowAgain() : Boolean{
        return sp.getBoolean(isShowDialogShowDontShowAgainKey, false)
    }

    fun saveActiveMinutes(minutes: Long){
        sp.edit().putLong(activeMinutesKey, minutes).apply()
    }

    fun readActiveMinutes(): Long{
        return sp.getLong(activeMinutesKey, 0L)
    }

}