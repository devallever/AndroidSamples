package com.allever.android.sample.jfit.rainbowbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.allever.android.sample.R;
import com.allever.android.sample.customview.linecharview.BezierUtil;
import com.allever.android.sample.jfit.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 彩色菜单条
 * Created by CHARWIN.
 */

public class RainbowBar extends View {
	public static final String TAG = "RainbowBar";
	private String mTag = "";
	int[][] mColorArray = {
			{0xff81cc97, 0xff275e91},
			{0xff4FA6E0, 0xff296B9E},
			{0xff55D6F0, 0xff2D798B},
			{0xff57DAD8, 0xff31818C},
			{0xff57D796, 0xff398E72},
			{0xff52C95D, 0xff358737},
			{0xff73C743, 0xff5DA335},
			{0xffA8C236, 0xff899F2E},
			{0xffD0C628, 0xffBAB023},
			{0xffF5D546, 0xffC28E1C},
			{0xffE1921E, 0xffBD7B17},
			{0xffD57E21, 0xffBF6419},
			{0xffF49040, 0xffC34716}};
	
	
	private Paint mPaint,
			mPaintText,
			mPaintTextBig,
			mPaintTextSelected,
			mPaintTextBigSelected;
	private Paint.FontMetricsInt mFontMetrics, mFontMetricsBig;
	private int mTotalWidth, mTotalHeight;
	private float mShaderX, mShaderY;
	private float mSwitchingPercent = 1f; // 切换进度
	private float mSwitchingPercentAmplitude = 0.01f; // 切换进度增幅
	private boolean mAutoDraw = false;
	private Shader mShader;
	private int[] mColor = {0xff81cc97, 0xff275e91};
	private int[] mColorTarget = {0xff3284DC, 0xff2362A3};
	private int[] mColorLast = {0xff3284DC, 0xff2362A3};
	private List<RainbowData> mDatas = new ArrayList<>();
	private int mSelectedBlockTopY; // 选中板块顶部Y坐标
	private int mSelectedBlockBottomY; // 选中板块底部Y坐标
	private int mOffsetY = 0;
	private int mBlockHeightAvg;
	private Path mQuadPathLine;
	private int mBlockX;
	private int mArcX;
	private int mCenterY;
	private int mTextOffsetY;
	private Path mQuadPath;
	
	public RainbowBar(Context context) {
		super(context);
		initBasics();
	}
	public RainbowBar(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initBasics();
	}
	public RainbowBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initBasics();
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public RainbowBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initBasics();
	}
	
	private void initBasics() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);//去除锯齿
		mPaint.setFilterBitmap(true);//对位图进行滤波处理
		mPaint.setStyle(Paint.Style.FILL);
		
		mPaintText = new Paint();
		mPaintText.setColor(0xff000000);
		mPaintText.setTypeface(AppUtil.getCustomTypefase(getContext()));
		mPaintText.setAntiAlias(true);//去除锯齿
		mPaintText.setFilterBitmap(true);//对位图进行滤波处理
		mPaintText.setTextSize(getResources().getDimensionPixelSize(R.dimen.rainbowbar_text_size));
		mPaintText.setTextAlign(Paint.Align.LEFT);
		mFontMetrics = mPaintText.getFontMetricsInt();
		
		mPaintTextBig = new Paint(mPaintText);
		mPaintTextBig.setTextSize(getResources().getDimensionPixelSize(R.dimen.rainbowbar_text_size_big));
		mPaintTextBig.setFakeBoldText(true);
		mFontMetricsBig = mPaintTextBig.getFontMetricsInt();
		
		mPaintTextSelected = new Paint(mPaintText);
		mPaintTextSelected.setColor(0xffffffff);
		
		mPaintTextBigSelected = new Paint(mPaintTextBig);
		mPaintTextBigSelected.setColor(0xffffffff);
		
		mTextOffsetY = (mFontMetricsBig.ascent + mFontMetricsBig.descent)/2;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalWidth = w;
		mTotalHeight = h;
		
		mShaderX = mTotalWidth * 0.5f;
		mShaderY = mTotalWidth * 0.4f;
		
		mArcX = (int) (mTotalWidth * 0.27f);
		mCenterY = mTotalHeight / 2;
		mBlockX = (int)((mTotalWidth - mArcX) * 0.25f + mArcX);
		
		createPath();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// 主模块
		mShader = new LinearGradient(0, 0, mShaderX, mShaderY, mColor[0], mColor[1], Shader.TileMode.CLAMP);
		mPaint.setShader(mShader);
		canvas.drawPath(mQuadPath, mPaint);
		
		drawDataText(canvas);
		
		if (mAutoDraw){
			if (mListener != null) {
				mListener.onRainbowColorChanged(mColor[0], mColor[1], mSwitchingPercent, mTag);
			}
			
			if (mSwitchingPercent < 1f){
				mSwitchingPercent += mSwitchingPercentAmplitude;
			}
			
			if (mSwitchingPercent >= 1f){
				mSwitchingPercent = 1f;
				mAutoDraw = false;
				if (mListener != null) {
					mListener.onRainbowColorChangeCompleted();
				}
			}
			
			int color1 = ColorUtil.getGradientColor(mColorLast[0], mColorTarget[0], mSwitchingPercent);
			int color2 = ColorUtil.getGradientColor(mColorLast[1], mColorTarget[1], mSwitchingPercent);
			mColor = new int[]{color1, color2};
			postInvalidate();
		}
	}
	
	private void drawDataText(Canvas canvas) {
		for (int i = 0; i < mDatas.size(); i++) {
			RainbowData data = mDatas.get(i);
			float h = i * mBlockHeightAvg + mBlockHeightAvg/2f + mOffsetY;
			float hPercent = h / mTotalHeight;
			PointF p = BezierUtil.calculateBezierPointForQuadratic(hPercent, new PointF(mBlockX, 0), new PointF(0, mCenterY), new PointF(mBlockX, mTotalHeight));
			boolean isSelected = isSelectedBlock(p.y);
			
			canvas.drawText(data.getTitle(), p.x, p.y - mTextOffsetY + mFontMetricsBig.ascent, isSelected ? mPaintTextSelected : mPaintText);
			canvas.drawText(data.getValue(), p.x, p.y - mTextOffsetY, isSelected ? mPaintTextBigSelected : mPaintTextBig);
			canvas.drawText(data.getUnit(), p.x, p.y - mTextOffsetY + mFontMetricsBig.descent, isSelected ? mPaintTextSelected : mPaintText);
		}
	}
	
	private boolean isSelectedBlock(float y) {
		return (y >= mSelectedBlockTopY && y <= mSelectedBlockBottomY);
	}
	
	/**
	 * 模块结构
	 *
	 * @return
	 */
	private void createPath() {
		
		mQuadPathLine = new Path();
		mQuadPathLine.moveTo(mArcX, 0);//设置Path的起点
		mQuadPathLine.quadTo(-mArcX, mCenterY, mArcX, mTotalHeight); //设置贝塞尔曲线的控制点坐标和终点坐标
		
		mQuadPath = new Path();
		mQuadPath.moveTo(mArcX, 0);//设置Path的起点
		mQuadPath.quadTo(-mArcX, mCenterY, mArcX, mTotalHeight); //设置贝塞尔曲线的控制点坐标和终点坐标
		mQuadPath.lineTo(mTotalWidth, mTotalHeight);
		mQuadPath.quadTo(mTotalWidth - mArcX * 2, mCenterY, mTotalWidth, 0);
		mQuadPath.lineTo(mArcX, 0);
	}
	
	public void changeColor(String tag, int startColor, int endColor){
		mTag = tag;
		mColorTarget = new int[]{startColor, endColor};
		mColorLast = new int[]{mColor[0], mColor[1]};
		
		mSwitchingPercent = 0f;
		mAutoDraw = true;
		postInvalidate();
	}
	
	public interface RainbowBarListener{
		void onRainbowColorChanged(int startColor, int endColor, float percent, String tag);
		void onRainbowColorChangeCompleted();
	}
	private RainbowBarListener mListener;
	
	public void setRainbowBarListener(RainbowBarListener listener) {
		mListener = listener;
	}
	
	public void updateData(List<RainbowData> list) {
		if (list != null) {
			int maxDisplayCount = mDatas.size();
			if (maxDisplayCount > 3) {
				maxDisplayCount = 3;
			}
			mBlockHeightAvg = maxDisplayCount > 0 ? mTotalHeight / maxDisplayCount : mTotalHeight; // 每块平均高度
			mSelectedBlockTopY = mBlockHeightAvg;
			mSelectedBlockBottomY = mBlockHeightAvg + mBlockHeightAvg;
		}
		
		mDatas.clear();
		mDatas.addAll(list);
		postInvalidate();
	}
	
	private int mDownY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		int offsetX = 0;
//		switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//
//				mDownY = (int)event.getY();
//				//getParent().requestDisallowInterceptTouchEvent(true); // 阻止父层的View截获touch事件
//				break;
//
//			case MotionEvent.ACTION_MOVE:
//				mOffsetY = (int)(event.getY() - mDownY);
//				postInvalidate();
//				break;
//
//			case MotionEvent.ACTION_UP:
//			case MotionEvent.ACTION_CANCEL: // 滑出控件区域时（或作为listItem时，受外部事件影响）
//
//				//getParent().requestDisallowInterceptTouchEvent(false); // 取消阻止父层的View截获touch事件
//				break;
//
//			default:
//				//Log.i(TAG, "Action : " + event.getAction());
//				break;
//		}

		return true; // 必须return true才能触发
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		
		int offsetX = 0;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
				mDownY = (int)event.getY();
				getParent().requestDisallowInterceptTouchEvent(true); // 阻止父层的View截获touch事件
				break;
			
			case MotionEvent.ACTION_MOVE:
				mOffsetY = (int)(event.getY() - mDownY);
				postInvalidate();
				break;
			
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL: // 滑出控件区域时（或作为listItem时，受外部事件影响）
				
				getParent().requestDisallowInterceptTouchEvent(false); // 取消阻止父层的View截获touch事件
				break;
			
			default:
				//Log.i(TAG, "Action : " + event.getAction());
				break;
		}
		return true;
	}
}
