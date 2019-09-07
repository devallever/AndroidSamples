package com.allever.mysimple.mvpTest.mvp.model;

import com.allever.mysimple.mvpTest.bean.User;
import com.allever.mysimple.mvpTest.retrofit.RetrofitUtil;

import rx.Subscriber;

/**
 * Created by Allever on 2017/1/19.
 */

public class UserModel implements BaseModel {
    @Override
    public void getSignature(Subscriber<User> subscriber, String username) {
        RetrofitUtil.getInstance().getSignature(subscriber,username);
    }
}
