package com.allever.allsample.scroll;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by allever on 17-8-12.
 */

public class DragViewGroup extends FrameLayout {
    private static final String TAG = "DragViewGroup";

    private Context mContext;
    private ViewDragHelper mViewDragHelper;

    private View mMainView;
    private View mMenuView;

    private int mWidth;

    //最关键的实现，
    private ViewDragHelper.Callback mViewDragHelperCallback = new ViewDragHelper.Callback() {
        //指定在创建ViewDragHelper时，参数parentView中，的哪一个子View可以被移动。
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //这样只有MainView可以被拖动
            return mMainView == child;
            //return false; //默认返回
        }

        /**
         * 要实现滑动，这两个方法必须重写
         * 默认返回值为0，表示不滑动
         * 只重写其中一个，只能在某一方向上滑动*/
        //具体滑动方法--垂直
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //return super.clampViewPositionVertical(child, top, dy); //0
            return 0;
        }

        //具体滑动方法--水平
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //return super.clampViewPositionHorizontal(child, left, dx);  //0
            Log.d(TAG, "clampViewPositionHorizontal: left = " + left);
            //向右滑动left>0, 向左滑动则小于0，
            //只允许向右滑动显示菜单，左滑动不超出右边界。
            if (left > 0){
                if(left>mWidth){
                    return mWidth;
                }else {
                    return left;
                }
            }
            else return 0;
        }

        //拖动结束后调用
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //手指抬起后缓慢移动到指定位置
            //让MainView移动后左边距小于500px时，将MainView还原到最初状态。
            //这里可以看出，MainView和MenuView是重叠在一块的。最初显示的是MainView，MenuView则是在下面
            if (mMainView.getLeft() < (mWidth/2)){
                //关闭菜单
                //相当于Scroller的startScroll方法
                mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }else {
                //打开菜单
                mViewDragHelper.smoothSlideViewTo(mMainView,mWidth,0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }
        }
    };
    public DragViewGroup(Context context){
        super(context);
        initView(context);
    }

    public DragViewGroup(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        initView(context);
    }

    public DragViewGroup(Context context,AttributeSet attributeSet, int deftStyle){
        super(context, attributeSet, deftStyle);
        initView(context);
    }


    private void initView(Context context){
        mContext = context;
        mViewDragHelper = ViewDragHelper.create(this,mViewDragHelperCallback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //这就确定了DragViewGroup中的布局的先后添加顺序
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
/*        mMenuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭菜单
                //相当于Scroller的startScroll方法
                mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }
        });*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取MenuView的宽度，处理滑动距离
        mWidth = mMenuView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    //将事件传递给VIewDragHelper处理，必须重写。并调用mViewDragHelper.processTouchEvent(event);
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }


    //ViewDragHelper内部使用scroller实行滑动
    //这是模板代码
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
