package com.allever.mysimple.mvpTest.mvp.model;

import com.allever.mysimple.mvpTest.bean.User;

import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * Created by Allever on 2017/1/19.
 */

public interface BaseModel {
    void getSignature(Subscriber<User> subscriber, String username);
}
