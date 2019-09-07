package com.allever.allsample.mvp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;
import com.allever.allsample.mvp2.presenter.MVPMainPresenter;
import com.allever.allsample.mvp2.view.MVPMainView;

/**
 * Created by allever on 17-12-10.
 */

public class MVP2Activity extends BaseActivity implements MVPMainView {
    private static final byte MSG_LOGIN = 0X01;
    private static final String TAG = "MVP2Activity";
    private Button btn_get;
    private TextView tv_result;

    private MVP2Handler mvp2Handler;
    private MVPMainPresenter mMvpMainPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_2_activity_layout);

        mvp2Handler = new MVP2Handler();
        mMvpMainPresenter = new MVPMainPresenter();
        mMvpMainPresenter.attach(this);

        btn_get = (Button)findViewById(R.id.id_mvp_2_btn_get);
        tv_result = (TextView)findViewById(R.id.id_mvp_2_tv_result);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserList();
            }
        });
    }

    @Override
    protected void onDestroy() {
        mMvpMainPresenter.detach();
        super.onDestroy();
    }

    private void getUserList(){
        mMvpMainPresenter.login("allever","dixm");
    }

    @Override
    public void requestLoading() {

    }

    @Override
    public void requestSuccess(String result) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = result;
        mvp2Handler.sendMessage(msg);
    }

    @Override
    public void requestFail(String msg) {
        Log.d(TAG, "requestFail: " + msg);
    }

    private class MVP2Handler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_LOGIN:
                    tv_result.setText((String)msg.obj);
                    break;
                default:
                    break;
            }
        }
    }



    public static void startAction(Context context){
        Intent intent = new Intent(context, MVP2Activity.class);
        context.startActivity(intent);
    }
}
