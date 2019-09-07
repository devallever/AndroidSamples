package com.allever.mysimple.observerPattern;

import android.icu.lang.UScript;
import android.util.Log;

/**
 * Created by Allever on 2016/11/20.
 * 具体观察者：股票同事
 */

public class StockObserver extends Observer {

    private static final String TAG = "StockObserver";

    public StockObserver(String name, Subject subject){
        super(name,subject);
    }

    @Override
    public void update() {
        Log.d(TAG, subject.getSubjectAction() + "，" + name + " , " + "关闭股票行情,继续工作");
    }
}
