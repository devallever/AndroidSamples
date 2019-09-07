package com.allever.allsample.vodeotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;
import com.allever.allsample.vodeotest.model.MapData;

/**
 * Created by allever on 17-10-13.
 */

public class VideoTest2Activity extends BaseActivity{

    private MapHelper mMapHelper;
    private static final String TAG = "VideoTest2Activity";

    private FullScreenVideoView mVideoView;
    //private MediaPlayer mAudioMediaPlayer;

    private TextView tv_desc;

    private String mMapDir;

    private boolean isPause = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_2);

        mMapDir = getApplicationContext().getFilesDir().getAbsolutePath() + "/NorRock";
        //mMapDir = Environment.getExternalStorageDirectory().getPath() + "/jfitness";
        ///data/user/0/com.allever.allsample/files
        Log.d(TAG, "onCreate: mMapDir = " + mMapDir);

        initView();

        mMapHelper = new MapHelper(this, mVideoView,mMapDir, new MapHelper.CallBack() {
            @Override
            public void updateDescription(final String descString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_desc.setText(descString);
                    }
                });

            }

            @Override
            public void parserXMLFinished(MapData mapData) {
                //模拟
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int levelId = 0;
                            while (levelId <= 20){
                                mMapHelper.setLevelId(levelId);
                                Thread.sleep(10000);
                                levelId++;
                                if (levelId > 20){
                                    levelId = 0;
                                }
                            }
                        }catch (InterruptedException ie){
                            ie.printStackTrace();
                        }
                    }
                }).start();
                */
            }

            @Override
            public void playFinished() {
                //goto ReportActivity
            }

            @Override
            public void updateSetting(float resistance, float incline) {

            }
        });


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: 没有权限");
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }else{
            //
            Log.d(TAG, "onCreate: 有权限");
            mMapHelper.startTask();

            //下载完成
            //解压业务放在MapActivity或MapFragment
            //FileUtil.createDir(mMapDir);
            ZipExtractorTask zipExtractorTask = new ZipExtractorTask(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + "NorRock.zip",
                    mMapDir, this, true, new ZipExtractorTask.ZipExtractorTaskListener() {
                @Override
                public void unZipFinished() {
                    //解压完成，开始解析xml
                    //parseMapXml();
                    mMapHelper.startTask();
                }
            });
            //zipExtractorTask.execute();

        }
    }

    private void initView(){
        tv_desc = (TextView)findViewById(R.id.id_sport_main_tv_desc);
        mVideoView = (FullScreenVideoView)findViewById(R.id.id_sport_main_video_view);
        //mAudioMediaPlayer = new MediaPlayer();
        tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPause){
                    mMapHelper.pause();
                    Log.d(TAG, "onClick: stop");
                }else {
                    mMapHelper.restart();
                    Log.d(TAG, "onClick: restart");
                }
                isPause = !isPause;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapHelper.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //mMapHelper.startTask();
                    ZipExtractorTask zipExtractorTask = new ZipExtractorTask(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + "NorRock.zip",
                            mMapDir, this, true, new ZipExtractorTask.ZipExtractorTaskListener() {
                        @Override
                        public void unZipFinished() {
                            //解压完成，开始解析xml
                            //parseMapXml();
                            mMapHelper.startTask();
                        }
                    });
                    zipExtractorTask.execute();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: 拒绝权限");
                }
                break;
        }
    }



}
