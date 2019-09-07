package com.allever.allsample.crash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-11-2.
 */

public class CrashTestActivity extends BaseActivity {
    private static final String TAG = "CrashTestActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_test_activity);



        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }else{
            Log.d(TAG, "onCreate: ");
            throw new RuntimeException("抛出自定义Crash");

        }


    }
}
