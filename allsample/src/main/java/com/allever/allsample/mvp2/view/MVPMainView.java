package com.allever.allsample.mvp2.view;

import com.allever.allsample.okhttp.bean.LoginRoot;

/**
 * Created by allever on 17-12-10.
 */

public interface MVPMainView {
    void requestLoading();
    void requestSuccess(String result);
    void requestFail(String msg);
}
