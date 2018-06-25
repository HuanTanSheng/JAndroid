package com.huantansheng.j.extension

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.huantansheng.j.base.BaseApplication

/**
 * 获取Uri的真实路径
 */
fun Uri.getFilePath(): String? {
    var realPath: String? = null
    when (scheme) {
        ContentResolver.SCHEME_CONTENT -> {
            val cursor = BaseApplication.instance.applicationContext.contentResolver.query(this, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)

            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                if (index > -1) {
                    realPath = cursor.getString(index)
                }
            }
            cursor.close()
        }

        else -> realPath = path

    }
    return realPath
}