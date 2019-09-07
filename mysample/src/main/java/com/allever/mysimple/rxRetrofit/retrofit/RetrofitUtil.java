package com.allever.mysimple.rxRetrofit.retrofit;

import android.util.Log;

import com.allever.mysimple.nearbyProject.pojo.NearbyNewsRoot;
import com.allever.mysimple.rxRetrofit.bean.User;

import java.net.NoRouteToHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Allever on 2017/1/15.
 */

public class RetrofitUtil {
    private static final String TAG = "RetrofitUtil";
    //private static final String BASE_URL = "http://27.54.249.252:8080/SocialServer/";
    private static final String BASE_URL = "http://192.168.23.1:8080/SocialServer/";
    private Retrofit retrofit;
    private RetrofitService retrofitService;

    private RetrofitUtil(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitUtil getInstance(){
        return RetrofitHolder.INSTANCE;
    }

    public void getSignature(Subscriber<User> subscriber,String username){
        retrofitService.getSignature(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    public void getNearbyNews(Subscriber<NearbyNewsRoot> subscriber, String longitude, String latitude, String page){
        Log.d(TAG, "Retrofit->getNearbyNews: ");
        retrofitService.getNearbyNews(longitude,latitude,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    private static class RetrofitHolder{
        private static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }
}
