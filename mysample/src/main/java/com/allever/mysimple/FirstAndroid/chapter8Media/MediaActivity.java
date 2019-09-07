package com.allever.mysimple.FirstAndroid.chapter8Media;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.os.EnvironmentCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.allever.mysimple.FirstAndroid.FirstAndroidMainActivity;
import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by allever on 17-3-26.
 */

public class MediaActivity extends FirstAndroidBaseActivity implements View.OnClickListener{

    private Button btn_send_notification;
    private Button btn_camera;

    private Button btn_audio_play;
    private Button btn_audio_pause;
    private Button btn_audio_stop;

    private Button btn_video_play;
    private Button btn_video_pause;
    private Button btn_video_resume;
    private VideoView videoView;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_activity_layout);

        btn_send_notification = (Button)findViewById(R.id.id_media_activity_send_notify);
        btn_send_notification.setOnClickListener(this);

        btn_camera = (Button)findViewById(R.id.id_media_activity_btn_camera);
        btn_camera.setOnClickListener(this);

        btn_audio_play = (Button)findViewById(R.id.id_media_activity_btn_audio_play);
        btn_audio_play.setOnClickListener(this);
        btn_audio_pause = (Button)findViewById(R.id.id_media_activity_btn_audio_pause);
        btn_audio_pause.setOnClickListener(this);
        btn_audio_stop = (Button)findViewById(R.id.id_media_activity_btn_audio_stop);
        btn_audio_stop.setOnClickListener(this);

        btn_video_play = (Button)findViewById(R.id.id_media_activity_btn_video_play);
        btn_video_play.setOnClickListener(this);
        btn_video_pause = (Button)findViewById(R.id.id_media_activity_btn_video_pause);
        btn_video_pause.setOnClickListener(this);
        btn_video_resume = (Button)findViewById(R.id.id_media_activity_btn_video_resume);
        btn_video_resume.setOnClickListener(this);
        videoView = (VideoView)findViewById(R.id.id_media_activity_video_view);

        //运行时权限处理
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            initMediaPlay();
            initVideoView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlay();
                    initVideoView();
                }else{
                    Toast.makeText(this,"请授权", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initMediaPlay(){
        File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
        try {
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void initVideoView(){
        File file = new File(Environment.getExternalStorageDirectory(),"video.mp4");
        videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_media_activity_send_notify) {
            Intent intent = new Intent(this, FirstAndroidMainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setWhen(System.currentTimeMillis())
                    //设置点击行为
                    .setContentIntent(pendingIntent)
                    //点击后消失
                    .setAutoCancel(true)
                    //设置声音
                    .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                    //设置震动 震动1s 静止1s 震动1s 要申明权限
                    .setVibrate(new long[]{0, 1000, 1000, 1000})
                    //设置呼吸灯闪烁
                    .setLights(Color.GREEN, 1000, 1000)
                    //设置默认
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    //设置长文本
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("An Activity is an application component that provides a screen with which users can interact in order to do something, such as dial the phone, take a photo, send an email, or view a map."))
                    //设置展开大图,,设置了长文本会看不到
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.expensive)))
                    //设置通知重要程度
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .build();
            notificationManager.notify(1, notification);
        } else if (id == R.id.id_media_activity_btn_camera) {
            Intent intentCamera = new Intent(this, CameraActivity.class);
            startActivity(intentCamera);
        } else if (id == R.id.id_media_activity_btn_audio_play) {
            mediaPlayer.start();
        } else if (id == R.id.id_media_activity_btn_audio_pause) {
            mediaPlayer.pause();
        } else if (id == R.id.id_media_activity_btn_audio_stop) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                initMediaPlay();
            }
        } else if (id == R.id.id_media_activity_btn_video_play) {
            videoView.start();
        } else if (id == R.id.id_media_activity_btn_video_pause) {
            videoView.pause();
        } else if (id == R.id.id_media_activity_btn_video_resume) {
            videoView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (videoView != null){
            videoView.suspend();
        }
    }
}
