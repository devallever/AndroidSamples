package com.allever.mysimple.nearbyProject.mvp.model;

import android.view.View;

import rx.Subscriber;

/**
 * Created by Allever on 2017/1/21.
 */

public interface NearbyNewsModel {
    void getNearbyNews(Subscriber subscriber, String longitude,String latitude,String page);
}
