package com.allever.mysimple.artistImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import androidx.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by allever on 17-11-29.
 */

public class ImageLoader {
    private static final String TAG = "ImageLoader";

    private static final long DISK_CACHE_SIZE = 50 * 1024 * 1024;//50MB
    private static final int DISK_CACHE_INDEX = 0;
    private static final int IO_BUFFER_SIZE = 1024 * 8;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;
    private static final int MESSAGE_POST_RESULT = 1;
    //private static final int TAG_KEY_URL = R.id.id_bitmap_iv;


    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskCache;

    private boolean mIsDiskLruCacheCreated = false;
    private ImageResizer mImageResizer = new ImageResizer();

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable,"ImageLoader# " + mCount.getAndIncrement());
        }
    };

    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
            sThreadFactory
    );

    private Handler mMainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            LoaderResult loaderResult = (LoaderResult)msg.obj;
            ImageView imageView = loaderResult.imageView;
            String url = loaderResult.url;
//            if (url.equals(imageView.getTag(TAG_KEY_URL))){
//                imageView.setImageBitmap(loaderResult.bitmap);
//            }else {
//                Log.w(TAG, "handleMessage: set Image bitmap, but url has changed, ignore");
//            }
            imageView.setImageBitmap(loaderResult.bitmap);
        }
    };

    private ImageLoader(Context context){
        mContext = context.getApplicationContext();
        int maxMemory = (int)Runtime.getRuntime().maxMemory() / 1024;
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                int size = bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                Log.d(TAG, "sizeOf: bitmapSize = " + size + "KB");
                return size;
            }
        };

        File diskCacheDir = getDiskCacheDir(mContext,"bitmap");
        Log.d(TAG, "ImageLoader: diskCacheDir = " + diskCacheDir.getPath());
        if (!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE){
            try {
                mDiskCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public static ImageLoader build(Context context){
        return new ImageLoader(context);
    }

    /**
     * 同步加载*/
    public Bitmap loadBitmap(String url, int requestWidth, int requestHeight){
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if (bitmap != null){
            Log.d(TAG, "loadBitmap: formMemoryCache url = " + url);
            return bitmap;
        }
        try {
            bitmap = loadBitmapFromDiskCache(url,requestWidth,requestHeight);
            if (bitmap != null){
                Log.d(TAG, "loadBitmap: fromDiskCache");
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(url,requestWidth,requestHeight);
            Log.d(TAG, "loadBitmap: fromHttp");
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        if (bitmap == null && !mIsDiskLruCacheCreated){
            Log.w(TAG, "loadBitmap: encounter error, DiskLruCache is not created" );
            bitmap = downloadBitmapFromUrl(url);
        }
        return bitmap;
    }

    public void bindBitmap(final String url, final ImageView imageView, final int requestWidth, final int requestHeight){
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1 = loadBitmap(url,requestWidth,requestHeight);
                if (bitmap1 != null){
                    LoaderResult loaderResult = new LoaderResult(imageView,url,bitmap1);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT,loaderResult).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(runnable);
    }


    private Bitmap downloadBitmapFromUrl(String urlString){
        Bitmap bitmap = null;
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;

        try {
            final URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
        }catch (IOException ioe){
            Log.e(TAG, "downloadBitmapFromUrl: Error in downloadBitmap" );
            ioe.printStackTrace();
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            CloseUtil.closeQuickly(bufferedInputStream);
        }
        return bitmap;
    }


    /**
     * 添加到内存缓存*/
    private void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if (getBitmapFromMemoryCache(key) == null){
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap loadBitmapFromMemoryCache(String url){
        final String key = hashKeyFromUrl(url);
        Bitmap bitmap = getBitmapFromMemoryCache(key);
        return bitmap;
    }

    /**
     * 从内存中获取Bitmap*/
    private Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }

    /**
     * 从磁盘中获取Bitmap*/
    private Bitmap loadBitmapFromDiskCache(String url, int requestWidth, int requestHeight)throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()){
            Log.w(TAG, "loadBitmapFromDiskCache: load bitmap from UI Thread, it's not recommended !!" );
        }
        if (mDiskCache == null){
            return null;
        }
        Bitmap bitmap = null;
        String key = hashKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskCache.get(key);
        if (snapshot != null){
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampleBitmpFromFileDescriptor(fileDescriptor,requestWidth,requestHeight);
            if (bitmap != null){
                mMemoryCache.put(key,bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 从网络加载Bitmap*/
    private Bitmap loadBitmapFromHttp(String url, int requestWidth, int requestHeight)throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("can not visit network form main thread");
        }
        if (mDiskCache == null){
            return null;
        }

        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskCache.edit(key);
        if (editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(url, outputStream)){
                editor.commit();
            }else {
                editor.abort();
            }
            mDiskCache.flush();
        }
        return loadBitmapFromDiskCache(url,requestWidth, requestHeight);
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream){
        HttpURLConnection httpURLConnection = null;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            final URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            bufferedOutputStream = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);
            int b;
            while ((b = bufferedInputStream.read()) != -1){
                bufferedOutputStream.write(b);
            }
            return true;
        }catch (IOException ioe){
            Log.e(TAG, "downloadBitmap Fail: "+ ioe);
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            CloseUtil.closeQuickly(bufferedInputStream);
            CloseUtil.closeQuickly(bufferedOutputStream);
        }
        return false;
    }

    /**
     * 获取可用空间*/
    private long getUsableSpace(File dir){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return dir.getUsableSpace();
        }
        final StatFs statFs = new StatFs(dir.getPath());
        return (long)statFs.getBlockSize() * (long)statFs.getAvailableBlocks();
    }


    /**
     * 获取缓存路径*/
    private File getDiskCacheDir(Context context, String uniqueName){
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private String hashKeyFromUrl(String url){
        String cacheKey;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        }catch (NoSuchAlgorithmException e){
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<bytes.length; i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    private static class LoaderResult{
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String url, Bitmap bitmap){
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }

    }



}
