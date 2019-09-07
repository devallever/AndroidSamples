package com.allever.android.sample.jfit.rainbowbar;

/**
 * Created by CHARWIN.
 */

public class ColorUtil {
	
	/**
	 * 获取过渡颜色
	 *
	 * @param srcColor
	 * @param targetColor
	 * @param percent     过渡位置：0～100%
	 * @return color
	 */
	public static int getGradientColor(int srcColor, int targetColor, float percent) {
		// >>> 无符号位移
		// >> 有符号位移
		//Gradient = A + (B-A) / Step * N
		
		if (percent <= 0f) {
			return srcColor;
		} else if (percent >= 1f) {
			return targetColor;
		}
		
		int blockCount = (int) (percent * 100f);
		
		/*
		int[] srcArgb;
		int[] targetArgb;
		int[] blocks;

		srcArgb = new int[]{(srcColor & 0xff000000) >> 24,
				(srcColor & 0x00ff0000) >> 16,
				(srcColor & 0x0000ff00) >> 8,
				(srcColor & 0x000000ff)};

		targetArgb = new int[]{(targetColor & 0xff000000) >> 24,
				(targetColor & 0x00ff0000) >> 16,
				(targetColor & 0x0000ff00) >> 8,
				(targetColor & 0x000000ff)};

		blocks = new int[4];
		for (int i = 0; i < 4; i++) {
			blocks[i] = srcArgb[i] + (targetArgb[i] - srcArgb[i]) / 100 * blockCount;
		}

		return (blocks[0] << 24)
				+ (blocks[1] << 16)
				+ (blocks[2] << 8)
				+ blocks[3];
				*/
		
		// 与注释代码逻辑一致的精简版
		int result = 0;
		for(int i=0; i < 4; i++) {
			int b = (3 - i) * 8;
			int a = 0xFF << b;

			int src = (srcColor & a) >> b;
			int target = (targetColor & a) >> b;
			result += (int)(src + (target - src) / 100f * blockCount) << b;
		}
		return result;
	}
	
	/**
	 * 颜色增亮
	 * @param color
	 * @param percent 正数变亮，负数变暗，绝对值范围：0～100%（0～1）
	 * @return
	 */
	public static int getBrightColor(int color, float percent){
		int[] argb = new int[]{(color & 0xff000000) >> 24,
				(color & 0x00ff0000) >> 16,
				(color & 0x0000ff00) >> 8,
				(color & 0x000000ff)};
		
		for (int i = 0; i < argb.length; i++) {
			if (i == 0) {
				continue; // 不影响透明度
			}
			int k = argb[i];
			k = k + (int)((255f - k) * percent);
			if (k > 255){
				k = 255;
			} else if (k < 0) {
				k = 0;
			}
			argb[i] = k;
		}
		
		return (argb[0] << 24)
				+ (argb[1] << 16)
				+ (argb[2] << 8)
				+ argb[3];
	}
}
