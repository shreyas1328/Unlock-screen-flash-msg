package com.shreyas.unlock_screen_flash_msg

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServiceTest : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("qwerty1", "sasdasd:sdc")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("qwerty1", "asdas: ${(intent!!.action == Intent.ACTION_USER_PRESENT)}")
        return START_STICKY
    }

}