package com.shreyas.unlock_screen_flash_msg

import android.content.Context


fun setPrefs(context: Context, isClicked:Int) {
    val prefs = context.getSharedPreferences("MYAPP", 0)
    val edit = prefs.edit()
    edit.let {
        it.putInt("KEYCLICKED", isClicked)
        it.apply()
    }
}

fun getPrefs(context: Context):Int {
    val prefs = context.getSharedPreferences("MYAPP", 0)
    return prefs.getInt("KEYCLICKED", 0)
}