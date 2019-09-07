package com.allever.allsample.mvp2.presenter;

import com.allever.allsample.mvp2.view.IMvpBaseView;

/**
 * Created by allever on 17-12-10.
 */

public  abstract class BasePresenter<V extends IMvpBaseView> {
    private V mMvpView;
    public void attachMvpView(V view){
        this.mMvpView = view;
    }

    public void detachMvpView(){
        mMvpView = null;
    }

    public V getMvpView(){
        return mMvpView;
    }
}
