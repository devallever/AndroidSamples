package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/3/5.
 */

public class LayoutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_android_layout_activity_layout);
    }
}
