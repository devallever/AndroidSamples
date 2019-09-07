package com.allever.allsample.util;

import android.view.View;

/**
 * Created by allever on 17-8-5.
 */

public class ViewUtil {



    public static int measureHeight(int measureSpec){
        int result = 200;//默认高度200px
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);

        switch (specMode){
            case View.MeasureSpec.EXACTLY:
                result = size;
                break;
            case View.MeasureSpec.AT_MOST:
                result = Math.min(result,size);//取默认值和测量值小的那一个
                //result = size;
                break;
            case View.MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        return result;
    }


    public static int measureWidth(int measureSpec){
        int result = 200;//默认宽度200px
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);

        switch (specMode){
            case View.MeasureSpec.EXACTLY:
                result = size;
                break;
            case View.MeasureSpec.AT_MOST:
                result = Math.min(result,size);//取默认值和测量值小的那一个
                //result = size;
                break;
            case View.MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        return result;
    }
}
