package com.allever.allsample.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by allever on 17-9-2.
 */

public final class  FileUtil {
    public static final String DIR_DOWNLOAD = Environment.DIRECTORY_DOWNLOADS;
    public static final String DIR_PICTURES = Environment.DIRECTORY_PICTURES;

    private static final String TAG = "FileUtil";
    public static final String getNameFromUrl(String url){
        String name = url.substring(url.lastIndexOf("/")).split("/")[1];//不带"/"
        Log.d(TAG, "getNameFromUrl:  = " + name);
        return name;
    }

    public static final String getExternalDownloadDir(){
        String downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
        Log.d(TAG, "getExternalDownloadDir: = " + downloadDir);
        return downloadDir;
    }

    public static final String getExternalDir(String dirType){
        String dir = Environment.getExternalStoragePublicDirectory(dirType).getPath() + "/";
        Log.d(TAG, "getExternalDir:  = " + dir);
        return dir;
    }

    /**
     * @param absPath 文件路径*/
    public static final boolean isExistFile(String absPath){
        File file = new File(absPath);
        if (file.exists()) return true;
        else return false;
    }

    /*
    public static final boolean createFile(String absPath){
        String dir = absPath.substring(0,absPath.lastIndexOf("/")) + "/";
        String name = getNameFromUrl(absPath);
        File file = new File(dir + name);
        if (!file.exists()){
            Log.d(TAG, "createFile: dir = " + dir);
            File fileDir = new File(dir);
            fileDir.mkdirs();
            file = new File(dir + name);
            if (file.exists()) Log.d(TAG, "createFile: fileCreated!");
            else Log.d(TAG, "createFile: fileNotCreate!");
        }else {
            return true;
        }
        return false;
    }
    */


    public static void createDir(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
    }




    public static List<String> getSelectedTypeFile(String fileAbsolutePath,String fileType) {
        List<String> musicList = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP4结尾
                if (filename.trim().toLowerCase().endsWith("." + fileType)) {
                    Log.d(TAG, "getMusicFile: " +fileAbsolutePath+"/" + filename);
                    musicList.add(fileAbsolutePath+"/" + filename);
                }
            }
        }
        return musicList;
    }

}
