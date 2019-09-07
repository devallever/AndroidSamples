package com.allever.mysimple.imageloader;

/**
 * Created by Allever on 2016/11/24.
 */

public class Utils {
    public static String getImageName(String url){
        String imageName = "";
        String[] temp = url.split("./");
        imageName = temp[temp.length-1];
        return imageName;
    }
}
