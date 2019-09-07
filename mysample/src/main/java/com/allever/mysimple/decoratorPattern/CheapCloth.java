package com.allever.mysimple.decoratorPattern;

import android.util.Log;

/**
 * Created by Allever on 2016/11/19.
 */

public class CheapCloth extends PersonCloth {
    private static final String TAG = "CheapCloth";
    public CheapCloth(Person person){
        super(person);
    }

    private void dressLight(){
        Log.d(TAG, "我穿了便宜的衣服");
    }

    @Override
    public void dressed() {
        super.dressed();
        dressLight();
    }
}
