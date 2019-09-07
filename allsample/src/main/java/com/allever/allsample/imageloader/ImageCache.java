package com.allever.allsample.imageloader;

import android.graphics.Bitmap;

/**
 * Created by allever on 17-10-12.
 */

public interface ImageCache {
    Bitmap get(String url);
    void put(String url, Bitmap bitmap);
}
