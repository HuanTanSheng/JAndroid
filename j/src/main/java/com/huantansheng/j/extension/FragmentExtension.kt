package com.huantansheng.j.extension

import android.content.ComponentName
import android.content.Intent
import android.support.v4.app.Fragment
import com.huantansheng.j.constants.JCode



/**
 * 启动网络设置界面
 */
fun Fragment.startNetUI() {
    val intent = Intent("/")
    val cm = ComponentName("com.android.settings", "com.android.settings.WirelessSettings")
    intent.component = cm
    intent.action = "android.intent.action.VIEW"
    startActivityForResult(intent, JCode.REQUEST_SETTING_NET)
}
