package com.allever.allsample.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by allever on 17-8-12.
 */

public class DragViewLayoutParams extends View {
    private float mLastX;
    private float mLastY;
    public DragViewLayoutParams(Context context, AttributeSet attributeSet){
        super(context,attributeSet,0);
    }
    public DragViewLayoutParams(Context context, AttributeSet attributeSet, int deftStyle){
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
/*                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.leftMargin = (int)(getLeft() + offsetX);
                params.topMargin = (int)(getTop() + offsetY);
                setLayoutParams(params);*/


                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
                params.leftMargin = (int)(getLeft() + offsetX);
                params.topMargin = (int)(getTop() + offsetY);
                setLayoutParams(params);
                //重置初始坐标
                mLastX = rawX;
                mLastY = rawY;
                break;
        }
        return true;//拦截，不上交
    }
}
