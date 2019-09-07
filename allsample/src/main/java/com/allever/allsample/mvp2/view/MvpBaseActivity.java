package com.allever.allsample.mvp2.view;

import androidx.appcompat.app.AppCompatActivity;

import com.allever.allsample.mvp2.presenter.BasePresenter;

/**
 * Created by allever on 17-12-10.
 */

public class MvpBaseActivity<V extends IMvpBaseView, P extends BasePresenter<V>> extends AppCompatActivity implements IMvpBaseView {

}
