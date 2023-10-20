package com.wydxzcs.tw.g.domain.models

import com.wydxzcs.tw.g.JConstants

data class GeneralAppState(
    val link: String = JConstants.emptyData,
    val adb: String = JConstants.emptyData,
    val refferer: String = JConstants.emptyData,
    val deeplink: String = JConstants.emptyData,
    val gaid: String = JConstants.emptyData,
)