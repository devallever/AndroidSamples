package com.allever.allsample;

import android.app.Application;
import android.content.Context;

import com.allever.allsample.crash.CrashHandler;


/**
 * Created by allever on 17-6-29.
 */

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
        //Vitamio.isInitialized(getApplicationContext());

        CrashHandler.getInstance().init(this);

    }
}
