package com.shreyas.unlock_screen_flash_msg

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.shreyas.calllimitkotline.Actions
import com.shreyas.calllimitkotline.ServiceState
import com.shreyas.calllimitkotline.getServiceState
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var br:BReciver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPrefs(this, 0)


        btn_overlay.setOnClickListener {
            windowOverLayPermission()
        }
    }

    override fun onStart() {
        super.onStart()

        checkPermisiions()
    }

    override fun onResume() {
        super.onResume()

        checkCanDrawOverlays()

    }

    private fun checkCanDrawOverlays() {
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                (!Settings.canDrawOverlays(this))
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ) {
            btn_overlay.isClickable = true
            textView.text = getString(R.string.text)

        }else {
            btn_overlay.isClickable = false
            textView.text = getString(R.string.text_2)
        }
    }

    private fun actionOnService(actions: Actions) {



        if (getServiceState(this) == ServiceState.STOPPED && actions == Actions.STOP) return
        Intent(this, EndlessService::class.java).also {intent ->
            if (Build.VERSION.SDK_INT >= O) {
                startForegroundService(intent)
                return
            }
            startService(intent)
        }
    }

    private fun checkPermisiions() {
        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.SYSTEM_ALERT_WINDOW
            ),
            1
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(
            "qwerty",
            "onRequestPermissionsResult: " + grantResults[1] + "  " + PackageManager.PERMISSION_GRANTED
        )
        when (requestCode) {
            1 -> {

                if (grantResults.size > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    actionOnService(Actions.START)
                    if (Build.VERSION.SDK_INT >= 23) {

                    }
                } else {
                    checkPermisiions()
                }
            }

        }
    }

    private fun windowOverLayPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("ssd4", "sdasd:  "+(!Settings.canDrawOverlays(this)) )
            if (!Settings.canDrawOverlays(this)) {

                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, 1234)
            }else {

            }
        }else {
            Toast.makeText(this, "This permission does not require on your device", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }


}
