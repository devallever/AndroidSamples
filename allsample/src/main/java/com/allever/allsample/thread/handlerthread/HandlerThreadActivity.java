package com.allever.allsample.thread.handlerthread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-7-1.
 */

public class HandlerThreadActivity extends BaseActivity {
    private static final String TAG = "HandlerThreadActivity";

    private Button btn_post_to_sub_thread;
    private Button btn_post_to_main_thread;

    private HandlerThread handlerThread;
    private Handler handler;
    private Handler mainHandler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "Thread id = " + Thread.currentThread().getId());
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_thread_activity_layout);



        Log.d(TAG, "Main Thread id = " + Thread.currentThread().getId());
        handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        handler.post(runnable);

        mainHandler = new Handler();

        btn_post_to_sub_thread = (Button)findViewById(R.id.id_handler_thread_activity_btn_post_to_sub_thread);
        btn_post_to_sub_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.post(runnable);
            }
        });

        btn_post_to_main_thread = (Button)findViewById(R.id.id_handler_thread_activity_btn_post_to_main_thread);
        btn_post_to_main_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Thread id = " + Thread.currentThread().getId());
                        mainHandler.post(runnable);
                    }
                }).start();

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,HandlerThreadActivity.class);
        context.startActivity(intent);
    }
}
