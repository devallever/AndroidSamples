package com.allever.android.sample.jfit.ripple;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.allever.android.sample.customview.linecharview.DensityUtil;

/**
 * 文字内水波纹
 */

public class RippleTextView extends AppCompatTextView {
	
	// 波纹颜色
	private int mWaveColor = 0xff2cfef5;
	private static final float STRETCH_FACTOR_A = 8; // 上下幅度
	private static final int OFFSET_Y = 0;
	// 第一条水波移动速度
	private static final int TRANSLATE_X_SPEED_ONE = 2;
	private static final int NEED_INVALIDATE = 0X23;
	private Bitmap mBmpWave;
	private float mCycleFactorW;
	private int mTotalWidth, mTotalHeight;
	private float[] mYPositions;
	private float[] mResetOneYPositions;
	private int mXOffsetSpeedOne;
	private int mXOneOffset;
	private Paint mWavePaint;
	private DrawFilter mDrawFilter;
	private float mWaveProgress = 0.5f;
	private float mWaveProgressNow = mWaveProgress;
	private float mWaveProgressChangeRange = 0.004f; // 波纹上下增减幅度
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case NEED_INVALIDATE:
					invalidate();
					//mHandler.sendEmptyMessageDelayed(NEED_INVALIDATE, 50);
					break;
			}
		}
	};
	
	public RippleTextView(Context context) {
		super(context);
		init(context);
	}
	
	public RippleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	public RippleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		// 字体
		setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Shket-Regular_0.016.otf"));
		// 将dp转化为px，用于控制不同分辨率上移动速度基本一致
		mXOffsetSpeedOne = DensityUtil.dip2px(context, TRANSLATE_X_SPEED_ONE);
		// 初始绘制波纹的画笔
		mWavePaint = new Paint();
		// 去除画笔锯齿
		mWavePaint.setAntiAlias(true);
		// 设置风格为实线
		mWavePaint.setStyle(Paint.Style.FILL);
		// 设置画笔颜色
		mWavePaint.setColor(mWaveColor);
		mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Canvas canvasWave = null;
		
		// 波纹背景
		if (mBmpWave == null) {
			mBmpWave = Bitmap.createBitmap(mTotalWidth, mTotalHeight, Bitmap.Config.ARGB_8888);
		}
		try {
			canvasWave = new Canvas(mBmpWave);
			canvasWave.drawColor(getCurrentTextColor());
			canvasWave.setDrawFilter(mDrawFilter);
			resetPositonY();
			for (int i = 0; i < mTotalWidth; i++) {
				// 减400只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
				// 绘制第一条水波纹
				canvasWave.drawLine(i, mTotalHeight - mResetOneYPositions[i] - mTotalHeight * mWaveProgressNow, i,
						mTotalHeight,
						mWavePaint);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		getPaint().setShader(new BitmapShader(mBmpWave, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP));
		super.onDraw(canvas);
		
		if (canvasWave != null) {
			canvasWave = null;
		}
		
		if (mWaveProgressNow < mWaveProgress) {
			mWaveProgressNow += mWaveProgressChangeRange;
			if (mWaveProgressNow > mWaveProgress) {
				mWaveProgressNow = mWaveProgress;
			}
		} else if (mWaveProgressNow > mWaveProgress) {
			mWaveProgressNow -= mWaveProgressChangeRange;
			if (mWaveProgressNow < mWaveProgress) {
				mWaveProgressNow = mWaveProgress;
			}
		}
		// 改变两条波纹的移动点
		mXOneOffset += mXOffsetSpeedOne;
		
		// 如果已经移动到结尾处，则重头记录
		if (mXOneOffset >= mTotalWidth) {
			mXOneOffset = 0;
		}
		
		// 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
		//postInvalidate();
		
		postInvalidateDelayed(50);
//		mHandler.sendEmptyMessageDelayed(NEED_INVALIDATE, 10);
	}
	
	private void resetPositonY() {
		// mXOneOffset代表当前第一条水波纹要移动的距离
		int yOneInterval = mYPositions.length - mXOneOffset;
		// 使用System.arraycopy方式重新填充第一条波纹的数据
		System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
		System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 记录下view的宽高
		mTotalWidth = w;
		mTotalHeight = h;
		// 用于保存原始波纹的y值
		mYPositions = new float[mTotalWidth];
		// 用于保存波纹一的y值
		mResetOneYPositions = new float[mTotalWidth];
		// 将周期定为view总宽度
		mCycleFactorW = (float) (2 * Math.PI / mTotalWidth) * 5;
		
		// 根据view总宽度得出所有对应的y值
		for (int i = 0; i < mTotalWidth; i++) {
			mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
		}
	}
	
	
	public float getWaveProgress() {
		return mWaveProgress;
	}
	
	public void setWaveProgress(float waveProgress) {
		if (waveProgress > 1) {
			waveProgress = 1;
		} else if (waveProgress < 0) {
			waveProgress = 0;
		}
		this.mWaveProgress = waveProgress;
	}
	
	
	public void updateColor(int waveColor, int bgColor) {
		mWaveColor = waveColor;
		mWavePaint.setColor(mWaveColor);
		setTextColor(bgColor);
	}
	
}
