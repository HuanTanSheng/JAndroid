package com.huantansheng.j.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.support.annotation.StringRes
import android.view.View
import com.huantansheng.j.base.BaseApplication

/**
 * 获取应用版本号
 */
fun Context.getAppVersion(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName
}


/**
 * 是否有网络链接
 */
fun Context.hasNet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivityManager.activeNetworkInfo
    return info.isAvailable && info.state == NetworkInfo.State.CONNECTED
}

/**
 * 是否是wifi网络
 */
fun Context.isWifi(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
}

/**
 * 使view GONE
 */
fun Context.makeViewGone(vararg view: View) {
    view.forEach {
        it.visibility = View.GONE
    }
}

/**
 * 使view INVISIBLE
 */
fun Context.makeViewInvisible(vararg view: View) {
    view.forEach {
        it.visibility = View.INVISIBLE
    }
}

/**
 * 使view VISIBLE
 */
fun Context.makeViewVisible(vararg view: View) {
    view.forEach {
        it.visibility = View.VISIBLE
    }
}

/**
 * 设置相反的可见性
 */
fun Context.oppositeVisibility(unVisibleType: Int = View.INVISIBLE, isVisible: Boolean = true, contraryFromIndex: Int, vararg view: View) {
    for (i in view.indices) {
        if (isVisible) {
            if (i < contraryFromIndex) {
                view[i].visibility = View.VISIBLE
            } else {
                view[i].visibility = unVisibleType
            }
        } else {
            if (i < contraryFromIndex) {
                view[i].visibility = unVisibleType
            } else {
                view[i].visibility = View.VISIBLE
            }
        }
    }
}


/**
 * 分享文字
 */
fun Context.shareText(text: String, subject: String = "", chooserTitle: String = ""): Boolean {
    return try {
        val intent = Intent(android.content.Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, chooserTitle))
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * 分享图片
 */
fun Context.shareImage(imageUri: Uri, chooserTitle: String = ""): Boolean {
    return try {
        val intent = Intent(android.content.Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(android.content.Intent.EXTRA_STREAM, imageUri)
        startActivity(Intent.createChooser(intent, chooserTitle))
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * 分享多张图片
 */
fun Context.shareImages(imageUris: ArrayList<Uri>, chooserTitle: String = ""): Boolean {
    return try {
        val intent = Intent(android.content.Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/*"
        intent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, imageUris)
        startActivity(Intent.createChooser(intent, chooserTitle))
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

fun Context.toast(@StringRes textId: Int) {
    BaseApplication.instance.toast(textId)
}

fun Context.toast(text: String) {
    BaseApplication.instance.toast(text)
}




