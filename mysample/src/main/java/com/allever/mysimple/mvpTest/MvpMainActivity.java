package com.allever.mysimple.mvpTest;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.mvpTest.bean.User;
import com.allever.mysimple.mvpTest.mvp.presentor.UserPresenter;
import com.allever.mysimple.mvpTest.mvp.view.BaseView;

/**
 * Created by Allever on 2017/1/19.
 */

public class MvpMainActivity extends BaseActivity implements BaseView{

    private Button btn_search;
    private TextView tv_signature;
    private EditText et_username;
    private ProgressDialog progressDialog;

    private UserPresenter userPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_main_activity_layout);

        initView();

    }
    private void initView(){

        setToolbar((Toolbar) findViewById(R.id.id_toolbar),"MVP + Retrofit + RxJava");

        btn_search = (Button)findViewById(R.id.id_mvp_main_btn_search);
        tv_signature = (TextView)findViewById(R.id.id_mvp_main_tv_signature);
        et_username=(EditText)findViewById(R.id.id_mvp_main_et_username);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(".");

        userPresenter = new UserPresenter();
        userPresenter.attachView(this);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                userPresenter.getSignature(et_username.getText().toString());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPresenter.detachView();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        progressDialog.hide();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.hide();
    }

    @Override
    public void showText(User user) {
        tv_signature.setText(user.getUser().getSignature());
    }
}
