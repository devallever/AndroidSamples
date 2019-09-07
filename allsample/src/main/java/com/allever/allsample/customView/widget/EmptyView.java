package com.allever.allsample.customView.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.allever.allsample.util.ViewUtil;

/**
 * Created by allever on 17-8-5.
 */

public class EmptyView extends View {
    private static final String TAG = "EmptyView";
    public EmptyView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(ViewUtil.measureWidth(widthMeasureSpec), ViewUtil.measureHeight(heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //追踪当前事件的速度
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        //获取当前速度,单位ms
        velocityTracker.computeCurrentVelocity(1000);//计算速度:一段时间内手指滑过的像素数:
        int xVelocity = (int)velocityTracker.getXVelocity();
        int yVelocity = (int)velocityTracker.getYVelocity();
        Log.d(TAG, "onTouchEvent: xV = " + xVelocity);
        Log.d(TAG, "onTouchEvent: yV = " + yVelocity);

        //回收
        velocityTracker.clear();
        velocityTracker.recycle();
        return true;
    }
}
