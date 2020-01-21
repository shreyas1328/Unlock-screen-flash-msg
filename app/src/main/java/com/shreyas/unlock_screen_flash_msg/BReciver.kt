package com.shreyas.unlock_screen_flash_msg

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button

class BReciver : BroadcastReceiver() {

    var handler:Handler? = null
    var runnable:Runnable? = null

    override fun onReceive(context: Context?, intent: Intent?) {

//        Log.i("result_1234", "onReceive: redirect intent to LockMonitor    "+isChecked);
        Log.d("result_1234", "asdas: "+(intent!!.action == Intent.ACTION_USER_PRESENT) )

        if (intent!!.action == Intent.ACTION_USER_PRESENT ) {
            var count = getPrefs(context!!)
            setPrefs(context!!, count+1)

            if (getPrefs(context!!) <= 1) {
                setUpWindow(context)
            }

//            Log.d("result_12345", "onReceive:count   "+ count)
//            setPrefs(context!!, count+1)

            Log.d("result_12345", "onReceive:getPrefs   "+ getPrefs(context!!))

        }else {
            handler = Handler()
            runnable = Runnable {
                setPrefs(context!!, 0)
            }
            handler!!.postDelayed(runnable, 1000)

        }

    }

    private fun setUpWindow(context: Context?) {
       val wm = context!!.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
       val inflater = context!!.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.flash_msg, null)
        val params:WindowManager.LayoutParams
        setView(view, wm, context)
        if (Build.VERSION.SDK_INT >= 26) {
            params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }else {
            params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }
        params.gravity = Gravity.CENTER or Gravity.CENTER
        params.x = 0
        params.y = 0
        wm.addView(view, params)
    }

    private fun setView(
        view: View?,
        wm: WindowManager,
        context: Context
    ) {
        val mBtnDismiss = view?.findViewById<Button>(R.id.btn_dismiss)

        mBtnDismiss?.setOnClickListener(View.OnClickListener {
//            var count = getPrefs(context!!)
//            Log.d("result_12345", "onReceive:"+ getPrefs(context!!)+"    "+count)
//            while (count >= 0 ) {
                wm.removeView(view)
//                count--
//            }
            setPrefs(context, 0)
        })
    }

}