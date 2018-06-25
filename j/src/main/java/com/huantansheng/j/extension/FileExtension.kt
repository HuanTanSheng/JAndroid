package com.huantansheng.j.extension

import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import com.huantansheng.j.base.BaseApplication
import java.io.File

/**
 * 获取文件Uri
 */
fun File.getUri(fileProviderText: String): Uri {
    return if (Build.VERSION.SDK_INT >= 24) FileProvider.getUriForFile(BaseApplication.instance.applicationContext, fileProviderText, this) else Uri.fromFile(this)
}

