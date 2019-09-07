package com.allever.mysimple.imageloader;

import android.graphics.Bitmap;
import androidx.collection.LruCache;

/**
 * Created by Allever on 2016/11/16.
 */

public class MemoryCache implements ImageCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache(){
        //初始化LRU缓存
        initImageCache();
    }

    private void initImageCache(){
        //计算可使用最大缓存
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一最为图片内存缓存
        final int cacheSize = maxMemory / 4;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String url, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };

    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url,bitmap);
    }

    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }
}
