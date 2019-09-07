package com.allever.mysimple.mvpTest.mvp.view;

import com.allever.mysimple.mvpTest.bean.User;

/**
 * Created by Allever on 2017/1/19.
 */

public interface BaseView {
    void showProgressDialog();
    void hideProgressDialog();
    void showText(User user);
    void showErrorMessage(String message);
}
