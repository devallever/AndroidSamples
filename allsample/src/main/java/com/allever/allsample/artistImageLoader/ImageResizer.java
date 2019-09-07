package com.allever.allsample.artistImageLoader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.load.engine.Resource;

import java.io.FileDescriptor;

/**
 * Created by allever on 17-11-29.
 * @author Allever
 * 图片压缩
 */

public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public ImageResizer(){}

    public Bitmap decodeSampleBitmapFromResource(Resources resources, int resId, int requestWidth, int requestHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,resId);
        options.inSampleSize = calculateSampleSize(options,requestWidth,requestHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources,resId);
    }

    public Bitmap decodeSampleBitmpFromFileDescriptor(FileDescriptor fileDescriptor, int requestWidth, int requestHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
        options.inSampleSize = calculateSampleSize(options, requestWidth,requestHeight);
        options.inJustDecodeBounds = true;
        return  BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    private int calculateSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight){
        if (requestWidth == 0 || requestHeight == 0){
            return 1;
        }

        final int width = options.outWidth;
        final int height = options.outHeight;
        Log.d(TAG, "calculateSampleSize: outWidth = " + width + "\noutHeight = " + height);
        int inSampleSize = 1;
        Log.d(TAG, "calculateSampleSize: inSampleSize = " + inSampleSize);
        if (width > requestWidth || height > requestHeight){
            final int halfWidth = width/2;
            final int halfHeight = height/2;

            while (halfWidth/inSampleSize > requestWidth && halfHeight/inSampleSize > requestHeight){
//                inSampleSize = inSampleSize * 2;
                inSampleSize *= 2;
                Log.d(TAG, "calculateSampleSize: inSampleSize = " + inSampleSize);
            }
        }
        return inSampleSize;
    }


}
