package com.allever.mysimple.mvpTest.mvp.presentor;

import android.text.TextUtils;

import com.allever.mysimple.mvpTest.bean.User;
import com.allever.mysimple.mvpTest.mvp.view.BaseView;
import com.allever.mysimple.mvpTest.mvp.model.UserModel;

import rx.Subscriber;

/**
 * Created by Allever on 2017/1/19.
 */

public class UserPresenter implements BasePresenter {
    private BaseView baseView;
    private UserModel userModel;

    public UserPresenter(){
        userModel = new UserModel();
    }

    @Override
    public void attachView(BaseView view) {
        baseView = view;
    }

    @Override
    public void detachView() {
        baseView = null;
    }

    @Override
    public void getSignature(String username) {
        if (TextUtils.isEmpty(username)){
            baseView.showErrorMessage("请输入用户名");
        }
        if (userModel != null){
            userModel.getSignature(new Subscriber<User>() {
                @Override
                public void onCompleted() {
                    baseView.hideProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    baseView.showErrorMessage("加载失败...");
                }

                @Override
                public void onNext(User user) {
                    baseView.showText(user);
                }
            },username);
        }
    }
}
