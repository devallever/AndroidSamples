package com.allever.mysimple.rxAndroid;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Allever on 2016/12/13.
 */

public class RxAndroidActivity extends BaseActivity {

    private static final String TAG = "RxAndroidActivity";

    private Button btn_main_thread;
    private Button btn_async;
    private Button btn_rx_android;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_android_activity_layout);
        setToolbar((Toolbar)findViewById(R.id.id_toolbar),"RxAndroid");

        initView();
    }

    private void initView(){
        btn_async = (Button)findViewById(R.id.id_rx_android_activity_btn_async);
        btn_main_thread = (Button)findViewById(R.id.id_rx_android_activity_btn_main_thread);
        btn_rx_android = (Button)findViewById(R.id.id_rx_android_activity_btn_rx_android);

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //发通知给subscriber更新
                Log.d(TAG, "call: ");
                subscriber.onNext(getString());
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());

        //btn_rx_android.setOnClickListener(v -> Toast.makeText(this,"okkk",Toast.LENGTH_LONG).show());

        btn_rx_android.setOnClickListener(view -> {
            Subscriber<String > subscriber = new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(String s) {
                    Log.d(TAG, "onNext: ");
                    Toast.makeText(RxAndroidActivity.this, s, Toast.LENGTH_LONG).show();
                }
            };
            observable.subscribe(subscriber);
        });


    }


    private String getString(){
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Complete!!!";
    }
}
