package com.allever.android.sample.jfit;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by CHARWIN.
 */

public class AppUtil {
	
	private static Typeface customTypefase;
	
	/**
	 * 获取本app指定的字体
	 * @param context
	 * @return
	 */
	public static Typeface getCustomTypefase(Context context){
		if (customTypefase == null) {
			customTypefase = Typeface.createFromAsset(context.getAssets(), "font/Shket-Regular_0.016.otf");
		}
		return customTypefase;
	}
	
}
