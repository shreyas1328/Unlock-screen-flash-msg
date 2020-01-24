package com.shreyas.unlock_screen_flash_msg

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
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

fun enableReciver(context: Context) {
    val filter = IntentFilter()
    filter.addAction("android.intent.action.USER_PRESENT")
    context.registerReceiver(BReciver(), filter)
    val receiver = ComponentName(context, BReciver::class.java)

    context.packageManager.setComponentEnabledSetting(
        receiver,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
}


fun disableReciver(context: Context) {
    val receiver = ComponentName(context, BReciver::class.java)

    context.packageManager.setComponentEnabledSetting(
        receiver,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP
    )
}


private val POWERMANAGER_INTENTS = arrayOf(
    Intent().setComponent(
        ComponentName(
            "com.miui.securitycenter",
            "com.miui.permcenter.autostart.AutoStartManagementActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.letv.android.letvsafe",
            "com.letv.android.letvsafe.AutobootManageActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.huawei.systemmanager",
            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.huawei.systemmanager",
            "com.huawei.systemmanager.optimize.process.ProtectActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.huawei.systemmanager",
            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.coloros.safecenter",
            "com.coloros.safecenter.permission.startup.StartupAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.coloros.safecenter",
            "com.coloros.safecenter.startupapp.StartupAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.oppo.safe",
            "com.oppo.safe.permission.startup.StartupAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.iqoo.secure",
            "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.iqoo.secure",
            "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.vivo.permissionmanager",
            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.samsung.android.lool",
            "com.samsung.android.sm.ui.battery.BatteryActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.htc.pitroad",
            "com.htc.pitroad.landingpage.activity.LandingPageActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.asus.mobilemanager",
            "com.asus.mobilemanager.MainActivity"
        )
    )
)


fun autoStart(context: Context) {
    for (intent in POWERMANAGER_INTENTS) if (context.getPackageManager().resolveActivity(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        ) != null
    ) {
        context.startActivity(intent)
        break
    }
}