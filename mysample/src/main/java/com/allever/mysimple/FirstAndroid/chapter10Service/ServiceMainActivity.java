package com.allever.mysimple.FirstAndroid.chapter10Service;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice.DownloadActivity;
import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-4-15.
 */

public class ServiceMainActivity extends FirstAndroidBaseActivity implements View.OnClickListener {

    private Handler handler;
    private Looper looper;

    private static final int MESSAGE_OK = 1000;


    private Button btn_handler;
    private Button btn_async;
    private Button btn_service;
    private Button btn_best_practice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_main_activity_layout);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MESSAGE_OK:
                        String result = (String) msg.obj;
                        Toast.makeText(ServiceMainActivity.this,"handleMessage():\n" + result,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        btn_handler = (Button)findViewById(R.id.id_service_main_activity_btn_handler);
        btn_handler.setOnClickListener(this);

        btn_async = (Button)findViewById(R.id.id_service_main_activity_btn_async_task);
        btn_async.setOnClickListener(this);

        btn_service = (Button)findViewById(R.id.id_service_main_activity_btn_service);
        btn_service.setOnClickListener(this);

        btn_best_practice = (Button)findViewById(R.id.id_service_main_activity_btn_service_best_practice);
        btn_best_practice.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_service_main_activity_btn_handler) {
            Message message = new Message();
            message.what = MESSAGE_OK;
            String strMessage = "Hello";
            message.obj = strMessage;
            handler.sendMessage(message);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ServiceMainActivity.this, "handler.post():\n" + strMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (id == R.id.id_service_main_activity_btn_async_task) {
            new MyAsyncTask().execute("url address");
            Looper.prepare();
        } else if (id == R.id.id_service_main_activity_btn_service) {
            Intent intent = new Intent(this, ServiceActivity.class);
            startActivity(intent);
        }
//        else if (id == R.id.id_servic_e_main_activity_btn_service_best_practice) {
//            Intent intentDownload = new Intent(this, DownloadActivity.class);
//            startActivity(intentDownload);
//        }
    }


    class MyAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            //后台任务执行开始前调用，进行界面上的初始化操作，如显示对话框
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

            String url = urls[0];
            //...
            String result = url;  // 假设进行网络请求后返回的数据
            return result;   //返回值作为 onPostExecute() 方法的参数
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(ServiceMainActivity.this, s, Toast.LENGTH_SHORT).show();
            //super.onPostExecute(s);
        }
    }

}
