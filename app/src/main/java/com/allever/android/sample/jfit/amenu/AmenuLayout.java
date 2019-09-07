package com.allever.android.sample.jfit.amenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.allever.android.sample.customview.linecharview.BezierUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 弧形菜单布局控件
 * Created by CHARWIN.
 */

public class AmenuLayout extends ViewGroup implements View.OnClickListener {
	
	public static final int MSG_REQUEST_LAYOUT = 0x009;
//	public OnAmenuItemClickListener mOnAmenuItemClickListener;
	private int mTotalWidth,
			mTotalHeight,
			mDownY,
//			mOffsetY,
//			mOffsetYLast,
			mCenterY,
			mBlockX,
			mArcX,
//			mMaxScrollY,
			mChildHeight, mChildWidth;
	private static final int MAX_DISPLAY_COUNT = 3; // 最大可视数量
	private int mOffsetYTarget;
	private int mSpeedY = 30;
	private boolean mAutoScroll = false;
	private int mSelectedId = -1;
//	private int mTopIndex = 0; // 可视界面上第一个view的索引
	private int mPage = 1; // 1起步
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
	private int mDisplayCount;
	
	public AmenuLayout(Context context) {
		super(context);
	}
	
	public AmenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public AmenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public AmenuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
		mDisplayCount = count > MAX_DISPLAY_COUNT ? MAX_DISPLAY_COUNT : count;
		if (count > 0) {
			mChildHeight = mTotalHeight / mDisplayCount;
//			mMaxScrollY = -(mChildHeight * count - mChildHeight * displayCount);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int pageCount = getPageCount();
		if (mPage > pageCount) {
			mPage = pageCount;
		}
		int cCount = getChildCount();
		int indexTop = (mPage - 1) * MAX_DISPLAY_COUNT;
		
		mDisplayCount = cCount - indexTop;
		if (mDisplayCount > MAX_DISPLAY_COUNT) {
			mDisplayCount = MAX_DISPLAY_COUNT;
		}
		if (mDisplayCount > 0) {
			mChildHeight = mTotalHeight / mDisplayCount;
		}
		
		int indexBottom = (indexTop + mDisplayCount) - 1;
		
		if (!mAnimLayout) {
			for (int i = 0; i < cCount; i++) {
				View childView = getChildAt(i);
				if (i == indexTop) {
					if (mSelectedId != childView.getId()) {
						if (mOnAmenuListener != null) {
							mOnAmenuListener.onAmenuItemSelected(childView);
						}
					}
					mSelectedId = childView.getId();
				}
				
				if (childView instanceof AmenuItemView) {
					((AmenuItemView) childView).setTextColor(childView.getId() == mSelectedId ? COLOR_MENU_TEXT_SELECTED : COLOR_MENU_TEXT);
				}
				
				if (i >= indexTop && i <= indexBottom) {
					int cl = 0, ct = 0, cr = 0, cb = 0;
					
					ct = (i - indexTop) * mChildHeight;
					cb = ct + mChildHeight;
					
					float h = ct + mChildHeight / 2;
					float hPercent = h / mTotalHeight;
					PointF p = BezierUtil.calculateBezierPointForQuadratic(hPercent, new PointF(mBlockX, 0), new PointF(-mArcX, mCenterY), new PointF(mBlockX, mTotalHeight));
					
					cl = (int) p.x;
					cr = cl + mChildWidth;
					
					childView.layout(cl, ct, cr, cb);
				} else {
					int k = mTotalHeight + 10;
					childView.layout(0, k, k, 0);
				}
			}
			if (mOnAmenuListener != null) {
				mOnAmenuListener.onAmenuRefresh();
			}
		}else {
			mDisplayPositions.clear();
			for (int i = 0; i < cCount; i++) {
				if (i >= indexTop && i <= indexBottom) {
					int cl = 0, ct = 0, cr = 0, cb = 0;
					
					ct = (i - indexTop) * mChildHeight;
					cb = ct + mChildHeight;
					
					float h = ct + mChildHeight / 2;
					float hPercent = h / mTotalHeight;
					PointF p = BezierUtil.calculateBezierPointForQuadratic(hPercent, new PointF(mBlockX, 0), new PointF(-mArcX, mCenterY), new PointF(mBlockX, mTotalHeight));
					
					cl = (int) p.x;
					cr = cl + mChildWidth;
					mDisplayPositions.put(i, new Integer[]{cl, ct, cr, cb});
				}
			}
			
			for (int i = 0; i < cCount; i++) {
				if (mDisplayPositions.containsKey(i)) {
					Integer[] p = mDisplayPositions.get(i);
					getChildAt(i).layout(p[0], p[1], p[2], p[3]);
				} else {
					getChildAt(i).layout(0, mTotalHeight + 10, 0, mTotalHeight + 10);
				}
			}
		}
	}
	private int mIndexAdd = -1;
	private boolean mAnimLayout = false;
	private List<Integer> mDisplayIndexs = new ArrayList<>();
	private Map<Integer, Integer[]> mDisplayPositions = new HashMap<>();
	
	
	public void addWithAnim(AmenuData data, int displayIndex){
		
		View v = findViewById(data.getId());
		if (v != null) {
			return;
		}
		addData(data, -1);
		
//		mAnimLayout = true;
//		int indexTop = (mPage - 1) * MAX_DISPLAY_COUNT;
//		mIndexAdd = indexTop + displayIndex;
		
//		addData(data, mIndexAdd); // addView会自动隐性调用onLayout
		
//		int lastIndex = mIndexAdd+1;
//
//		int count = getChildCount();
//		for (int i = 0; i < count; i++) {
//			View v = getChildAt(i);
//			int targetY = 0;
//			if (mDisplayPositions.containsKey(i)) {
//				Integer[] ps = mDisplayPositions.get(i);
//				targetY = ps[1];
//				if (i == mIndexAdd){
//					v.layout(mTotalWidth, ps[1], mTotalWidth + mChildWidth, ps[3]);
//				}
//			}else if (i == lastIndex && !mDisplayPositions.containsKey(lastIndex)) {
//				targetY = mTotalHeight;
//			}else{
//				continue;
//			}
//
//			float h = targetY + mChildHeight / 2;
//			float hPercent = h / mTotalHeight;
//			PointF p = getPoint(hPercent);
//
//			TranslateAnimation translateAnimation = new TranslateAnimation(0,
//					p.x - v.getLeft(),
//
//					0,
//					targetY - v.getTop());
//			translateAnimation.setDuration(1000);
//			v.startAnimation(translateAnimation);
//		}
	}
	
	public void delWithAnim(AmenuData data){
		View v = findViewById(data.getId());
		if (v != null) {
			removeView(v);
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
//					int y = (int) (event.getY() - mDownY);
//					if (y >= 0) { // 到顶部
//						mOffsetY = mOffsetYLast = 0;
//						mDownY = (int) event.getY();
//					} else if (y <= mMaxScrollY) { // 到底部
//						mOffsetY = mOffsetYLast = mMaxScrollY;
//						mDownY = (int) event.getY();
//					} else {
//						mOffsetY = y;
//					}
//					requestLayout();
				}
				//break;
				return true;

			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL: // 滑出控件区域时（或作为listItem时，受外部事件影响）
				getParent().requestDisallowInterceptTouchEvent(false); // 取消阻止父层的View截获touch事件
				if (mIsScroll) {
					int count = getChildCount();
					int pageMax = count / MAX_DISPLAY_COUNT;
					if (count % MAX_DISPLAY_COUNT != 0) {
						pageMax++;
					}
					
					int page = mPage;
					if (event.getY() > mDownY){ // 下拉
						page--;
					}else{ // 上拉
						page++;
					}
					if (page < 1){
						page = 1;
					} else if (page > pageMax) {
						page = pageMax;
					}
					
					if (page != mPage){
						mPage = page;
						if (mOnAmenuListener != null){
							int indexTop = (mPage-1) * MAX_DISPLAY_COUNT;
							boolean canUp = mPage > 1;
							boolean canDown = (mPage * MAX_DISPLAY_COUNT) < count;
							if (mOnAmenuListener.onAmenuRequestChange(indexTop, mPage, pageMax, canUp, canDown)) {
								requestLayout();
							}
						}
					}
					return true;
				}
				break;

			default:
				break;
		}
		return super.dispatchTouchEvent(event);
	}
	
//	private void calOffsetYTarget() {
//		if (mChildHeight > 0 && mOffsetY != 0) {
//			int count = mOffsetY / mChildHeight;
//			int yu = mOffsetY % mChildHeight;
//			if (Math.abs(yu) > mChildHeight / 2) {
//				count--;
//			}
//			mOffsetYTarget = count * mChildHeight;
//			Log.i("计算偏移Y", count + "  ,  " + mOffsetY + "  ,  " + mOffsetYTarget + "  ,  " + mChildHeight + "  ,  " + yu);
//		} else {
//			mOffsetYTarget = 0;
//		}
//	}
	
	public void upateData(AmenuData data) {
		View v = findViewById(data.getId());
		if (v != null && v instanceof AmenuItemView) {
			((AmenuItemView) v).updateData(data.getTitle(), data.getValue(), data.getUnit());
		}
	}
	
	private void addData(AmenuData data, int index) {
		AmenuItemView view = new AmenuItemView(getContext());
		view.setId(data.getId());
		view.setData(data.getTitle(), data.getValue(), data.getUnit());
		view.setTag(data);
		view.setOnClickListener(this);
		
		if (index >= 0) {
			addView(view, index);
		}else{
			addView(view);
		}
	}
	
	public void setDatas(List<AmenuData> datas) {
		removeAllViews();
		for (int i = 0; i < datas.size(); i++) {
			addData(datas.get(i), -1);
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
			if (mOnAmenuListener != null) {
				mOnAmenuListener.onAmenuItemClick(view);
				mOnAmenuListener.onAmenuItemSelected(view);
			}
		}
	}
	
	private OnAmenuListener mOnAmenuListener;
	public interface OnAmenuListener{
		boolean onAmenuRequestChange(int indexTop, int targetPage, int maxPage, boolean canUp, boolean canDown);
		void onAmenuItemSelected(View view);
		void onAmenuItemClick(View view);
		void onAmenuRefresh();
	}
	
	public void setOnAmenuListener(OnAmenuListener listener) {
		this.mOnAmenuListener = listener;
	}

	
	/**
	 * 当前页码（从1开始）
	 * @return
	 */
	public int getPageNow() {
		return mPage;
	}
	
	/**
	 * 总页数
	 * @return
	 */
	public int getPageCount(){
		int count = getChildCount();
		if (count > 0) {
			int pageMax = count / MAX_DISPLAY_COUNT;
			if (count % MAX_DISPLAY_COUNT != 0) {
				pageMax++;
			}
			return pageMax;
		}
		return 0;
	}

	private PointF getPoint(float yPercent){
		return BezierUtil.calculateBezierPointForQuadratic(yPercent, new PointF(mBlockX, 0), new PointF(-mArcX, mCenterY), new PointF(mBlockX, mTotalHeight));
	}
}
