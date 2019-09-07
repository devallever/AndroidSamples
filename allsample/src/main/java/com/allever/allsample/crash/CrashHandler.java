package com.allever.allsample.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ShortcutInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by allever on 17-11-2.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/crash/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";


    private CrashHandler(){}

    public void init(Context context){
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }



    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            //导出crash到sdcard
            dumpExceptionToSDCard(throwable);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        throwable.printStackTrace();
        if (mDefaultExceptionHandler != null){
            mDefaultExceptionHandler.uncaughtException(thread,throwable);
        }else {
            Process.killProcess(Process.myPid());
        }


    }

    private void dumpExceptionToSDCard(Throwable throwable) throws IOException{
        //检查sdcard是否可用
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            if (DEBUG){
                Log.w(TAG, "dumpExceptionToSDCard: sdcard unmounted");
                return;
            }
        }

        File crashPath = new File(PATH);
        if (!crashPath.exists()){
            crashPath.mkdirs();
            Log.d(TAG, "dumpExceptionToSDCard: dir not exist");
        }

        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date(current));
        File crashFile = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(crashFile)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            throwable.printStackTrace(pw);
            pw.close();
        }catch (Exception e){
            Log.e(TAG, "dumpExceptionToSDCard: ",e);
            Log.e(TAG, "dumpExceptionToSDCard: dump crash info fail");
        }


    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException{
        PackageManager pm = mContext.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
        //App版本
        pw.println("App Version: " + packageInfo.versionName + "_" + packageInfo.versionCode);
        //系统版本
        pw.println("OS Version: " + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        //手机制造商
        pw.println("Vender: " + Build.MANUFACTURER);
        //手机型号
        pw.println("Model: " + Build.MODEL);
        //CPU架构
        pw.println("CPU ABI: " + Build.CPU_ABI);

    }


    public static CrashHandler getInstance(){
        return CrashHandlerHelper.INSTANCE;
    }

    private static class CrashHandlerHelper{
        private static CrashHandler INSTANCE = new CrashHandler();
    }
}
