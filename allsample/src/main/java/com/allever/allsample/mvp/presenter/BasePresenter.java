package com.allever.allsample.mvp.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by allever on 17-10-11.
 */

public abstract class BasePresenter<T> {
    protected Reference<T> mViewRef;

    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);//建立关联
    }

    protected T getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView(){
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
