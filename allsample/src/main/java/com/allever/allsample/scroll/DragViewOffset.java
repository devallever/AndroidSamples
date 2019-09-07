package com.allever.allsample.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by allever on 17-8-12.
 */

public class DragViewOffset extends View {
    private float mLastX;
    private float mLastY;
    public DragViewOffset(Context context, AttributeSet attributeSet){
        super(context,attributeSet,0);
    }
    public DragViewOffset(Context context, AttributeSet attributeSet, int deftStyle){
        super(context,attributeSet,deftStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rawX = event.getRawX();//
        float rawY = event.getRawY();
        //使用getX/Y方法移动不够灵敏
        //float x = event.getX();
        //float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = rawX;
                mLastY = rawY;
                //mLastX = x;
                //mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //偏移量
                float offsetX = rawX - mLastX;
                float offsetY = rawY - mLastY;
                //float offsetX = x - mLastX;
                //float offsetY = y - mLastY;
                offsetLeftAndRight((int)offsetX);
                offsetTopAndBottom((int)offsetY);
                //重置初始坐标
                mLastX = rawX;
                mLastY = rawY;
                //mLastX = x;
                //mLastY = y;
                break;
        }
        return true;//拦截，不上交
    }
}
