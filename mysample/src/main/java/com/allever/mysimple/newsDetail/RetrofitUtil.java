package com.allever.mysimple.newsDetail;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by allever on 17-5-26.
 */

public class RetrofitUtil {
    public static String BASE_URL = "http://c.m.163.com/nc/article/headline/";
    private static final int TIME_OUT = 5000;
    private Retrofit retrofit;
    private NewsService newsService;

    private RetrofitUtil(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        newsService = retrofit.create(NewsService.class);
    }

    public static RetrofitUtil getInstance(){
        return RetrofitHolder.INSTANCE;
    }

    private static class RetrofitHolder{
        private static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }

    public Observable< List<NewsSummary>> getNewsList(){
        return newsService.getNewsList();
    }
}
