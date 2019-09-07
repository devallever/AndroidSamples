package com.allever.android.sample.jfit;

import android.graphics.Bitmap;
import android.util.LruCache;

import androidx.annotation.DrawableRes;

/**
 * Created by CHARWIN.
 */

public class BitmapCacheUtil {
	private static final LruCache<String, Bitmap> mMemoryCache = new LruCache<>(1024 * 1);
	
	public static Bitmap getBitmap(String id) {
		return mMemoryCache.get(id);
	}
	
	public static void addToCache(@DrawableRes int id, Bitmap bitmap) {
		mMemoryCache.put(id + "", bitmap);
	}
}
