package com.allever.android.sample.jfit.colimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.allever.android.sample.R;
import com.allever.android.sample.jfit.BitmapCacheUtil;

/**
 * Created by CHARWIN.
 */

public class ColImgViewV1 extends ImageView {
	
	private int mStartColor = 0xff55D6F0;
	private int mEndColor = 0xffC34716;
	private int mDrawableRes = R.drawable.end_ic_award;
	
	
	public ColImgViewV1(Context context) {
		super(context);
	}
	
	public ColImgViewV1(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ColImgViewV1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public ColImgViewV1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	
	//private Bitmap mBmpAlpha = null;
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mStartColor != 0 && mEndColor != 0 && mDrawableRes != 0) {
//			Bitmap bmp = getAlphaBitmap(mDrawableRes, mStartColor, mEndColor);
			//canvas.drawBitmap(bmp, 0, 0, null);
			
			Bitmap mBmpAlpha = BitmapCacheUtil.getBitmap(mDrawableRes + "");
			if (mBmpAlpha == null) {
				//Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(mDrawableRes)).getBitmap();
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), mDrawableRes);
				mBmpAlpha = bmp.extractAlpha();
				BitmapCacheUtil.addToCache(mDrawableRes, mBmpAlpha);
			}
			
			Paint paint = new Paint();
			Shader shader = new LinearGradient(0, 0, 0, 100, mStartColor, mEndColor, Shader.TileMode.MIRROR);
			paint.setShader(shader);
			
			//在画布上（mAlphaBitmap）绘制alpha位图
			canvas.drawBitmap(mBmpAlpha, 0, 0, paint);
		} else {
			super.onDraw(canvas);
		}
	}
	
	
	//提取图像Alpha位图
	public Bitmap getAlphaBitmap(@DrawableRes int drawableId, int startColor, int endColor) {
		Bitmap bmpSrc = ((BitmapDrawable) getResources().getDrawable(drawableId)).getBitmap();
		Bitmap bmpAlpha = Bitmap.createBitmap(bmpSrc.getWidth(), bmpSrc.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmpAlpha);
		Paint paint = new Paint();
		
		Shader shader = new LinearGradient(0, 0, 0, 100, startColor, endColor, Shader.TileMode.MIRROR);
		paint.setShader(shader);
		
		//从原位图中提取只包含alpha的位图
		//Bitmap b = bmpSrc.extractAlpha();
		
		//在画布上（mAlphaBitmap）绘制alpha位图
		canvas.drawBitmap(bmpSrc.extractAlpha(), 0, 0, paint);
		
		return bmpAlpha;
	}
	
	//提取图像Alpha位图
	public Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
//          BitmapDrawable mBitmapDrawable = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.enemy_infantry_ninja);
//          Bitmap mBitmap = mBitmapDrawable.getBitmap();
		
		//BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
		//注意这两个方法的区别
		//Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
		Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		
		Canvas mCanvas = new Canvas(mAlphaBitmap);
		Paint mPaint = new Paint();
		
		//mPaint.setColor(mColor);
		Shader shader = new LinearGradient(0, 0, 0, 100, Color.parseColor("#ffff0000"), Color.parseColor("#ff0000ff"), Shader.TileMode.MIRROR);
		mPaint.setShader(shader);
		//从原位图中提取只包含alpha的位图
		Bitmap alphaBitmap = mBitmap.extractAlpha();
		//在画布上（mAlphaBitmap）绘制alpha位图
		mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);
		
		return mAlphaBitmap;
	}
	
	public void updateColor(int startColor, int endColor) {
		mStartColor = startColor;
		mEndColor = endColor;
		postInvalidate();
	}
}
