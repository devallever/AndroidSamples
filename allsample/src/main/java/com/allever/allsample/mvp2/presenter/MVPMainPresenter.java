package com.allever.allsample.mvp2.presenter;

import com.allever.allsample.mvp2.model.LoginModel;
import com.allever.allsample.mvp2.view.MVPMainView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.http.PUT;

/**
 * Created by allever on 17-12-10.
 */

public class MVPMainPresenter {
    private LoginModel mLoginModel;
    private MVPMainView mMvpMainView;


//    public MVPMainPresenter(MVPMainView mMvpMainView){
//        this.mMvpMainView = mMvpMainView;
//        this.mLoginModel = new LoginModel();
//    }

    public MVPMainPresenter(){
        mLoginModel = new LoginModel();
    }

    public void login(String username, String pwd){
        mLoginModel.doLogin(username, pwd, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMvpMainView.requestFail("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMvpMainView.requestSuccess(response.body().string());
            }
        });
    }

    public void attach(MVPMainView mMvpMainView){
        this.mMvpMainView = mMvpMainView;
    }

    public void detach(){
        mMvpMainView = null;
    }

    public void cancelHttp(){

    }
}
