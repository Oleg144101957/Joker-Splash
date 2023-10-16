package com.wydxzcs.tw.g.domain.repository

interface GeneralAppStateRepository {
    fun saveLink(link: String)
    fun saveAdb(adb: String)
    fun saveRefferer(refferer: String)
    fun saveDeeplink(deeplink: String)
    fun saveIsModer(isModer: Boolean)
}