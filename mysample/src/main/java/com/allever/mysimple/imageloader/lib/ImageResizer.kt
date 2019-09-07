package com.allever.mysimple.imageloader.lib

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileDescriptor

/***
 * 图片压缩类
 */
class ImageResizer() {


    fun decodeSampleFromResource(resources: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap? {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, option)
        option.inSampleSize = calculateInSampleSize(option, reqWidth, reqHeight)
        option.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, resId, option)
    }

    fun decodeSampleFromFileDescroptor(fileDescriptor: FileDescriptor, reqWidth: Int, reqHeight: Int): Bitmap? {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, option)
        option.inSampleSize = calculateInSampleSize(option, reqWidth, reqHeight)
        option.inJustDecodeBounds = false
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, option)
    }


    /**
     * 计算采样率
     */
    private fun calculateInSampleSize(option: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        if (reqHeight == 0 || reqWidth == 0) {
            return 1
        }

        val width = option.outWidth
        val height = option.outHeight

        var inSampleSize = 1
        if (width > reqWidth || height > reqHeight) {
            val halfWidth = width / 2
            val halfHeight = height / 2
            while (halfWidth / inSampleSize >= reqWidth && halfHeight / inSampleSize >= reqHeight) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}