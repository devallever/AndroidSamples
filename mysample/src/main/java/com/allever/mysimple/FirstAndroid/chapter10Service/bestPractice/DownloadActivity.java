package com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-4-20.
 */

public class DownloadActivity  extends FirstAndroidBaseActivity implements View.OnClickListener {

    //private IntentFilter intentFilter;
    //private NotificationBroadCast notificationBroadCast;

    //private String url = "https://raw.githubusercontent.com/devallever/MyCoolWeather/master/app/simpleWeather.apk";
    private String url = "https://raw.githubusercontent.com/devallever/MyCoolWeather/master/app/simpleWeather.apk";


    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private Button btn_start;
    private Button btn_pause;
    private Button btn_cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_activity_layout);

        btn_cancel = (Button)findViewById(R.id.id_download_activity_btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_start = (Button)findViewById(R.id.id_download_activity_btn_start);
        btn_start.setOnClickListener(this);
        btn_pause = (Button)findViewById(R.id.id_download_activity_btn_pause);
        btn_pause.setOnClickListener(this);

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
        //6.0权限管理
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //intentFilter = new IntentFilter();
        //intentFilter.addAction("com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice.ACTION_CONTROL");
        //notificationBroadCast = new NotificationBroadCast();
        //registerReceiver(notificationBroadCast,intentFilter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "拒绝授权无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        //unregisterReceiver(notificationBroadCast);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_download_activity_btn_cancel) {
            downloadBinder.cancelDownload();
        } else if (id == R.id.id_download_activity_btn_start) {
            downloadBinder.startDownload(url, "影片3下载中...");
        } else if (id == R.id.id_download_activity_btn_pause) {
            downloadBinder.pauseDownload();
        }
    }

}
