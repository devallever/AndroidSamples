package com.allever.mysimple.newsDetail;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allever on 17-5-26.
 */

public class NewsDetailActivity extends BaseActivity {

    private static final String TAG = "NewsDetailActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_activity_layout);


        Button btn_get = (Button)findViewById(R.id.id_news_detail_activity_btn_get);
        btn_get.setOnClickListener(v->{
            RetrofitUtil.getInstance().getNewsList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<NewsSummary>>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: " + e.toString());
                        }

                        @Override
                        public void onNext(List<NewsSummary> newsSummaries) {
                            Log.d(TAG, "title = " + newsSummaries.get(0).getTitle());
                        }
                    });
        });
    }
}
