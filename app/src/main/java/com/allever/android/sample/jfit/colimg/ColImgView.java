package com.allever.android.sample.jfit.colimg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.allever.android.sample.R;
import com.allever.android.sample.jfit.BitmapCacheUtil;


/**
 * Created by CHARWIN.
 */

public class ColImgView extends View {
	
	private int mStartColor = 0;
	private int mEndColor = 0;
	private int mDrawableRes = 0;
	private int mTotalWidth, mTotalHeight;
	private int mBitmapWidth, mBitmapHeight;
	
	public ColImgView(Context context) {
		this(context, null);
	}
	
	public ColImgView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ColImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}
	
	private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColImgView, defStyleAttr, 0);
		mStartColor = a.getColor(R.styleable.ColImgView_startColor, 0);
		mEndColor = a.getColor(R.styleable.ColImgView_endColor, 0);
		mDrawableRes = a.getResourceId(R.styleable.ColImgView_src, 0);
		a.recycle();
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mDrawableRes != 0) {
			
			if (mStartColor != 0 && mEndColor != 0) {
				Bitmap bmpAlpha = BitmapCacheUtil.getBitmap(mDrawableRes + "");
				if (bmpAlpha == null) {
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), mDrawableRes);
					bmpAlpha = bmp.extractAlpha();
					BitmapCacheUtil.addToCache(mDrawableRes, bmpAlpha);
				}

				Paint paint = new Paint();
				Shader shader = new LinearGradient(0, 0, 0, mTotalHeight, mStartColor, mEndColor, Shader.TileMode.MIRROR);
				paint.setShader(shader);

				//在画布上（mAlphaBitmap）绘制alpha位图
				canvas.drawBitmap(bmpAlpha,
						mTotalWidth / 2 - mBitmapWidth / 2,
						mTotalHeight / 2 - mBitmapHeight / 2,
						paint);
			}else{
				canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), mDrawableRes),
						mTotalWidth / 2 - mBitmapWidth / 2,
						mTotalHeight / 2 - mBitmapHeight / 2,
						new Paint());
			}
		} else {
			super.onDraw(canvas);
		}
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalWidth = w;
		mTotalHeight = h;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//Bitmap bmp = BitmapFactory.decodeByteArray();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), mDrawableRes, options);
		mBitmapHeight = options.outHeight;
		mBitmapWidth = options.outWidth;
		String imageType = options.outMimeType;
		
		
		int modeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
		int modeHeight = View.MeasureSpec.getMode(heightMeasureSpec);

		int w = View.MeasureSpec.getSize(widthMeasureSpec);
		int h = View.MeasureSpec.getSize(heightMeasureSpec);
		
		//Log.i("mode", modeWidth + " , " + modeHeight + " w:" + w + " , h:" + h);
		
		if (modeWidth == MeasureSpec.AT_MOST) {
			w = mBitmapWidth;
		}
		
		if (modeHeight == MeasureSpec.AT_MOST) {
			h = mBitmapHeight;
		}
		
		setMeasuredDimension(w, h);
		
		// mode
		// AT_MOST : 自动  wrap_content
		// EXACTLY : 精确  match_parent 或指定值
		// UNSPECIFIED = 0;
		
//		int w = View.MeasureSpec.getSize(widthMeasureSpec);
//		int h = View.MeasureSpec.getSize(heightMeasureSpec);
//		setMeasuredDimension(w/2, h/2);
	}
	
	public void setDrawable(@DrawableRes int drawable) {
		mDrawableRes = drawable;
	}
	
	public void updateColor(int startColor, int endColor) {
		mStartColor = startColor;
		mEndColor = endColor;
		postInvalidate();
	}
}
