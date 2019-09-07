package com.allever.mysimple.FirstAndroid.chapter10Service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-4-17.
 */

public class ServiceActivity extends FirstAndroidBaseActivity  implements View.OnClickListener{
    private static final String TAG = "ServiceActivity";
    private Button btn_start_service;
    private Button btn_stop_service;
    private Button btn_bind_service;
    private Button btn_unbind_service;
    private Button btn_front_service;
    private Button btn_start_intent_service;

    private MyService.MyBinder myBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ()");
            myBinder = (MyService.MyBinder) service;
            myBinder.startDownload();
            myBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ()");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity_layout);

        //Intent intent = new Intent(this, ServiceActivity.class);

        btn_start_service = (Button)findViewById(R.id.id_service_activity_btn_start_service);
        btn_start_service.setOnClickListener(this);

        btn_stop_service = (Button)findViewById(R.id.id_service_activity_btn_stop_service);
        btn_stop_service.setOnClickListener(this);


        btn_bind_service = (Button)findViewById(R.id.id_service_activity_btn_bind_service);
        btn_bind_service.setOnClickListener(this);

        btn_unbind_service = (Button)findViewById(R.id.id_service_activity_btn_unbind_service);
        btn_unbind_service.setOnClickListener(this);

        btn_front_service = (Button)findViewById(R.id.id_service_activity_btn_start_front_service);
        btn_front_service.setOnClickListener(this);

        btn_start_intent_service = (Button)findViewById(R.id.id_service_activity_btn_start_intent_service);
        btn_start_intent_service.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Intent myServiceIntent = new Intent(this,  MyService.class);
        int id = v.getId();
        if (id == R.id.id_service_activity_btn_start_service) {
            Intent startServiceIntent = new Intent(this, MyService.class);
            startService(startServiceIntent);
        } else if (id == R.id.id_service_activity_btn_stop_service) {
            Intent stopServiceIntent = new Intent(this, MyService.class);
            stopService(stopServiceIntent);
        } else if (id == R.id.id_service_activity_btn_bind_service) {
            Intent bindServiceIntent = new Intent(this, MyService.class);
            bindService(bindServiceIntent, serviceConnection, BIND_AUTO_CREATE);
        } else if (id == R.id.id_service_activity_btn_unbind_service) {//Intent unbindServiceIntent = new Intent(this, MyService.class);
            unbindService(serviceConnection);
        } else if (id == R.id.id_service_activity_btn_start_front_service) {
            Intent startFrontServiceIntent = new Intent(this, MyService.class);
            startService(startFrontServiceIntent);
        } else if (id == R.id.id_service_activity_btn_start_intent_service) {
            Intent startIntentService = new Intent(this, MyIntentService.class);
            startService(startIntentService);
        }
    }
}
