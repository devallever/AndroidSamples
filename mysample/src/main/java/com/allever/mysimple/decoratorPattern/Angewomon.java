package com.allever.mysimple.decoratorPattern;

import android.util.Log;

/**
 * Created by Allever on 2016/11/19.
 */

public class Angewomon extends Person {
    private static final String TAG = "Angewomon";
    @Override
    public void dressed() {
        Log.d(TAG, "现在我只穿了最基本的内衣内裤");
        //System.out.println("现在我只穿了最基本的内衣内裤");
    }
}
