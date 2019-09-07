package com.allever.mysimple.FirstAndroid.chapter4Fragment.bestPractice;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-3-13.
 */

public class NewsActivity extends FirstAndroidBaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_layout);
    }
}
