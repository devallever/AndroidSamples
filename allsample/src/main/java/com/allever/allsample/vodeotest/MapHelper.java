package com.allever.allsample.vodeotest;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import com.allever.allsample.util.FileUtil;
import com.allever.allsample.vodeotest.model.Atom;
import com.allever.allsample.vodeotest.model.DescString;
import com.allever.allsample.vodeotest.model.Event;
import com.allever.allsample.vodeotest.model.LanguageDictionary;
import com.allever.allsample.vodeotest.model.LanguageType;
import com.allever.allsample.vodeotest.model.Level;
import com.allever.allsample.vodeotest.model.MapData;
import com.allever.allsample.vodeotest.model.PlayElement;
import com.allever.allsample.vodeotest.model.Playlist;
import com.allever.allsample.vodeotest.model.Source;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * @author Created by allever on 17-10-17.
 */


public class MapHelper {

    private final String SOURCE_TYPE_IMAGE = "image_thumbnail";
    private final String SOURCE_TYPE_VIDEO_WORKOUT = "video_workout";
    private final String SOURCE_TYPE_VIDEO_SPLASH = "video_splash";
    private final String SOURCE_TYPE_AUDIO = "audio_sfx";
    private final String SOURCE_TYPE_TEXT = "text_scroll";

    private static final String TAG = "MapHelper";

    private MapData mMapData;//解析xml保存对象
    private Context mContext;

    private FullScreenVideoView mVideoView;

    private MediaPlayer mAudioMediaPlayer;//播放音效
    private MediaPlayer mMusicMediaPlayer;//播放音乐
    private CallBack mCallBack;
    private String mMapDir;//根目录: /data/data/packageName/files/<MapName>
    private int mLevelId = 1;//levelId
    //private String mMapName;//地图包名称～地图包目录名称.
    private TimerThread mTimer;//计时器
    private Handler mHandler;
    private boolean isReleased = false;
    private boolean isPause = false;

    private CountDownTimer mHighCountDownTimer;
    private CountDownTimer mLowCountDownTimer;


    private List<String> mVideoNameList;//播放列表，绝对路径
    private List<String> mMusicList;
    private int mVideoPosition = 0;//视频播放列表索引
    private int mMusicPosition = 0;//音乐播放列表索引

    //解析xml转为model
    private Playlist mPlayList;
    private Map<Integer, Source> mSourceMap;
    private LanguageDictionary mLanguageDictionary;
    private Map<Integer, DescString> mDescStringMap;

    private VideoHandler.XMLParserListener xmlParserListener = new VideoHandler.XMLParserListener() {
        @Override
        public void finished(MapData mapData) {
            //xml解析完成.
            mMapData = mapData;
            mSourceMap = mMapData.getSourceMap();
            mDescStringMap = mMapData.getLanguageDictionary().getDescStringMap();
            mPlayList = mMapData.getPlaylist();

            /*
            //解析信息
            Log.d(TAG, "finished: 地图包信息");
            PackageInfo packageInfo = mMapData.getPackageInfo();
            Log.d(TAG, "version = " + packageInfo.getVersion());
            Log.d(TAG, "title = " + packageInfo.getTitle());
            Log.d(TAG, "isFlat = " + packageInfo.isFlat());
            Log.d(TAG, "isCoached = " + packageInfo.isCoached());

            Log.d(TAG, "字符串资源,其中一个id的内容");
            int stringId = 1;
            //Map<Integer,DescString> descStringMap = mapData.getLanguageDictionary().getDescStringMap();
            DescString descString = mDescStringMap.get(stringId);
            Map<LanguageType, String> descContentMap =  descString.getDescMap();
            for (Map.Entry<LanguageType,String> entry:descContentMap.entrySet()){
                Log.d(TAG, entry.getKey()+": " + entry.getValue());
            }

            StringBuilder stringBuilder = new StringBuilder();
            //Map<Integer,Source> sourceMap = mapData.getSourceMap();
            //String sourceName = "";
            Log.d(TAG, "资源列表");
            Source source;
            for (int i=1; i<mSourceMap.size(); i++){
                source = mSourceMap.get(i);
                //sourceName = sourceMap.get(i).getSource();
                stringBuilder.append("id = " + source.getId() + "\t type = " + source.getType());
                if (source.getSource() != "") stringBuilder.append( "\t source = " + source.getSource());
                if (source.getStringId() != -1) stringBuilder.append("\t stringid = " + source.getStringId());
                stringBuilder.append("\n");
            }
            Log.d(TAG, stringBuilder.toString());
            stringBuilder.setLength(0);

            Log.d(TAG, "视频播放列表");
            List<PlayElement> playElementList = mMapData.getPlaylist().getPlayElementList();
            Map<Integer, Atom> atomMap = mMapData.getAtomMap();
            for (PlayElement playElement: playElementList){
                Log.d(TAG, mSourceMap.get(atomMap.get(playElement.getAtomId()).getVideoSourceId()).getSource());
            }

            Log.d(TAG, "AtomId = 1, levelId = 1 时的事件列表");
            int levelId = 1;
            int atomId = 1;
            Atom atom = atomMap.get(atomId);
            Map<Integer, Level> levelMap = atom.getLevelMap();
            Map<Integer, Event> eventMap = levelMap.get(levelId).getEventMap();
            //遍历map
            Event event;
            for (int i = 0; i<=mSourceMap.get(atomMap.get(atomId).getVideoSourceId()).getDuration();i++){
                event = eventMap.get(i);
                if (event ==null) continue;
                stringBuilder.append("time = " + event.getTime() + "\t resistance = " + event.getResistance() + "\t incline = " + event.getIncline());
                if (event.getEventSource() != null){
                    stringBuilder.append("\t sourceId = " + event.getEventSource().getSourceId());
                }
                Log.d(TAG, stringBuilder.toString());
                stringBuilder.setLength(0);
            }
            */


            //获取背景音乐播放列表
            mMusicList = FileUtil.getSelectedTypeFile(mMapDir+ "/music","mp3");

            //获取视频播放列表
            mVideoNameList = new ArrayList<>();
            String videoName;
            for (PlayElement playElement: mPlayList.getPlayElementList()){
                videoName = mSourceMap.get(mMapData.getAtomMap().get(playElement.getAtomId()).getVideoSourceId()).getSource();
                mVideoNameList.add(mMapDir + "/videos/" + videoName);
            }

            //if (mCallBack != null) mCallBack.parserXMLFinished(mMapData);//run on ui thread

            //初始化并播放 UI Thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            });
        }
    };
    public MapHelper(Context context, FullScreenVideoView videoView, String mapDir, CallBack callBack){
        this.mContext = context;
        this.mVideoView = videoView;
        this.mAudioMediaPlayer = new MediaPlayer();
        this.mMusicMediaPlayer = new MediaPlayer();
        this.mCallBack = callBack;
        this.mMapDir = mapDir;
        //mMapDir = Environment.getExternalStorageDirectory().getPath() + "/jfitness";//外部储存
        mHandler = new Handler();

        /*
        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPause){
                    stop();
                }else {
                    restart();
                }
                isPause = !isPause;
            }
        });
        */
    }


    private void parserXML(final String xmlFilePath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser=factory.newSAXParser();
                    parser.parse(new File(xmlFilePath),new VideoHandler(xmlParserListener));
                }catch (Exception saxE){
                    saxE.printStackTrace();
                }

            }
        }).start();
    }




    /**
     * 计时线程，视频播放同时开始计时，每秒获取当前播放进度time,
     * 根据time，获取资源*/
    private class TimerThread extends Thread{
        private boolean isStop = true;
        public void stopMe(){
            isStop = false;
        }
        @Override
        public void run() {
            final long duration = mVideoView.getDuration();
            try {
                int time = mVideoView.getCurrentPosition()/1000;
                int atomId = mMapData.getPlaylist().getPlayElementList().get(mVideoPosition).getAtomId();
                Map<Integer, Atom> atomMap = mMapData.getAtomMap();
                Atom atom = atomMap.get(atomId);
                Map<Integer, Level> levelMap = atom.getLevelMap();
                Map<Integer, Event> eventMap = levelMap.get(mLevelId).getEventMap();
                Event event;
                StringBuilder stringBuilder = new StringBuilder();
                while ( isStop &&time < duration){
                    Log.d(TAG, "run: time = " + time + "\t duration = " + duration);
                    Log.d(TAG, "run: current = " + mVideoView.getCurrentPosition());
                    time = mVideoView.getCurrentPosition()/1000;

                    //------------
                    event = eventMap.get(time);
                    if (event ==null) {
                        Thread.sleep(1000);
                        time++;
                        continue;
                    }
                    stringBuilder.append(
                            "time = " + time +
                            "\t resistance = " + event.getResistance() +
                            "\t incline = " + event.getIncline());
                    mCallBack.updateSetting(event.getResistance(),event.getIncline());//回调设置参数

                    Source source;
                    if (event.getEventSource() != null){
                        stringBuilder.append("\t sourceId = " + event.getEventSource().getSourceId());
                        source = mSourceMap.get(event.getEventSource().getSourceId());
                        String sourceType = source.getType();

                        switch (sourceType){
                            case SOURCE_TYPE_TEXT:
                                int stringId = source.getId();
                                DescString descString = mDescStringMap.get(stringId);
                                final String description = descString.getDescMap().get(LanguageType.ZH_CN);//,默认英文
                                mCallBack.updateDescription(description);
                                break;
                            case SOURCE_TYPE_AUDIO:
                                try {
                                    String audioName = source.getSource();
                                    mAudioMediaPlayer.reset();
                                    String filePath = mMapDir + "/audio/" + audioName;
                                    File sourceFile = new File(filePath);
                                    if (sourceFile.exists()){
                                        //暂停背景音乐
                                        if (mMusicMediaPlayer.isPlaying()) {
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    setVolumeLow(mMusicMediaPlayer);
                                                    //mMusicMediaPlayer.pause();
                                                }
                                            });
                                        }

                                        mAudioMediaPlayer.setDataSource(filePath);
                                        mAudioMediaPlayer.prepare();
                                        mAudioMediaPlayer.start();
                                    }


                                }catch (IOException ioe){
                                    ioe.printStackTrace();
                                }

                                break;
                            default:
                                break;
                        }

                    }
                    Log.d(TAG, stringBuilder.toString());
                    stringBuilder.setLength(0);
                    //------------

                    Thread.sleep(1000);
                    time++;
                }
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    private void init(){
        //播放音乐逻辑
        try {
            if (mMusicList.size() > 0) {
                mMusicMediaPlayer.setDataSource(mMusicList.get(mMusicPosition));
                mMusicMediaPlayer.prepare();
                mMusicMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        setVolumeHigh(mp);
                        mp.start();
                    }
                });
                mMusicMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //播放下一首
                        try {
                            mMusicMediaPlayer.reset();
                            mMusicPosition++;
                            if (mMusicPosition >= mMusicList.size()){
                                mMusicPosition = 0;
                            }
                            mMusicMediaPlayer.setDataSource(mMusicList.get(mMusicPosition));
                            mMusicMediaPlayer.prepare();
                        }catch (IOException ioe){
                            ioe.printStackTrace();
                        }

                    }
                });
            }

        }catch (IOException ioe){
            ioe.printStackTrace();
        }


        //播放音效同时暂停背景音乐
        //音效完成继续播放背景音乐
        mAudioMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!mMusicMediaPlayer.isPlaying()) {
                    setVolumeHigh(mMusicMediaPlayer);
                    mMusicMediaPlayer.start();
                }
            }
        });


        //播放视频逻辑
        mVideoView.setVideoPath(mVideoNameList.get(mVideoPosition));
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                handleVideoCompletion(mp);
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
                if (mTimer != null){
                    mTimer.stopMe();
                }
                mTimer = new TimerThread();
                mTimer.start();
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                handleVideoCompletion(mp);
                return true;
            }
        });

    }

    private void handleVideoCompletion(MediaPlayer mp){
        mp.reset();
        mVideoPosition ++;
        if (mVideoPosition > mVideoNameList.size() - 1){
            Log.d(TAG, "onCompletion: 全部播放完成");
            if (mTimer != null){
                mTimer.stopMe();
            }
            //运动结束，跳转运动报告界面
            if (mCallBack != null) {
                mCallBack.playFinished();
            }
            onDestroy();
            return;
        }
        mVideoView.setVideoPath(mVideoNameList.get(mVideoPosition));
        if (mVideoPosition == 1){
            String name = mDescStringMap.get(mPlayList.getNameStringId()).getDescMap().get(LanguageType.EN_US);
            String location = mDescStringMap.get(mPlayList.getLocationStringId()).getDescMap().get(LanguageType.EN_US);
            //String locationDesc = mDescStringMap.get(mPlayList.getLocationDescriptionStringId()).getDescMap().get(LanguageType.EN_US);
            if (mCallBack != null) {
                mCallBack.updateDescription(name+" - " + location);
            }
        }
    }

    /**
     * 音量渐强*/
    private void setVolumeHigh(final MediaPlayer mediaPlayer){
        //渐强的时长，单位：毫秒；默认2秒
        final long duration = 2000;
        //音量调节的时间间隔
        long interval = duration / 10;
        mHighCountDownTimer = new CountDownTimer(duration, interval){
            @Override
            public void onTick(long millisUntilFinished) {
                final float volume = 1f - millisUntilFinished * 1.0f / duration;
                mediaPlayer.setVolume(volume, volume);
            }

            @Override
            public void onFinish() {
                mediaPlayer.setVolume(1f, 1f);
            }
        };
        mHighCountDownTimer.start();
    }

    /**
     * 音量渐弱*/
    private void setVolumeLow(final MediaPlayer mediaPlayer){
        //渐强的时长，单位：毫秒；默认2秒
        final long duration = 2000;
        //音量调节的时间间隔
        long interval = duration / 10;
        mLowCountDownTimer = new CountDownTimer(duration, interval){

            @Override
            public void onTick(long millisUntilFinished) {
                final float volume = millisUntilFinished * 1.0f / duration;
                mediaPlayer.setVolume(volume, volume);

            }

            @Override
            public void onFinish() {
                mediaPlayer.setVolume(0f, 0f);
                mediaPlayer.pause();

            }
        };
        mLowCountDownTimer.start();
    }

    /**
     * 开始*/
    public void startTask(){
        parserXML(mMapDir + "/settings.xml");
    }

    /**
     * 设置levelId*/
    public void setLevelId(int levelId){
        mLevelId = levelId;
        Log.d(TAG, "setLevelId: levelId is change to " + levelId);
    }


    /**
     * 暂停运动时停止播放*/
    public void pause(){
        if (mTimer != null) {
            mTimer.stopMe();
        }
        if (mVideoView.isPlaying()){
            mVideoView.pause();
        }
        if (mMusicMediaPlayer.isPlaying()) {
            setVolumeLow(mMusicMediaPlayer);//内部停止
        }
    }

    /**
     * 继续播放*/
    public void restart(){
        if (mTimer != null) {
            mTimer.stopMe();
            mTimer = new TimerThread();
            mTimer.start();
        }

        mVideoView.start();

        setVolumeHigh(mMusicMediaPlayer);
        mMusicMediaPlayer.start();

    }

    /**
     * 回收资源，结束计时线程*/
    public void onDestroy(){
        if (isReleased){
            return;
        }
        //结束计时线程.
        if (mTimer!= null) {
            mTimer.stopMe();
        }
        if (mHighCountDownTimer != null) {
            mHighCountDownTimer.cancel();
        }
        if (mLowCountDownTimer != null) {
            mLowCountDownTimer.cancel();
        }


        //回收资源
        if (mVideoView != null){
            if (mVideoView.isPlaying()){
                mVideoView.stopPlayback();
            }
        }

        if (mAudioMediaPlayer != null) {
            if (mAudioMediaPlayer.isPlaying()){
                mAudioMediaPlayer.stop();
                mAudioMediaPlayer.release();
            }
        }

        if (mMusicMediaPlayer != null){
            if (mMusicMediaPlayer.isPlaying()){
                mMusicMediaPlayer.stop();
                mMusicMediaPlayer.release();
            }
        }
        isReleased = true;
    }

    public interface CallBack{
        void updateDescription(String descString);//更新字幕
        void parserXMLFinished(MapData mapData);
        void playFinished();//
        void updateSetting(float resistance,float incline);//设置机器参数
    }
}
