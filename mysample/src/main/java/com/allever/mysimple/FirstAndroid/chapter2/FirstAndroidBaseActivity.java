package com.allever.mysimple.FirstAndroid.chapter2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver.LoginActivity;

/**
 * Created by Allever on 2017/3/1.
 */

public class FirstAndroidBaseActivity extends AppCompatActivity {

    private static final String TAG = "FirstAndroidBaseActivit";

    private ForceOfflineReceiver forceOfflineReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver.FORCE_OFFLINE");
        forceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(forceOfflineReceiver, intentFilter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName());
        FirstAndroidCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirstAndroidCollector.removeActivity(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (forceOfflineReceiver!=null){
            unregisterReceiver(forceOfflineReceiver);
        }
    }

    class ForceOfflineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("You are forced to be offline. Please try to login again.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirstAndroidCollector.removeAll();
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    context.startActivity(intent1);
                }
            });
            builder.show();
        }
    }
}
