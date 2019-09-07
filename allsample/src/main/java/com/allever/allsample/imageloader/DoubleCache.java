package com.allever.allsample.imageloader;

import android.graphics.Bitmap;

import retrofit2.http.PUT;

/**
 * Created by allever on 17-10-12.
 */

public class DoubleCache implements ImageCache {
    private ImageCache mMemoryCache = new MemoryCache();
    private ImageCache mDiskCache = new DiskCache();

    @Override
    public Bitmap get(String url){
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null){
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    @Override
    public void put(String url,Bitmap bitmap){
        mMemoryCache.put(url,bitmap);
        mDiskCache.put(url,bitmap);
    }
}
