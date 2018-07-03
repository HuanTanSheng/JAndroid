package com.huantansheng.jandroid.base

import android.annotation.SuppressLint
import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.instance.actIn(this)
    }


    fun exit() {
        BaseApplication.instance.exit()
    }

    override fun onDestroy() {
        BaseApplication.instance.actOut(this)
        super.onDestroy()
    }

}