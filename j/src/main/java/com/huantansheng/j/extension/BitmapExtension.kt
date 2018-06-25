package com.huantansheng.j.extension

import android.graphics.*
import android.media.MediaScannerConnection
import android.text.TextPaint
import android.view.ViewGroup
import com.huantansheng.j.base.BaseApplication
import com.huantansheng.j.rxJava.IoToUI
import io.reactivex.Observable
import java.io.File
import java.io.IOException

/**
 * 安全的回收bitmap
 */
fun Bitmap.safeRecycle() {
    if (!isRecycled) {
        recycle()
    }
    System.gc()
}

/**
 * 根据viewGroup缩放bitmap
 *
 * @param viewGroup bitmap的viewGroup
 * @param needRecycle   是否回收传进来的bitmap
 * @return 缩放后的bitmap, 如果原bitmap宽高小于viewGroup宽高则返回原bitmap
 */
fun Bitmap.scaleBitmapForViewGroup(viewGroup: ViewGroup, needRecycle: Boolean): Bitmap {
    val bW = width
    val bH = height

    val vW = viewGroup.measuredWidth
    val vH = viewGroup.measuredHeight

    val scaleW = vW.toFloat() / bW.toFloat()
    val scaleH = vH.toFloat() / bH.toFloat()

    //如果图片小于viewGroup的宽高则返回null
    if (bW < vW && bH < vH) {
        return this
    }

    var width = vW
    var height = vH

    if (bW >= bH) {
        width = vW
        height = (scaleW * bH).toInt()
    } else {
        width = (scaleH * bW).toInt()
        height = vH
    }
    if (width > vW) {
        val tempScaleW = vW.toFloat() / width.toFloat()
        width = vW
        height = (height * tempScaleW).toInt()
    }
    if (height > vH) {
        val tempScaleH = vH.toFloat() / height.toFloat()
        height = vH
        width = (width * tempScaleH).toInt()
    }
    val scaleBitmap = Bitmap.createScaledBitmap(this, width, height, true)
    if (needRecycle) {
        safeRecycle()
    }
    return scaleBitmap
}

/**
 * 给图片添加水印，水印会根据图片宽高自动缩放处理
 *
 * @param watermark              水印
 * @param offsetX                添加水印的X轴偏移量
 * @param offsetY                添加水印的Y轴偏移量
 * @param srcWatermarkImageWidth 水印对应的原图片宽度,即ui制作水印时候参考的图片画布宽度,应该是已知的图片最大宽度
 * @param addInLeft              true 在左下角添加水印，false 在右下角添加水印
 */
fun Bitmap.addWatermark(watermark: Bitmap, srcWatermarkImageWidth: Int, offsetX: Int, offsetY: Int, addInLeft: Boolean) {
    val imageWidth = width
    val imageHeight = height
    if (0 == imageWidth || 0 == imageHeight) {
        throw RuntimeException("Sky: 加水印的原图宽或高不能为0！")
    }
    val watermarkWidth = watermark.width
    val watermarkHeight = watermark.height
    var scale = imageWidth / srcWatermarkImageWidth.toFloat()
    if (scale > 1)
        scale = 1f
    else if (scale < 0.4) scale = 0.4f
    val scaleWatermarkWidth = (watermarkWidth * scale).toInt()
    val scaleWatermarkHeight = (watermarkHeight * scale).toInt()
    val scaleWatermark = Bitmap.createScaledBitmap(watermark, scaleWatermarkWidth, scaleWatermarkHeight, true)
    val canvas = Canvas(this)
    val paint = Paint()
    paint.isAntiAlias = true
    if (addInLeft) {
        canvas.drawBitmap(scaleWatermark, offsetX.toFloat(), (imageHeight - scaleWatermarkHeight - offsetY).toFloat(), paint)
    } else {
        canvas.drawBitmap(scaleWatermark, (imageWidth - offsetX - scaleWatermarkWidth).toFloat(), (imageHeight - scaleWatermarkHeight - offsetY).toFloat(), paint)
    }
    scaleWatermark.safeRecycle()
}


/**
 * 给图片添加带文字和图片的水印，水印会根据图片宽高自动缩放处理
 *
 * @param watermark              水印图片
 * @param srcWatermarkImageWidth 水印对应的原图片宽度,即ui制作水印时候参考的图片画布宽度,应该是已知的图片最大宽度
 * @param text                   要添加的文字
 * @param offsetX                添加水印的X轴偏移量
 * @param offsetY                添加水印的Y轴偏移量
 * @param addInLeft              true 在左下角添加水印，false 在右下角添加水印
 */

fun Bitmap.addWatermarkWithText(watermark: Bitmap, srcWatermarkImageWidth: Int, text: String, offsetX: Int, offsetY: Int, addInLeft: Boolean) {
    val imageWidth = width.toFloat()
    val imageHeight = height.toFloat()
    if (0f == imageWidth || 0f == imageHeight) {
        throw RuntimeException("Sky: 加水印的原图宽或高不能为0！")
    }
    val watermarkWidth = watermark.width.toFloat()
    val watermarkHeight = watermark.height.toFloat()
    var scale = imageWidth / srcWatermarkImageWidth.toFloat()
    if (scale > 1)
        scale = 1f
    else if (scale < 0.4) scale = 0.4f
    val scaleWatermarkWidth = watermarkWidth * scale
    val scaleWatermarkHeight = watermarkHeight * scale
    val scaleWatermark = Bitmap.createScaledBitmap(watermark, scaleWatermarkWidth.toInt(), scaleWatermarkHeight.toInt(), true)
    val canvas = Canvas(this)
    val textPaint = TextPaint()
    textPaint.isAntiAlias = true
    textPaint.color = Color.WHITE
    val textSize = (scaleWatermark.height * 2).toFloat() / 3.toFloat()
    textPaint.textSize = textSize
    val textRect = Rect()
    textPaint.getTextBounds(text, 0, text.length, textRect)
    if (addInLeft) {
        canvas.drawText(text, scaleWatermarkWidth + offsetX, imageHeight - textRect.height().toFloat() - textRect.top.toFloat() - offsetY.toFloat(), textPaint)
    } else {
        canvas.drawText(text, imageWidth - offsetX.toFloat() - textRect.width().toFloat() - textRect.left.toFloat(), imageHeight - textRect.height().toFloat() - textRect.top.toFloat() - offsetY.toFloat(), textPaint)
    }

    val scaleWatermarkPaint = Paint()
    scaleWatermarkPaint.isAntiAlias = true
    if (addInLeft) {
        canvas.drawBitmap(scaleWatermark, offsetX.toFloat(), imageHeight - textRect.height().toFloat() - offsetY.toFloat() - scaleWatermarkHeight / 6, scaleWatermarkPaint)
    } else {
        canvas.drawBitmap(scaleWatermark, imageWidth - textRect.width().toFloat() - offsetX.toFloat() - scaleWatermarkWidth / 6, imageHeight - textRect.height().toFloat() - offsetY.toFloat() - scaleWatermarkHeight / 6, scaleWatermarkPaint)
    }
    scaleWatermark.safeRecycle()
}


/**
 * 保存Bitmap到指定文件夹
 *
 * @param dirPath     文件夹全路径
 * @param namePrefix  保存文件的前缀名，文件最终名称格式为：前缀名+自动生成的唯一数字字符+.png
 * @param notifyMedia 是否更新到媒体库
 * @return observable,已经处理操作线程
 */
fun Bitmap.saveToDir(dirPath: String, namePrefix: String, notifyMedia: Boolean): Observable<File> {
    val observable: Observable<File> = Observable.create {
        val dirFile = File(dirPath)
        if (!dirFile.isDirectory) {
            if (!dirFile.mkdirs()) {
                it.onError(IOException())
            }
        }

        val writeFile = createTempFile(namePrefix, ".png", dirFile)
        val fops = writeFile.outputStream()
        compress(Bitmap.CompressFormat.PNG, 100, fops)
        fops.flush()
        fops.close()

        if (notifyMedia)
            MediaScannerConnection.scanFile(BaseApplication.instance.applicationContext, arrayOf(writeFile.absolutePath), null, null)

        it.onNext(writeFile)
    }

    observable.compose(IoToUI.composeO())
    return observable
}

/**
 * 给bitmap去色
 * @return 已经去色的bitmap
 */
fun Bitmap.toGrayscale(): Bitmap {
    val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    val canvas = Canvas(result)
    val paint = Paint()
    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(0f)
    val colorMatrixColorFilter = ColorMatrixColorFilter(colorMatrix)
    paint.colorFilter = colorMatrixColorFilter
    canvas.drawBitmap(this, 0f, 0f, paint)
    return result
}



