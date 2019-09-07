package com.allever.allsample.webtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-8-16.
 */

public class LoginActivity  extends BaseActivity {
    private EditText et_username;
    private EditText et_pwd;
    private Button btn_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        initView();
    }

    private void initView(){
        et_pwd = (EditText)findViewById(R.id.id_login_activity_et_pwd);
        et_username = (EditText)findViewById(R.id.id_login_activity_et_username);
        btn_login = (Button)findViewById(R.id.id_login_activity_btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = et_username.getText().toString();
                final String pwd = et_pwd.getText().toString();
                WebTestActivity.startAction(LoginActivity.this,username);
            }
        });

    }

    public static void startAction(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }


}
