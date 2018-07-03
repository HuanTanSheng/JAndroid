package com.huantansheng.jandroid.extension

import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 把View画成Bitmap
 * @return Bitmap
 */
fun View.generateBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)

    return bitmap
}

/**
 * 给view添加listener
 */
fun View.OnClickListener.setClicker(vararg view: View) {
    view.forEach { it.setOnClickListener(this) }
}

fun RecyclerView.init(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    setLayoutManager(layoutManager)
    setAdapter(adapter)
}