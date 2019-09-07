package com.allever.allsample.thread.intentService;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by allever on 17-7-2.
 */

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //run on sub thread
        Log.d(TAG, "Thread id = " + Thread.currentThread().getId());
    }
}
