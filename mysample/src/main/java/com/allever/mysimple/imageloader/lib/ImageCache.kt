package com.allever.imageloader.lib

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.util.LruCache
import com.allever.imageloader.R
import com.allever.mysimple.imageloader.lib.ImageResizer
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream
import java.lang.RuntimeException

class ImageCache(context: Context) {

    companion object {
        private val TAG = ImageCache::class.java.simpleName
        private val MAX_MEMORY = Runtime.getRuntime().maxMemory() / 1024
        private val MEMORY_CACHE_SIZE = MAX_MEMORY / 8
        private const val DISK_CACHE_SIZE = 1024 * 1024 * 50
        private const val DISK_CACHE_INDEX = 0
        private const val TAG_KEY_URI = R.id.imageloader_uri
    }

    private lateinit var mMemoryCache: LruCache<String, Bitmap>

    private lateinit var mContext: Context
    //    private lateinit var mMemoryCache: LruCache<String, Bitmap>
    private lateinit var mDiskCache: DiskLruCache
    private var mIsDiskLruCacheCreated = false
    private val mImageResizer = ImageResizer()

    init {

        mContext = context.applicationContext

        mMemoryCache = object : LruCache<String, Bitmap>(MEMORY_CACHE_SIZE.toInt()) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.rowBytes * bitmap.height / 1024
            }
        }

        val diskCacheDir = getDiskCacheDir(mContext, "bitmap")
        mDiskCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE.toLong())
        mIsDiskLruCacheCreated = true

    }

    fun getMemoryCache(url: String): Bitmap? {
        val hexUrl = HttpUtils.hashKeyFromUrl(url)
        return mMemoryCache.get(hexUrl)
    }

    fun putMemoryCache(url: String, bitmap: Bitmap) {
        if (getMemoryCache(url) == null) {
            val hexUrl = HttpUtils.hashKeyFromUrl(url)
            mMemoryCache.put(hexUrl, bitmap)
        }
    }


    fun getDiskCache(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w(TAG, "load bitmap from UI Thread, it's not recommended!!!")
        }

        var bitmap: Bitmap? = null
        val key = HttpUtils.hashKeyFromUrl(url)
        val snapShot = mDiskCache.get(key)
        if (snapShot != null) {
            val fileInputStream = snapShot.getInputStream(DISK_CACHE_INDEX) as FileInputStream
            val fileDescriptor = fileInputStream.fd
            bitmap = mImageResizer.decodeSampleFromFileDescroptor(fileDescriptor, reqWidth, reqHeight)
            if (bitmap != null) {
                putMemoryCache(url, bitmap)
            }
        }

        return bitmap
    }

    fun getImageFromNetwork(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw RuntimeException("can not visit network from UI Thread.")
        }

        val key = HttpUtils.hashKeyFromUrl(url)
        val editor = mDiskCache.edit(key)
        if (editor != null) {
            val outputStream = editor.newOutputStream(DISK_CACHE_INDEX)
            if (downloadUrlToStream(url, outputStream)) {
                editor.commit()
            } else {
                editor.abort()
            }
            mDiskCache.flush()
        }

        return getDiskCache(url, reqWidth, reqHeight)
    }

    private fun downloadUrlToStream(urlString: String, outputStream: OutputStream): Boolean {
        return HttpUtils.downloadUrlToStream(urlString, outputStream)
    }



    /**
     * 获取磁盘缓存路径
     */
    private fun getDiskCacheDir(context: Context, uniqueName: String): File {
        val externalStorageAvailable = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        val cachePath = if (externalStorageAvailable) {
            context.externalCacheDir?.path ?: context.cacheDir.path
        } else {
            context.cacheDir.path
        }
        Log.d(TAG, "cache path = $cachePath")
        return File("$cachePath${File.separator}$uniqueName")
    }

    private fun getUsableSpace(path: File): Long {
        return path.usableSpace
    }

}