package com.allever.mysimple.observerPattern;

import android.util.Log;

/**
 * Created by Allever on 2016/11/20.
 */

public class NBAObserver extends Observer {
    private static final String TAG = "NBAObserver";
    public NBAObserver(String name, Subject subject){
        super(name, subject);
    }

    @Override
    public void update() {
        Log.d(TAG, subject.getSubjectAction() + "，" + name + " , " + "关闭NBA, 继续工作");
    }
}
