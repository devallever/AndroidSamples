package com.allever.mysimple.decoratorPattern;

import android.util.Log;

/**
 * Created by Allever on 2016/11/19.
 */

public class ExpensiveCloth extends PersonCloth {

    private static final String TAG = "ExpensiveCloth";

    public ExpensiveCloth(Person person){
        super(person);
    }

    private void dressArmour(){
        Log.d(TAG, "我穿了昂贵的铠甲");
    }

    @Override
    public void dressed() {
        super.dressed();
        dressArmour();
    }
}
