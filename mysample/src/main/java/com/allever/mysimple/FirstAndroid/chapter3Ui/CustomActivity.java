package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/3/5.
 */

public class CustomActivity extends FirstAndroidBaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_android_custome_activity_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) actionBar.hide();
    }
}
