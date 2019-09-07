package com.allever.mysimple.singleton;

/**
 * Created by Allever on 2016/11/24.
 * 双重锁定
 */

public class MyOkhttp {
    private static MyOkhttp okhttp = null;
    private MyOkhttp(){

    }

    public static MyOkhttp getInstance(){
        if (okhttp == null){
            synchronized (MyOkhttp.class){
                if (okhttp == null){
                    okhttp = new MyOkhttp();
                }
            }
        }
        return okhttp;
    }
}
