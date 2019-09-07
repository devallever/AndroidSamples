package com.allever.mysimple.imageloader;

import android.graphics.Bitmap;

/**
 * Created by Allever on 2016/11/16.
 */

public interface ImageCache {
    void put(String url, Bitmap bitmap);
    Bitmap get(String url);
}
