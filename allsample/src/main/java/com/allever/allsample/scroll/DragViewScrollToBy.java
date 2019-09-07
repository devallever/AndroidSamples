package com.allever.allsample.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by allever on 17-8-12.
 */

public class DragViewScrollToBy extends View {
    private float mLastX;
    private float mLastY;
    public DragViewScrollToBy(Context context, AttributeSet attributeSet){
        super(context,attributeSet,0);
    }
    public DragViewScrollToBy(Context context, AttributeSet attributeSet, int deftStyle){
        super(context,attributeSet,deftStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rawX = event.getRawX();//
        float rawY = event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = rawX;
                mLastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                //偏移量
                float offsetX = rawX - mLastX;
                float offsetY = rawY - mLastY;

                ((View)getParent()).scrollBy(-(int)offsetX,-(int)offsetY);
                //((View)getParent()).scrollTo(-(int)event.getRawX(),-(int)event.getRawY());不行
                //((View)getParent()).scrollBy((int)getX(),(int)getY());//不行
                //重置初始坐标
                mLastX = rawX;
                mLastY = rawY;
                break;
        }
        return true;//拦截，不上交
    }
}
