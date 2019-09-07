package com.allever.android.sample.jfit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.allever.android.sample.R;

import java.util.UUID;

/**
 * 横向滑动选择器
 *
 * Created by CHARWIN.
 */

public class HSlider extends View {
	public static final String TAG = "HSlider";
	
	private Paint mPaintText, mPaintTextLight;
	private int mTotalWidth, mTotalHeight;
	private int mTextColor, mTextLightColor;
	private int mTextSize, mTextLightSize;
	private int mBlockCount = 5; // 板块数量
	private int mBlockWidth; // 板块平均大小
	private int mTotalWidthHalf, mBlockWidthHalf; // 1/2
	private int mOffsetX = 0; // 横向偏移量
	private int mDownX;
	private int mSpeedX = 30; // 速度
	private boolean mAutoScroll = false; // 自动滚动
	private int mCenterXMin, mCenterXMax;
	private int mSelectedPosition, mLastSelectedPositon = -1;
	private String[] mDatas = {"1", "2", "3", "4", "5", "6", "7"};
	private Paint.FontMetricsInt mFontMetrics, mFontMetricsLight;
	private int mBaseline;
	private HSliderListener mListener;
	private String mTag;
	private int mLastOffsetX, mTargetOffsetX;
	private int mMinOffsetX, mMaxOffsetX;
	private Handler mHandler;
	private final static int MSG_INVALIDATE = 1;
	private int mSpaceBlockCount; // 两侧单侧占位块数量
	private boolean mUserAction = false; // 用户操作行为，防止意外触发回调事件

	
	public HSlider(Context context) {
		super(context);
		initStyle(context, null, 0);
		initBasics();
	}
	
	public HSlider(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initStyle(context, attrs, 0);
		initBasics();
	}
	
	public HSlider(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initStyle(context, attrs, defStyleAttr);
		initBasics();
	}
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public HSlider(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initStyle(context, attrs, defStyleAttr);
		initBasics();
	}
	
	private void initStyle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HSlider, defStyleAttr, 0);
		
		int id_ts = a.getResourceId(R.styleable.HSlider_textDefSize, 0);
		if (id_ts > 0){
			mTextSize = getResources().getDimensionPixelSize(id_ts);
		}else{
			mTextSize = (int)a.getDimensionPixelSize(R.styleable.HSlider_textDefSize, getResources().getDimensionPixelSize(R.dimen.hslider_text_size));
		}
		
		int id_ts_big = a.getResourceId(R.styleable.HSlider_textBigSize, 0);
		if (id_ts_big > 0){
			mTextLightSize = getResources().getDimensionPixelSize(id_ts_big);
		}else{
			mTextLightSize = (int)a.getDimensionPixelSize(R.styleable.HSlider_textBigSize, getResources().getDimensionPixelSize(R.dimen.hslider_text_size_big));
		}
		
		//mTextSize = (int)a.getDimension(R.styleable.HSlider_textDefSize, getResources().getDimension(R.dimen.hslider_text_size));
		//mTextLightSize = (int)a.getDimension(R.styleable.HSlider_textBigSize, getResources().getDimension(R.dimen.hslider_text_size_big));
		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (mOffsetX < mMinOffsetX){
			mOffsetX = mMinOffsetX;
		} else if (mOffsetX > mMaxOffsetX) {
			mOffsetX = mMaxOffsetX;
		}
		
		int centerX = mBlockWidth / 2;
		
		for (int i = 0; i < mDatas.length; i++) {
			
			float x = centerX + mBlockWidth * i + mOffsetX;
			// 只绘制『可视范围』内的图形
			if (x > 0 && x < mTotalWidth) {
				String text = "";
				int index = i;
				if (index >= 0 && index < mDatas.length) {
					text = mDatas[index];
				}
				
				if (x > mCenterXMin && x < mCenterXMax) {
					mSelectedPosition = index;
					// 绘制中心位置
					float percent = 1 - (Math.abs(mTotalWidthHalf - x) / mBlockWidthHalf);
					int fontSize = (int) ((mTextLightSize - mTextSize) * percent) + mTextSize;
					mPaintTextLight.setTextSize(fontSize);
					mFontMetricsLight = mPaintTextLight.getFontMetricsInt();
					
					int baseLine = (mTotalHeight - mFontMetricsLight.bottom - mFontMetricsLight.top) / 2;
					
					canvas.drawText(text,
							x,
							baseLine,
							mPaintTextLight);
				} else {
					canvas.drawText(text,
							x,
							mBaseline,
							mPaintText);
				}
			}
		}
		
		if (mAutoScroll) {
			//Log.i(TAG, "target : " + mTargetOffsetX + " , nowOffset : " + mOffsetX);
			if (mOffsetX != mTargetOffsetX){
				int diffX = Math.abs(Math.abs(mOffsetX) - Math.abs(mTargetOffsetX)); // 差距
				
				int change = diffX > mSpeedX ? mSpeedX : diffX / 2; // 每次偏移量
				if (change <= 0){
					change = 1;
				}
				
				if (mOffsetX > mTargetOffsetX){
					mOffsetX -= change;
				}else{
					mOffsetX += change;
				}
				
				mLastOffsetX = mOffsetX;
				mHandler.sendEmptyMessage(MSG_INVALIDATE);
			}else {
				mLastOffsetX = mOffsetX;
				if (mUserAction
						&& mListener != null
						&& mSelectedPosition >= 0
						&& mSelectedPosition < mDatas.length
						&& mSelectedPosition != mLastSelectedPositon) {
					mLastSelectedPositon = mSelectedPosition;
					mListener.onHSliderSelected(mTag, mSelectedPosition, mDatas[mSelectedPosition]);
				}
				mUserAction = false;
			}
		}
		
	}
	
	private int mDownY = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int offsetX = 0;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mAutoScroll = false;
				mDownX = (int)event.getX();
				mDownY = (int)event.getY();
				//getParent().requestDisallowInterceptTouchEvent(true); // 阻止父层的View截获touch事件
				break;
			
			case MotionEvent.ACTION_MOVE:
				mAutoScroll = false;
				offsetX = mLastOffsetX + ((int)event.getX() - mDownX);
				if (offsetX != 0
						&& offsetX != mOffsetX
						&& offsetX >= mMinOffsetX
						&& offsetX <= mMaxOffsetX){
					Bundle bundle = new Bundle();
					bundle.putInt("offsetX", offsetX);
					
					Message msg = mHandler.obtainMessage(MSG_INVALIDATE);
					msg.setData(bundle);
					mHandler.sendMessage(msg);
				}else{
					//Log.i(TAG, "offsetX: " + offsetX + " , mOffsetX: " + mOffsetX + " , min: " + mMinOffsetX + " , max: " + mMaxOffsetX);
				}
				break;
			
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL: // 滑出控件区域时（或作为listItem时，受外部事件影响）
				calculateTargetOffsetX();
				mAutoScroll = true;
				mUserAction = true;
				mHandler.sendEmptyMessage(MSG_INVALIDATE);
				//getParent().requestDisallowInterceptTouchEvent(false); // 取消阻止父层的View截获touch事件
				break;
			
			default:
				//Log.i(TAG, "Action : " + event.getAction());
				break;
		}
		
		return true; // 必须return true才能触发
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalWidth = w;
		mTotalHeight = h;
		mBaseline = (mTotalHeight - mFontMetrics.bottom - mFontMetrics.top) / 2;
		mTotalWidthHalf = mTotalWidth / 2;
		mBlockWidth = mTotalWidth / mBlockCount;
		mBlockWidthHalf = mBlockWidth / 2;
		mCenterXMin = mTotalWidthHalf - mBlockWidthHalf;
		mCenterXMax = mTotalWidthHalf + mBlockWidthHalf;
		
		initArrt();
		
		//Log.i(TAG, "onSizeChanged - min:" + mMinOffsetX + " len:" + mDatas.length + " spaceCount:" + mSpaceBlockCount + " bw:" + mBlockWidth);
	}
	
	private void initArrt() {
		mMinOffsetX = (mDatas.length - mSpaceBlockCount - 1) * mBlockWidth * -1;
		mMaxOffsetX = mSpaceBlockCount * mBlockWidth;
		
		mOffsetX = mLastOffsetX
				= mTargetOffsetX
				= (mSpaceBlockCount * mBlockWidth) - mSelectedPosition * mBlockWidth;
	}
	
	private void initBasics() {
		mTag = UUID.randomUUID().toString();
		
		mTextColor = getResources().getColor(R.color.color_hslider_text);
		mTextLightColor = getResources().getColor(R.color.color_hslider_text_light);
		
		//mTextSize = DensityUtil.sp2px(getContext(), getResources().getDimension(R.dimen.hslider_text_size));
		//mTextLightSize = DensityUtil.sp2px(getContext(), getResources().getDimension(R.dimen.hslider_text_size_big));
		
		mPaintText = new Paint();
		mPaintText.setColor(mTextColor);
		mPaintText.setTypeface(AppUtil.getCustomTypefase(getContext()));
		mPaintText.setAntiAlias(true);//去除锯齿
		mPaintText.setFilterBitmap(true);//对位图进行滤波处理
		mPaintText.setTextSize(mTextSize);
		mPaintText.setTextAlign(Paint.Align.CENTER);
		mFontMetrics = mPaintText.getFontMetricsInt();
		
		mPaintTextLight = new Paint();
		mPaintTextLight.setColor(mTextLightColor);
		mPaintTextLight.setTypeface(AppUtil.getCustomTypefase(getContext()));
		mPaintTextLight.setAntiAlias(true);//去除锯齿
		mPaintTextLight.setFilterBitmap(true);//对位图进行滤波处理
		mPaintTextLight.setTextSize(mTextLightSize);
		mPaintTextLight.setTextAlign(Paint.Align.CENTER);
		mFontMetricsLight = mPaintTextLight.getFontMetricsInt();// 文字渐变
		Shader shader = new LinearGradient(0, 0, 0, mFontMetricsLight.bottom - mFontMetricsLight.top, Color.parseColor("#b4fffe"), Color.parseColor("#2ebde5"), Shader.TileMode.MIRROR);
		mPaintTextLight.setShader(shader);
		
		mHandler = createHandler();
	}
	
	public void setTextSize(int textSize, int bigTextSize) {
		mTextSize = textSize;
		mTextLightSize = bigTextSize;
		
		mPaintText.setTextSize(mTextSize);
		mFontMetrics = mPaintText.getFontMetricsInt();
		
		mPaintTextLight.setTextSize(mTextLightSize);
		mFontMetricsLight = mPaintTextLight.getFontMetricsInt();// 文字渐变
		Shader shader = new LinearGradient(0, 0, 0, mFontMetricsLight.bottom - mFontMetricsLight.top, Color.parseColor("#b4fffe"), Color.parseColor("#2ebde5"), Shader.TileMode.MIRROR);
		mPaintTextLight.setShader(shader);
		
		postInvalidate();
	}
	
	private Handler createHandler(){
		return new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case MSG_INVALIDATE:
						Bundle bundle = msg.getData();
						if (bundle != null && bundle.containsKey("offsetX")) {
							mOffsetX = msg.getData().getInt("offsetX");
						}
						postInvalidate();
						break;
				}
			}
		};
	}
	
	/**
	 * 计算最终偏移的目标值
 	 */
	private void calculateTargetOffsetX() {
		int count = mOffsetX / mBlockWidth;
		float remainder = mOffsetX % mBlockWidth;
		mTargetOffsetX = mBlockWidth * count;
		if (remainder != 0) {
			if (Math.abs(remainder) > mBlockWidthHalf) {
				if (remainder > 0){
					mTargetOffsetX += mBlockWidth;
				}else{
					mTargetOffsetX -= mBlockWidth;
				}
			}
		}
		
		if (mTargetOffsetX > mMaxOffsetX){
			mTargetOffsetX = mMaxOffsetX;
		}else if (mTargetOffsetX < mMinOffsetX){
			mTargetOffsetX = mMinOffsetX;
		}
	}
	
	/**
	 * 加一
	 */
	public void up(){
		if (mTargetOffsetX > mMinOffsetX) {
			mUserAction = true;
			mAutoScroll = true;
			mTargetOffsetX -= mBlockWidth;
			if (mTargetOffsetX < mMinOffsetX){
				mTargetOffsetX = mMinOffsetX;
			}
			mHandler.sendEmptyMessage(MSG_INVALIDATE);
		}
	}
	
	/**
	 * 减一
	 */
	public void down(){
		if (mTargetOffsetX < mMaxOffsetX) {
			mUserAction = true;
			mAutoScroll = true;
			mTargetOffsetX += mBlockWidth;
			if (mTargetOffsetX > mMaxOffsetX){
				mTargetOffsetX = mMaxOffsetX;
			}
			mHandler.sendEmptyMessage(MSG_INVALIDATE);
		}
	}
	
	
	/**
	 * 初始化控件属性
	 * @param tag 标记（多个HSlider共用同一Listener时，可用tag区分相应的HSlider）
	 * @param blockCount 可视数量（奇数，>=3）
	 * @param selectedPosition 默认选中索引，0开始
	 * @param datas 数据
	 * @param listener 选中监听器
	 */
	public void init(String tag, int blockCount, int selectedPosition, String[] datas, HSliderListener listener){
		if (tag != null) {
			mTag = tag;
		}
		mBlockCount = blockCount;
		mDatas = datas;
		mSelectedPosition = selectedPosition;
		mListener = listener;
		
		if (mBlockCount % 2 == 0){ // 限制可视数量为奇数
			mBlockCount--;
		}
		if (mBlockCount < 3){
			mBlockCount = 3;
		}
		
		mSpaceBlockCount = (mBlockCount - 1) / 2;
		
		if (mSelectedPosition < 0){
			mSelectedPosition = 0;
		} else if (mSelectedPosition >= datas.length) {
			mSelectedPosition = datas.length - 1;
		}
		
		initArrt();
//		mBlockWidth = mTotalWidth / mBlockCount;
//		mBlockWidthHalf = mBlockWidth / 2;
//		mTargetOffsetX = (mSpaceBlockCount * mBlockWidth) - mSelectedPosition * mBlockWidth;
//		mOffsetX = mTargetOffsetX;
//		//Log.i("init", "offsetX : " + mOffsetX + " " + mSpaceBlockCount + " " + mBlockWidth + " " + selectedPosition);
		mHandler.sendEmptyMessage(MSG_INVALIDATE);
	}
	
//	public void selectPosition(int position){
//		mSelectedPosition = position;
//
//	}
	
	/**
	 * 获取选中项的索引值
	 * @return
	 */
	public int getSelectedPosition(){
		return mSelectedPosition;
	}
	
	/**
	 * 获取选中项的内容
	 * @return
	 */
	public String getSelectedValue(){
		return mDatas[mSelectedPosition];
	}
	
	/**
	 * 监听器
	 */
	public interface HSliderListener{
		void onHSliderSelected(String tag, int position, String text);
	}
	
}
