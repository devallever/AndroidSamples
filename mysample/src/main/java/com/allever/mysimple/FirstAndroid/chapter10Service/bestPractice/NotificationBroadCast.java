package com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by allever on 17-9-30.
 */

public class NotificationBroadCast extends BroadcastReceiver {

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

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case "com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice.ACTION_CONTROL":
                if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.DOWNLOADING){
                    //正在下载,取消
                    downloadBinder.pauseDownload();
                }else if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.PAUSE){
                    //downloadBinder.startDownload(url,"下载中...");
                }
                break;

        }

    }
}