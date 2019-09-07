package com.allever.allsample.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by allever on 17-8-12.
 */

public class DragViewScroller extends View {

    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public DragViewScroller(Context context) {
        super(context);
        ininView(context);
    }

    public DragViewScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        ininView(context);
    }

    public DragViewScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ininView(context);
    }

    private void ininView(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开时，执行滑动过程
                View viewGroup = ((View) getParent());
                mScroller.startScroll(
                        viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),
                        -viewGroup.getScrollY());
                invalidate();
                break;
        }
        return true;
    }



    /***我写的，手指离开后不返回原位。
    private float mLastX;
    private float mLastY;
    private Scroller mScroller;
    public DragViewScroller(Context context, AttributeSet attributeSet){
        this(context,attributeSet,0);
        initView(context);

    }
    public DragViewScroller(Context context, AttributeSet attributeSet, int deftStyle){
        super(context,attributeSet,deftStyle);
        initView(context);
        //mScroller = new Scroller(context);
    }
    private void initView(Context context) {
        // 初始化Scroller
        mScroller = new Scroller(context);
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
                ((View) getParent()).scrollBy(-(int)offsetX, -(int)offsetY);
                //重置初始坐标
                mLastX = rawX;
                mLastY = rawY;
                //mLastX = x;
                //mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                View parent = (View)getParent();
                mScroller.startScroll(
                        parent.getScrollX(),
                        parent.getScrollY(),
                        -parent.getScrollX(),
                        -parent.getScrollY()
                );
                invalidate();
                break;
        }
        return true;//拦截，不上交
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY()
            );
            invalidate();
        }
    }
    */
}
