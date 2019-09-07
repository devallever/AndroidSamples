package com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.allever.mysimple.R;

import java.io.File;

/**
 * Created by allever on 17-4-20.
 */

public class DownloadService extends Service {
    private static final String TAG = "DownloadService";

    private IntentFilter intentFilter;
    private NotificationBroadCast notificationBroadCast;

    public static final int ACTION_START = 0x01;
    public static final int ACTION_PAUSE = 0x02;

    private DownloadTask downloadTask;
    private String downloadUrl;
    private DownloadBinder downloadBinder = new DownloadBinder();
    private RemoteViews mRemoteView;
    private ProgressBar mProgressBar;
    private int mFileLength = -1;
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress,int fileLength) {
            mFileLength = fileLength;
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", 100));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed.", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPaused() {
            downloadTask = null;
            getNotificationManager().notify(1, getNotification("以暂停.", -1));
            Toast.makeText(DownloadService.this, "Download Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Download Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mRemoteView = new RemoteViews(getPackageName(), R.layout.notification_download);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice.ACTION_CONTROL");
        notificationBroadCast = new NotificationBroadCast();
        registerReceiver(notificationBroadCast,intentFilter);
        return downloadBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationBroadCast);
    }

    class DownloadBinder extends Binder{
        public void startDownload(String url,String message){
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(downloadListener);
                downloadTask.execute(url);
                startForeground(1, getNotification(message, 0));
                //Toast.makeText(DownloadService.this, "Downloading...",Toast.LENGTH_SHORT).show();
            }
        }
        public void pauseDownload(){
            if (downloadTask != null) downloadTask.pauseDownload();
        }

        public void cancelDownload(){
            if (downloadTask != null) downloadTask.cancelDownload();
            else {
                if (downloadUrl != null){
                    //取消下载时将临时文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(dir + fileName);
                    if (file.exists()) file.delete();
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"Canceled.", Toast.LENGTH_SHORT).show();

                }
            }
        }




    }
    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress){
        //Intent intent = new Intent(DownloadService.this, DownloadActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        builder.setCustomContentView(mRemoteView);
        // builder.setContentIntent(pendingIntent);
        mRemoteView.setTextViewText(R.id.id_notification_download_tv_title,title);
        Intent controlIntent = new Intent("com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice.ACTION_CONTROL");
        if (progress > 0){//正在下载
            //builder.setContentText(progress + "%");
            try {
                //DownloadTask.getContentLength(downloadUrl);
                mRemoteView.setTextViewText(R.id.id_notification_download_tv_description,progress + "% " + mFileLength/(1024*1024) + "MB");
                mRemoteView.setProgressBar(R.id.id_notification_download_progress_bar,100,progress,false);
            }catch (Exception e){
                e.printStackTrace();
            }

            //builder.setProgress(100, progress, false);
        }else if (progress == 100){
        }else if (progress == -1){

        }

        if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.DOWNLOADING){
            mRemoteView.setImageViewResource(R.id.id_notification_download_iv_control,R.mipmap.pause);
        }else if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.PAUSE){
            mRemoteView.setImageViewResource(R.id.id_notification_download_iv_control,R.mipmap.start);
        }else if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.CANCEL){
        }else if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.FINISHED){
            mRemoteView.setImageViewResource(R.id.id_notification_download_iv_control,R.mipmap.start);
        }else if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.FAILED){
            Log.d(TAG, "getNotification: FAILED");
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,controlIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteView.setOnClickPendingIntent(R.id.id_notification_download_iv_control,pendingIntent);
        builder.setContentIntent(pendingIntent);
        //controlIntent.putExtra("action",progress);
        //builder.setContentIntent(pendingIntent);
        return builder.build();
    }

    class NotificationBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case "com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice.ACTION_CONTROL":
                    if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.DOWNLOADING){
                        //正在下载,取消
                        //downloadBinder.pauseDownload();
                        downloadBinder.pauseDownload();
                    }else if (DownloadTask.DOWNLOAD_STATUE == DownloadTask.DOWNLOAD_STATE.PAUSE){
                        //downloadBinder.startDownload(url,"下载中...");
                        downloadBinder.startDownload(downloadUrl,"下载中");
                    }
                    break;

            }

        }
    }


}
