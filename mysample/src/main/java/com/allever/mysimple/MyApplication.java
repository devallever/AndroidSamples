package com.allever.mysimple;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by Allever on 2016/12/7.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        //context = getApplicationContext();
        context = getApplicationContext();
        LitePal.initialize(this);
        //context = this;
    }

    public static Context getContext(){
        return context;
    }
}
