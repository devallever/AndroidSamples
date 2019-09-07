package com.allever.allsample.mvp2.model;

import com.allever.allsample.okhttp.util.OkHttpUtil;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * Created by allever on 17-12-10.
 */

public class LoginModel {
    public void doLogin(String username, String password, Callback callback){
        final String url = OkHttpUtil.BASE_URL + "LoginServlet?username=" + username + "&password=" + password + "&longitude=22.111&latitude=111.2222";
        OkHttpUtil.loginGet(url,callback);
    }

    public void cancleHttp(){

    }
}
