package com.allever.allsample.vodeotest;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

import java.io.IOException;


/**
 * Created by allever on 17-10-18.
 */

public class VideoTestActivity extends BaseActivity {
    private static final String TAG = "VideoTestActivity";
    //private VideoView videoView;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_test_activity);

        /*
        videoView = (VideoView)findViewById(R.id.id_vv);
       // videoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/JHRN0101.mov");
        //videoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/JHRN0102.mp4");
        videoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/JHRN0103.mov");

        videoView.start();
        */

        surfaceView = (SurfaceView)findViewById(R.id.id_sv);
        mediaPlayer = new MediaPlayer();
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mediaPlayer.setDisplay(holder);
                    mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/JHRN0101.mov");
                    mediaPlayer.prepare();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mediaPlayer!=null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion: Completion");
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onError: what = " + what);
                Log.d(TAG, "onError: extra = " + extra);
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //videoView.stopPlayback();
    }
}
