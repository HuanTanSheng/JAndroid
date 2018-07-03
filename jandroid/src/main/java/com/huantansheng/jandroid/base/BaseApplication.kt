package com.huantansheng.jandroid.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.support.annotation.StringRes
import android.widget.Toast
import com.huantansheng.jandroid.retrofit.ApiClient
import org.jetbrains.anko.defaultSharedPreferences


/**
 * 基础Application
 * Created by huan on 2018/6/25.
 */
@SuppressLint("Registered")
abstract class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
            private set
        lateinit var toast: Toast
            private set
    }


    private val actManager = ArrayList<Activity?>()

    private val mySharedPreferences by lazy { defaultSharedPreferences }

    @SuppressLint("ShowToast")
    override fun onCreate() {
        super.onCreate()
        ApiClient.initRetrofit(getApiBaseUrl())
        instance = this
        toast = Toast.makeText(applicationContext, null, Toast.LENGTH_SHORT)
    }

    //获取BaseUrl
    abstract fun getApiBaseUrl(): String

    //获取ConfigDao
    fun getConfigDao(): SharedPreferences {
        return mySharedPreferences
    }

    //清空配置信息
    fun clearConfigDao() {
        mySharedPreferences.edit().clear().apply()
    }

    //act压栈
    fun actIn(act: Activity) {
        actManager.add(act)
    }

    //act退栈
    fun actOut(act: Activity) {
        actManager.remove(act)
    }

    //退出
    fun exit() {
        actManager.forEach {
            it?.finish()
        }
    }

    //吐司
    fun toast(text: String) {
        toast.setText(text)
        toast.show()
    }

    //吐司
    fun toast(@StringRes textId: Int) {
        toast.setText(textId)
        toast.show()
    }
}