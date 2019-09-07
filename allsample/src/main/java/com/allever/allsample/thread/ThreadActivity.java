package com.allever.allsample.thread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;
import com.allever.allsample.thread.handlerthread.HandlerThreadActivity;

/**
 * Created by allever on 17-7-1.
 */

public class ThreadActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_handler_thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_activity_layout);

        initView();


    }

    private void initView(){
        btn_handler_thread = (Button)findViewById(R.id.id_thread_activity_btn_handler_thread);
        btn_handler_thread.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_thread_activity_btn_handler_thread) {
            HandlerThreadActivity.startActivity(this);
        }
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,ThreadActivity.class);
        context.startActivity(intent);
    }



}
