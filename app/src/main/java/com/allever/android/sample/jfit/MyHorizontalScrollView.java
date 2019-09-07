package com.allever.android.sample.jfit;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class MyHorizontalScrollView extends HorizontalScrollView {
	
	private static final String TAG = "MyHorizontalScrollView";
	
	private Handler mHandler;
	
	private ScrollViewListener mScrollViewListener;
	
	/**
	 * 滚动状态:
	 * IDLE=滚动停止
	 * TOUCH_SCROLL=手指拖动滚动
	 * FLING=滚动
	 */
	public enum ScrollType{IDLE,TOUCH_SCROLL,FLING};
	
	/**
	 * 滚动方向
	 */
	public enum ScrollDirection{NONE,LEFT,RIGHT};
	
	/**
	 * 记录当前滚动的距离
	 */
	private int currentX = -9999999;
	
	/**
	 * 当前滚动状态
	 */
	private ScrollType scrollType = ScrollType.IDLE;
	private ScrollDirection scrollDirection = ScrollDirection.NONE;
	
	public interface ScrollViewListener {
		void onScrollChanged(ScrollType scrollType, ScrollDirection scrollDirection);
	}
	
	public void setScrollViewListener(ScrollViewListener listener) {
		mScrollViewListener = listener;
	}
	
	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHandler = new Handler();
	}
	
	/**
	 * 滚动监听runnable
	 */
	private Runnable scrollRunnable = new Runnable() {
		@Override
		public void run() {
			if (getScrollX()==currentX) {
				//滚动停止,取消监听线程
				scrollType = ScrollType.IDLE;
				if (mScrollViewListener!=null) {
					mScrollViewListener.onScrollChanged(scrollType, scrollDirection);
				}
				mHandler.removeCallbacks(this);
				return;
			} else {
				//手指离开屏幕,但是view还在滚动
				scrollType = ScrollType.FLING;
				if(mScrollViewListener!=null){
					mScrollViewListener.onScrollChanged(scrollType, scrollDirection);
				}
			}
			currentX = getScrollX();
			//滚动监听间隔:milliseconds
			mHandler.postDelayed(this, 50);
		}
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				this.scrollType = ScrollType.TOUCH_SCROLL;
				mScrollViewListener.onScrollChanged(scrollType, scrollDirection);
				mHandler.removeCallbacks(scrollRunnable);
				break;
			case MotionEvent.ACTION_UP:
				mHandler.post(scrollRunnable);
				break;
		}
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (oldl > l) {
			scrollDirection = ScrollDirection.RIGHT;
		}else{
			scrollDirection = ScrollDirection.LEFT;
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}
}