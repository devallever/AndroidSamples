package com.allever.mysimple.mvpTest.mvp.presentor;

import com.allever.mysimple.mvpTest.mvp.view.BaseView;

/**
 * Created by Allever on 2017/1/19.
 */

public interface BasePresenter <T extends BaseView>{
    void attachView(T view);
    void detachView();
    void getSignature(String username);
}
