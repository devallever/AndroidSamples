package com.allever.mysimple.imageloader.lib

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import com.allever.imageloader.lib.ImageCache
import com.allever.mysimple.R
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@SuppressLint("StaticFieldLeak")
object ImageLoader {
    private val TAG = ImageLoader::class.java.simpleName
    private const val TAG_KEY_URI = R.id.imageloader_uri
    private const val MESSAGE_POST_RESULT = 1

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = CPU_COUNT + 1
    private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
    private const val KEEP_ALIVE = 10L

    private lateinit var mContext: Context

    private lateinit var mImageCache: ImageCache

    private val mThreadFactory = object : ThreadFactory {
        val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable?): Thread {
            return Thread(r, "$TAG#${mCount.getAndIncrement()}")
        }

    }

    private val THREAD_POOL_EXECUTOR = ThreadPoolExecutor(
        CORE_POOL_SIZE,
        MAXIMUM_POOL_SIZE,
        KEEP_ALIVE,
        TimeUnit.SECONDS,
        LinkedBlockingDeque<Runnable>(),
        mThreadFactory
    )

    private val mMainHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            val loaderResult = msg?.obj as? LoaderResult
            val imageView = loaderResult?.imageView
            val uri = imageView?.getTag(TAG_KEY_URI)
            if (uri == loaderResult?.uri) {
                imageView?.setImageBitmap(loaderResult.bitmap)
            } else {
                Log.w(TAG, "set image bitmap, but url has changed, ignored!")
            }
        }
    }

    /***
     * 初始化调用一次
     */
    fun init(context: Context) {
        mContext = context.applicationContext
        mImageCache = ImageCache(mContext)
    }

    fun display(uri: String, imageView: ImageView) {
        display(uri, imageView, 0, 0)
    }

    fun display(uri: String, imageView: ImageView, reqWidth: Int, reqHeight: Int) {
        val bitmap = loadBitmap(uri, reqWidth, reqHeight)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        }
    }


    fun displayAsync(uri: String, imageView: ImageView) {
        displayAsync(uri, imageView, 0, 0)
    }

    fun displayAsync(uri: String, imageView: ImageView, reqWidth: Int, reqHeight: Int) {
        imageView.setTag(TAG_KEY_URI, uri)
        var bitmap: Bitmap? = null
        bitmap = mImageCache.getMemoryCache(uri)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
            return
        }

        val runnable = Runnable {
            val bitmap: Bitmap? = loadBitmap(uri, reqWidth, reqHeight)
            if (bitmap != null) {
                val loaderResult = LoaderResult(imageView, uri, bitmap)
                mMainHandler.obtainMessage(MESSAGE_POST_RESULT, loaderResult).sendToTarget()
            }
        }

        THREAD_POOL_EXECUTOR.execute(runnable)

    }

    private fun loadBitmap(uri: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        var bitmap: Bitmap? = null
        bitmap = mImageCache.getMemoryCache(uri)
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromMemoryCache, url: $uri")
            return bitmap
        }

        try {
            bitmap = mImageCache.getDiskCache(uri, reqWidth, reqHeight)
            if (bitmap != null) {
                Log.d(TAG, "loadBitmapFromDiskCache, url: $uri")
                return bitmap
            }

            bitmap = mImageCache.getImageFromNetwork(uri, reqWidth, reqHeight)
            Log.d(TAG, "loadBitmapFromHttp, url: $uri")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (bitmap == null) {
            Log.w(TAG, "encounter error, DiskLruCache is not created.")
            bitmap = downloadImage(uri)
        }

        return bitmap

    }

    private fun downloadImage(urlString: String): Bitmap? {
        var bitmap: Bitmap? = null
        var httpURLConnection: HttpURLConnection? = null
        var bufferedInputStream: BufferedInputStream? = null

        try {
            val url = URL(urlString)
            httpURLConnection = url.openConnection() as? HttpURLConnection
            bufferedInputStream = BufferedInputStream(httpURLConnection?.inputStream, HttpUtils.IO_BUFFER_SIZE)
            bitmap = BitmapFactory.decodeStream(bufferedInputStream)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpURLConnection?.disconnect()
            CloseUtils.close(bufferedInputStream)
        }

        return bitmap
    }


}