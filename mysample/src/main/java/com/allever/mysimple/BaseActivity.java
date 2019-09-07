package com.allever.mysimple;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;


/**
 * Created by Allever on 2016/11/3.
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(this);
        setContentView(R.layout.base_activity);

    }

    protected void show(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    protected void log(String TAG, String message){

    }

    protected void setToolbar(Toolbar toolbar, String title){
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    protected void showProgressDialog(String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void dismissProgressDialog(){
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }



}
