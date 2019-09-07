package com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-3-15.
 */

public class LoginActivity extends FirstAndroidBaseActivity {

    private EditText et_account;
    private EditText et_password;

    private String account;
    private String password;

    private Button btn_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        et_account = (EditText)findViewById(R.id.id_login_activity_et_account);
        et_password = (EditText)findViewById(R.id.id_login_activity_et_password);

        btn_login = (Button)findViewById(R.id.id_login_activity_btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = et_account.getText().toString();
                password = et_password.getText().toString();

                if(account.equals("allever") && password.equals("dixm")){
                    Intent intent = new Intent(LoginActivity.this,LoginSuccessActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Account or Password is wrong!!1",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
