package com.allever.mysimple.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Allever on 2016/11/16.
 */

public class DiskCache implements ImageCache{
    //static String cacheDir = "sdcard/cache/";
    static String cacheDir = Environment.getExternalStorageDirectory().getPath() + "/cache/";
    public DiskCache(){

    }
    @Override
    public void put(String url, Bitmap bitmap) {
        //将Bitmap写入文件
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir + Utils.getImageName(url));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Bitmap get(String url) {
        //从本地文件中获取图片
        return BitmapFactory.decodeFile(cacheDir + Utils.getImageName(url));
    }
}
