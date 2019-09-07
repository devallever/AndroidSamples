package com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by allever on 17-3-15.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"MyBroadcastReceiver:received",Toast.LENGTH_LONG).show();
    }
}
