package com.allever.allsample.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by allever on 17-10-12.
 */

public class DiskCache implements ImageCache {
    private static String cacheDir = "sdcard/cache/";

    @Override
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir + url);
    }

    @Override
    public void put(String url, Bitmap bitmap){
        FileOutputStream fileOutInputStream = null;
        try {
            fileOutInputStream = new FileOutputStream(cacheDir + url);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutInputStream);
        }catch (FileNotFoundException fe){
            fe.printStackTrace();
        }finally {
            CloseUtil.closeQuickly(fileOutInputStream);
        }
    }
}
