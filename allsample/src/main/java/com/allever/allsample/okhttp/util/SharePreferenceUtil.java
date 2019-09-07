package com.allever.allsample.okhttp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.allever.allsample.MyApplication;

/**
 * Created by allever on 17-6-29.
 */

public class SharePreferenceUtil {
    public static void saveSessionId(String username, String session_id){
        SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session_id",session_id);
        editor.putString("username", username);
        editor.apply();
    }

    public static String getSessionId(){
        SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("session_id","");
    }

    public static String getUsername(){
        SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return sharedPreferences.getString("username","");
    }
}
