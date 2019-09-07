package com.allever.android.sample.jfit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.allever.android.sample.R;


/**
 * Created by CHARWIN.
 */

public class JfitHSlider extends LinearLayout {
	
	private HSlider hslider;
	
	public JfitHSlider(Context context) {
		this(context, null);
	}
	
	public JfitHSlider(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public JfitHSlider(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		// 加载布局
		LayoutInflater.from(context).inflate(R.layout.view_jfit_hslider, this);
		
		// 获取控件
		hslider = (HSlider) findViewById(R.id.hs_jfit_hslider);
		hslider.setTag(getTag());
		findViewById(R.id.ll_jfit_hslider_left).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hslider.down();
			}
		});
		findViewById(R.id.ll_jfit_hslider_right).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hslider.up();
			}
		});
	}
	
	public HSlider hslider(){
		return hslider;
	}
	
	@Override
	public void setTag(Object tag) {
		super.setTag(tag);
		hslider.setTag(tag);
	}
	
	public void setTextSize(int textSize, int bigTextSize) {
		hslider.setTextSize(textSize, bigTextSize);
	}
}
