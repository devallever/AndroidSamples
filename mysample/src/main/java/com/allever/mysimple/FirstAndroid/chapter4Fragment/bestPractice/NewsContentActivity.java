package com.allever.mysimple.FirstAndroid.chapter4Fragment.bestPractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IntegerRes;
import androidx.annotation.Nullable;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-3-13.
 */

public class NewsContentActivity extends FirstAndroidBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_activity_layout);
        String title = getIntent().getStringExtra("news_title");
        String content = getIntent().getStringExtra("news_content");
        NewsContentFragment newsContentFragment = (NewsContentFragment)getSupportFragmentManager().findFragmentById(R.id.id_news_content_activity_news_content_fragment);
        newsContentFragment.refresh(title,content);
    }

    public static  void actionStart(Context context, String title, String content){
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title",title);
        intent.putExtra("news_content",content);
        context.startActivity(intent);
    }
}
