package com.shreyas.unlock_screen_flash_msg

import android.content.Context
import android.util.Log

val SHAREDNAME:String = "MY_APP"
val KEY_TIME:String = "TIME"


fun log(msg: String) {
    Log.d("UNLOCK_PHONE", msg)
}

fun setStoreTime(context:Context, sec: Long) {
    val prefernce = context.getSharedPreferences(SHAREDNAME, 0)
    val editor = prefernce.edit()
    editor.putLong(KEY_TIME, sec)
    editor.apply()
}

fun getStoreTime(context: Context):Long {
    val preferences = context.getSharedPreferences(SHAREDNAME, 0)
    return  preferences.getLong(KEY_TIME, 5000)
}