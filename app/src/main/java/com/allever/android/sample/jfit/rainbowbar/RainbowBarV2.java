package com.allever.android.sample.jfit.rainbowbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.allever.android.sample.R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 彩色菜单条
 * Created by CHARWIN.
 */

public class RainbowBarV2 extends View {
	public static final String TAG = "RainbowBar";
	
	int[][] colors = {
			{0xff3284DC, 0xff2362A3},
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
	
	
	private Paint mPaint, mPaintBackground;
	private int mTotalWidth, mTotalHeight;
	private float mShaderX, mShaderY;
	private int[] mColor = colors[0];
	private float mSwitchingPercent = 1f; // 切换进度
	private float mSwitchingPercentAmplitude = 0.1f; // 切换进度增幅
	private boolean mAutoDraw = false;
	private Shader mShader;
	
	public RainbowBarV2(Context context) {
		super(context);
		initBasics();
	}
	public RainbowBarV2(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initBasics();
	}
	public RainbowBarV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initBasics();
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public RainbowBarV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initBasics();
	}
	
	private void initBasics() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);//去除锯齿
		mPaint.setFilterBitmap(true);//对位图进行滤波处理
		mPaint.setStyle(Paint.Style.FILL);
		mShader = new LinearGradient(0, 0, mShaderX, mShaderY, mColor[0], mColor[1], Shader.TileMode.CLAMP);
		mPaint.setShader(mShader);
		
		mPaintBackground = new Paint(mPaint);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalWidth = w;
		mTotalHeight = h;
		
		mShaderX = mTotalWidth * 0.5f;
		mShaderY = mTotalWidth * 0.4f;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Path path = createPath();
		
		// 背景过渡模块
		if (mSwitchingPercent < 1f){
			canvas.drawPath(path, mPaintBackground);
			Log.i(TAG, "显示过渡背景");
		}else{
			Log.i(TAG, "显示过渡背景 - 无");
		}
		
		// 主模块
		mPaint.setAlpha((int)(255f * mSwitchingPercent));
		canvas.drawPath(path, mPaint);
		
		if (mAutoDraw){
			if (mSwitchingPercent < 1f){
				mSwitchingPercent += mSwitchingPercentAmplitude;
			}
			
			if (mSwitchingPercent >= 1f){
				mSwitchingPercent = 1f;
				mAutoDraw = false;
			}
			Log.i(TAG, "percent: " + mSwitchingPercent);
			postInvalidate();
		}
	}
	
	/**
	 * 模块结构
	 *
	 * @return
	 */
	@NonNull
	private Path createPath() {
		int arcX = (int) (mTotalWidth * 0.24f);
		int centerY = mTotalHeight / 2;
		
		Path path = new Path();
		path.moveTo(arcX, 0);//设置Path的起点
		/**
		 * 参数1、2：x1，y1为控制点的坐标值
		 * 参数3、4：x2，y2为终点的坐标值
		 */
		path.quadTo(-arcX, centerY, arcX, mTotalHeight); //设置贝塞尔曲线的控制点坐标和终点坐标
		path.lineTo(mTotalWidth, mTotalHeight);
		path.quadTo(mTotalWidth - arcX * 2, centerY, mTotalWidth, 0);
		path.lineTo(arcX, 0);
		
		return path;
	}
	
	public void changeColor(int startColor, int endColor){
		mPaintBackground = new Paint(mPaint);
		mPaintBackground.setAlpha(255);
		
		mShader = new LinearGradient(0, 0, mShaderX, mShaderY, mColor[0], mColor[1], Shader.TileMode.CLAMP);
		mPaint.setShader(mShader);
		
		mColor = new int[]{startColor, endColor};
		mSwitchingPercent = 0f;
		mAutoDraw = true;
		postInvalidate();
	}
}
