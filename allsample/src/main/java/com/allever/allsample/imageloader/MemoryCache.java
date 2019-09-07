package com.allever.allsample.imageloader;

import android.graphics.Bitmap;
import androidx.collection.LruCache;

/**
 * Created by allever on 17-10-13.
 */

public class MemoryCache implements ImageCache {
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache(){
        initMemoryCache();
    }

    private void initMemoryCache(){
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory / 4;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                int size = bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                return size;
            }
        };
    }

    @Override
    public void put(String url, Bitmap bitmap){
        mMemoryCache.put(url,bitmap);
    }

    @Override
    public Bitmap get(String url){
        return mMemoryCache.get(url);
    }
}