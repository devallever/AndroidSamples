package com.allever.mysimple.mvpTest.retrofit;

import com.allever.mysimple.mvpTest.bean.User;

import java.nio.channels.SelectableChannel;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Allever on 2017/1/19.
 */

public class RetrofitUtil {

    public static final String BASE_URL = "http://27.54.249.252:8080/SocialServer/";
    private static final int TIME_OUT = 5000;
    private RetrofitService retrofitService;
    private Retrofit retrofit;

    private RetrofitUtil(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public void getSignature(Subscriber<User> subscriber, String username){
        retrofitService.getSignature(username)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static  RetrofitUtil getInstance(){
        return RetrofitHolder.INSTANCE;
    }

    private static class RetrofitHolder{
        private static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }
}
