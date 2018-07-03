package com.huantansheng.jandroid.extension

import android.content.ComponentName
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.huantansheng.jandroid.constants.JCode

/**
 * 初始化toolbar
 */
fun AppCompatActivity.initToolbar(toolbar: Toolbar, title: String?, showBackArrow: Boolean) {
    toolbar.title = title
    this.setSupportActionBar(toolbar)
    this.supportActionBar!!.setDisplayHomeAsUpEnabled(showBackArrow)
}

/**
 * 启动网络设置界面
 */
fun AppCompatActivity.startNetUI() {
    val intent = Intent("/")
    val cm = ComponentName("com.android.settings", "com.android.settings.WirelessSettings")
    intent.component = cm
    intent.action = "android.intent.action.VIEW"
    startActivityForResult(intent, JCode.REQUEST_SETTING_NET)
}
