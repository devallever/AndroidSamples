package com.allever.allsample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by allever on 17-6-29.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getName());
    }

    protected void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
