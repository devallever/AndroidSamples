package com.allever.android.sample.jfit.amenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.allever.android.sample.customview.linecharview.BezierUtil;

import java.util.List;

/**
 * 弧形菜单布局控件
 * Created by CHARWIN.
 */

public class AmenuLayoutV1 extends ViewGroup implements View.OnClickListener {
	
	public static final int MSG_REQUEST_LAYOUT = 0x009;
	public OnAmenuItemClickListener mOnAmenuItemClickListener;
	private int mTotalWidth,
			mTotalHeight,
			mDownY,
			mOffsetY,
			mOffsetYLast,
			mCenterY,
			mBlockX,
			mArcX,
			mMaxScrollY,
			mChildHeight, mChildWidth;
	private int mMaxDisplayCount = 3; // 最大可视数量
	private int mOffsetYTarget;
	private int mSpeedY = 30;
	private boolean mAutoScroll = false;
	private int mSelectedId = -1;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MSG_REQUEST_LAYOUT:
					requestLayout();
					break;
			}
		}
	};
	private boolean mIsScroll = false;
	private final static int COLOR_MENU_TEXT = 0xff000000;
	private final static int COLOR_MENU_TEXT_SELECTED = 0xffffffff;
	
	public AmenuLayoutV1(Context context) {
		super(context);
	}
	
	public AmenuLayoutV1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public AmenuLayoutV1(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public AmenuLayoutV1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalWidth = w;
		mTotalHeight = h;
		mArcX = (int) (mTotalWidth * 0.27f);
		mCenterY = mTotalHeight / 2;
		//mBlockX = (int)((mTotalWidth - mArcX) * 0.25f + mArcX);
		mBlockX = mArcX;
		mChildWidth = mTotalWidth - mArcX;
		
		int count = getChildCount();
		int displayCount = count > mMaxDisplayCount ? mMaxDisplayCount : count;
		if (count > 0) {
			mChildHeight = mTotalHeight / displayCount;
			mMaxScrollY = -(mChildHeight * count - mChildHeight * displayCount);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int cCount = getChildCount();
		int cWidth = 0;
		int cHeight = 0;

//		int h = getHeight();
//		int w = getWidth();
		
		/**
		 * 遍历所有childView根据其宽和高，以及margin进行布局
		 */
		for (int i = 0; i < cCount; i++) {
			View childView = getChildAt(i);
			if (mSelectedId == -1) {
				mSelectedId = childView.getId();
			}
			
			if (childView instanceof AmenuItemView) {
				((AmenuItemView) childView).setTextColor(childView.getId() == mSelectedId ? COLOR_MENU_TEXT_SELECTED : COLOR_MENU_TEXT);
			}
			cWidth = childView.getMeasuredWidth();
			cHeight = childView.getMeasuredHeight();
			
			
			int cl = 0, ct = 0, cr = 0, cb = 0;
			
			ct = i * mChildHeight + mOffsetY;
			cb = ct + mChildHeight;
			
			float h = ct + mChildHeight / 2;
			float hPercent = h / mTotalHeight;
			PointF p = BezierUtil.calculateBezierPointForQuadratic(hPercent, new PointF(mBlockX, 0), new PointF(-mArcX, mCenterY), new PointF(mBlockX, mTotalHeight));
			
			cl = (int) p.x;
			cr = cl + mChildWidth;
			
			childView.layout(cl, ct, cr, cb);
		}
		
		if (mAutoScroll) {
			autoScroll();
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mAutoScroll = false;
				mIsScroll = false;
				mDownY = (int) event.getY();
				//getParent().requestDisallowInterceptTouchEvent(true); // 阻止父层的View截获touch事件
				break;
			
			case MotionEvent.ACTION_MOVE:
				mAutoScroll = false;
				if (Math.abs(event.getY() - mDownY) > 25) {
					getParent().requestDisallowInterceptTouchEvent(true); // 阻止父层的View截获touch事件
					mIsScroll = true;
					int y = (int) (event.getY() - mDownY) + mOffsetYLast;
					if (y >= 0) { // 到顶部
						mOffsetY = mOffsetYLast = 0;
						mDownY = (int) event.getY();
					} else if (y <= mMaxScrollY) { // 到底部
						mOffsetY = mOffsetYLast = mMaxScrollY;
						mDownY = (int) event.getY();
					} else {
						mOffsetY = y;
					}
					requestLayout();
				}
				//break;
				return true;
			
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL: // 滑出控件区域时（或作为listItem时，受外部事件影响）
				getParent().requestDisallowInterceptTouchEvent(false); // 取消阻止父层的View截获touch事件
				if (mIsScroll) {
					mOffsetYLast = mOffsetY;
					mAutoScroll = true;
					mIsScroll = false;
					calOffsetYTarget();
					requestLayout();
					return true;
				}
				break;
			
			default:
				break;
		}
		return super.dispatchTouchEvent(event);
	}
	
	private void calOffsetYTarget() {
		if (mChildHeight > 0 && mOffsetY != 0) {
			int count = mOffsetY / mChildHeight;
			int yu = mOffsetY % mChildHeight;
			if (Math.abs(yu) > mChildHeight / 2) {
				count--;
			}
			mOffsetYTarget = count * mChildHeight;
			Log.i("计算偏移Y", count + "  ,  " + mOffsetY + "  ,  " + mOffsetYTarget + "  ,  " + mChildHeight + "  ,  " + yu);
		} else {
			mOffsetYTarget = 0;
		}
	}
	
	public void upateData(AmenuData data) {
		View v = findViewById(data.getId());
		if (v != null && v instanceof AmenuItemView) {
			((AmenuItemView) v).updateData(data.getTitle(), data.getValue(), data.getUnit());
		}
	}
	
	public void addData(AmenuData data) {
		AmenuItemView view = new AmenuItemView(getContext());
		view.setId(data.getId());
		view.setData(data.getTitle(), data.getValue(), data.getUnit());
		view.setOnClickListener(this);
		
		addView(view);
	}
	
	public void setDatas(List<AmenuData> datas) {
		removeAllViews();
		for (int i = 0; i < datas.size(); i++) {
			addData(datas.get(i));
		}
	}
	
	@Override
	public void onClick(View view) {
		if (view instanceof AmenuItemView) {
			View last = findViewById(mSelectedId);
			if (last != null) {
				if (last.getId() != view.getId()) {
					if (last instanceof AmenuItemView) {
						((AmenuItemView) last).setTextColor(COLOR_MENU_TEXT);
					}
				}else{
					return;
				}
			}
			mSelectedId = view.getId();
			((AmenuItemView) view).setTextColor(COLOR_MENU_TEXT_SELECTED);
			if (mOnAmenuItemClickListener != null) {
				mOnAmenuItemClickListener.onAmenuItemClick(view);
			}
		}
	}
	
	public void autoScroll() {
		if (mOffsetY != mOffsetYTarget) {
			int diffY = Math.abs(Math.abs(mOffsetY) - Math.abs(mOffsetYTarget)); // 差距
			
			int change = diffY > mSpeedY ? mSpeedY : diffY / 2; // 每次偏移量
			if (change <= 0) {
				change = 1;
			}
			
			if (mOffsetY > mOffsetYTarget) {
				mOffsetY -= change;
			} else {
				mOffsetY += change;
			}
			mOffsetYLast = mOffsetY;
			mHandler.sendEmptyMessage(MSG_REQUEST_LAYOUT);
		} else {
			mAutoScroll = false;
		}
	}
	
	public void setOnAmenuItemClickListener(OnAmenuItemClickListener listener) {
		mOnAmenuItemClickListener = listener;
	}
	
	public interface OnAmenuItemClickListener {
		void onAmenuItemClick(View view);
	}
}
