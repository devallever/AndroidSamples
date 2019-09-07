package com.allever.mysimple.observerPattern;

import android.util.Log;

/**
 * Created by Allever on 2016/11/20.
 */

public class TVObserver extends Observer {
    private static final String TAG = "TVObserver";
    public TVObserver(String name, Subject subject){
        super(name, subject);
    }

    @Override
    public void update() {
        Log.d(TAG, subject.getSubjectAction() + "，" + name + " , " + "关闭电视剧, 继续工作");
    }
}
