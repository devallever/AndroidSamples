package com.allever.mysimple.nearbyProject.mvp.model;

import android.util.Log;

import com.allever.mysimple.rxRetrofit.retrofit.RetrofitUtil;

import rx.Subscriber;

/**
 * Created by Allever on 2017/1/21.
 */

public class NearbyNewsModelImpl implements NearbyNewsModel{
    private static final String TAG = "NearbyNewsModelImpl";
    @Override
    public void getNearbyNews(Subscriber subscriber, String longitude, String latitude, String page) {
        Log.d(TAG, "NearbyNewsModelImpl->getNearbyNews: ");
        RetrofitUtil.getInstance().getNearbyNews(subscriber,latitude,longitude,page);
    }
}
