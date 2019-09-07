package com.allever.allsample.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.allever.allsample.mvp.presenter.BasePresenter;

/**
 * Created by allever on 17-10-11.
 */

public abstract class MVPBaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity{
    protected T mPresenter;//Presenter 对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}
