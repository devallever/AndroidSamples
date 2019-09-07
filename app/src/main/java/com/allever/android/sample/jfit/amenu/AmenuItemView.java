package com.allever.android.sample.jfit.amenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.allever.android.sample.R;
import com.allever.android.sample.jfit.AppUtil;


/**
 * Created by CHARWIN.
 */

public class AmenuItemView extends View {
	public static final String TAG = "AmenuItemView";
	
	private Paint mPaintText, mPaintTextBig;
	private Paint.FontMetricsInt mFontMetrics, mFontMetricsBig;
	private int mTotalWidth, mTotalHeight, mTextOffsetY;
	private int mTextColor = 0xff000000;
	
	private String mTextTitle = "标题",
			mTextValue = "123",
			mTextDescript = "abc";

	
	public AmenuItemView(Context context) {
		super(context);
		initBasics();
	}
	public AmenuItemView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initBasics();
	}
	public AmenuItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initBasics();
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public AmenuItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initBasics();
	}
	
	private void initBasics() {
		mPaintText = new Paint();
		mPaintText.setColor(mTextColor);
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
		
		mTextOffsetY = (mFontMetricsBig.ascent + mFontMetricsBig.descent)/2;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalWidth = w;
		mTotalHeight = h;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int wMax = (int)mPaintTextBig.measureText("000");
		int w = (int)mPaintTextBig.measureText(mTextValue);
		if (w > wMax) {
			w = wMax;
		}
		
		int x = mTotalWidth/2 - w/2;
		int y = mTotalHeight/2;
		
		canvas.drawText(mTextTitle,
				x,
				y - mTextOffsetY + mFontMetricsBig.ascent,
				mPaintText);
		
		canvas.drawText(mTextValue,
				x,
				y - mTextOffsetY,
				mPaintTextBig);
		
		canvas.drawText(mTextDescript,
				x,
				y - mTextOffsetY + mFontMetricsBig.descent,
				mPaintText);
	}
	
	public void setData(String title, String value, String descript) {
		mTextTitle = title;
		mTextValue = value;
		mTextDescript  = descript;
	}
	
	public void updateData(String title, String value, String descript) {
		setData(title, value, descript);
		postInvalidate();
	}
	
	public void setTextColor(int color){
		mTextColor = color;
		mPaintText.setColor(mTextColor);
		mPaintTextBig.setColor(mTextColor);
		postInvalidate();
	}
}
