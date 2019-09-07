package com.allever.mysimple;

import android.os.Bundle;
import androidx.annotation.Nullable;

import okhttp3.OkHttpClient;

/**
 * Created by allever on 17-8-14.
 */

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OkHttpClient client = new OkHttpClient();


    }
}
