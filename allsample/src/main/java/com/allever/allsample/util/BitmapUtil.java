package com.allever.allsample.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.allever.allsample.bitmapoom.BitmapTestActivity;

/**
 * Created by allever on 17-11-7.
 */

public class BitmapUtil {

    private static final String TAG = "BitmapUtil";

    public static Bitmap decodeSampleBitmapFromRes(Resources resources, int resId, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = true;
        return BitmapFactory.decodeResource(resources,resId,options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG, "calculateInSampleSize: width = " + width);
        Log.d(TAG, "calculateInSampleSize: height = " + height);
        int inSampleSize = 1;
        if (height > reqHeight || width > reqHeight){
            final int halfHeight = height/2;
            final int halfWidth = width/2;
            while ((halfHeight/inSampleSize) >= reqHeight && (halfWidth/inSampleSize) >= reqWidth){
                inSampleSize *= 2;
                Log.d(TAG, "calculateInSampleSize: inSample = " + inSampleSize);
                Log.d(TAG, "calculateInSampleSize: halfWidth = " + halfHeight);
                Log.d(TAG, "calculateInSampleSize: halfHeight = " + halfHeight);
            }
        }
        return inSampleSize;
    }
}
