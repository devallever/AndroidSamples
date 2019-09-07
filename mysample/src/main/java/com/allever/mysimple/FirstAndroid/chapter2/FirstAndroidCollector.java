package com.allever.mysimple.FirstAndroid.chapter2;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2017/3/1.
 */

public class FirstAndroidCollector {
    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void removeAll(){
        for (Activity activity: activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
