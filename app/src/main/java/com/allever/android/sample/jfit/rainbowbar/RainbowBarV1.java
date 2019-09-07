package com.allever.android.sample.jfit.rainbowbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.allever.android.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 彩色菜单条
 * Created by CHARWIN.
 */

public class RainbowBarV1 extends View {
	
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
	
	String[][] colorStrings = {
			{"#3284DC", "#2362A3"},
			{"#4FA6E0", "#296B9E"},
			{"#55D6F0", "#2D798B"},
			{"#57DAD8", "#31818C"},
			{"#57D796", "#398E72"},
			{"#52C95D", "#358737"},
			{"#73C743", "#5DA335"},
			{"#A8C236", "#899F2E"},
			{"#D0C628", "#BAB023"},
			{"#F5D546", "#C28E1C"},
			{"#E1921E", "#BD7B17"},
			{"#D57E21", "#BF6419"},
			{"#F49040", "#C34716"}};
	
	private Paint mPaint;
	private int mTotalWidth, mTotalHeight;
	private float mShaderX, mShaderY;
	private int mShaderColorStart = colors[0][0];
	private int mShaderColorEnd = colors[0][1];
	private int mTargetIndex = 0;
	private int mTargetColorStart = colors[mTargetIndex][0];
	private int mTargetColorEnd = colors[mTargetIndex][1];
	private float mPer1 = 1f, mPer2 = 1f;
	
	public RainbowBarV1(Context context) {
		super(context);
		initBasics();
	}
	public RainbowBarV1(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initBasics();
	}
	public RainbowBarV1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initBasics();
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public RainbowBarV1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initBasics();
	}

	/**
	 * 计算从startColor过度到endColor过程中百分比为franch时的颜色值
	 *
	 * @param startColor 起始颜色 int类型
	 * @param endColor   结束颜色 int类型
	 * @param franch     franch 百分比0.5
	 * @return 返回int格式的color
	 */
	public static int caculateColor(int startColor, int endColor, float franch) {
		String strStartColor = "#" + Integer.toHexString(startColor);
		String strEndColor = "#" + Integer.toHexString(endColor);
		String strResultColor = caculateColor(strStartColor, strEndColor, franch);
		Log.i("color", "start: " + strStartColor + " , end: " + strEndColor + " , result: " + strResultColor);
		return Color.parseColor(strResultColor);
	}
	
	/**
	 * 计算从startColor过度到endColor过程中百分比为franch时的颜色值
	 *
	 * @param startColor 起始颜色 （格式#FFFFFFFF）
	 * @param endColor   结束颜色 （格式#FFFFFFFF）
	 * @param franch     百分比0.5
	 * @return 返回String格式的color（格式#FFFFFFFF）
	 */
	public static String caculateColor(String startColor, String endColor, float franch) {
		
		int startAlpha = Integer.parseInt(startColor.substring(1, 3), 16);
		int startRed = Integer.parseInt(startColor.substring(3, 5), 16);
		int startGreen = Integer.parseInt(startColor.substring(5, 7), 16);
		int startBlue = Integer.parseInt(startColor.substring(7), 16);
		
		int endAlpha = Integer.parseInt(endColor.substring(1, 3), 16);
		int endRed = Integer.parseInt(endColor.substring(3, 5), 16);
		int endGreen = Integer.parseInt(endColor.substring(5, 7), 16);
		int endBlue = Integer.parseInt(endColor.substring(7), 16);
		
		int currentAlpha = startAlpha, currentRed = startRed, currentGreen = startGreen, currentBlue = startBlue;
		
		if (startAlpha != endAlpha) {
			currentAlpha = (startAlpha > endAlpha) ? --startAlpha : ++startAlpha;
		}
		if (startRed != endRed) {
			currentRed = (startRed > endRed) ? --startRed : ++startRed;
		}
		if (startGreen != endGreen) {
			currentGreen = (startGreen > endGreen) ? --startGreen : ++startGreen;
		}
		if (startBlue != endBlue) {
			currentBlue = (startBlue > endBlue) ? --startBlue : ++startBlue;
		}
		
		
		Log.i("color", "r: " + getHexString(currentRed) + " , g: " + getHexString(currentGreen) + " , b: " + getHexString(currentBlue) + " " + currentBlue);
		return "#" + getHexString(currentAlpha) + getHexString(currentRed)
				+ getHexString(currentGreen) + getHexString(currentBlue);
		
	}
	
	/**
	 * 将10进制颜色值转换成16进制。
	 */
	public static String getHexString(int value) {
		String hexString = Integer.toHexString(value);
		if (hexString.length() == 1) {
			hexString = "0" + hexString;
		}
		return hexString;
	}
	
	private void initBasics() {
		mPaint = new Paint();
		//mPaint.setColor(Color.parseColor("#ff00ff00"));
		mPaint.setAntiAlias(true);//去除锯齿
		mPaint.setFilterBitmap(true);//对位图进行滤波处理
		mPaint.setStyle(Paint.Style.FILL);
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalWidth = w;
		mTotalHeight = h;
		
		mShaderX = mTotalWidth * 0.3f;
		mShaderY = mTotalWidth / 2;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Shader shader = new LinearGradient(0, 0, mShaderX, mShaderY, mShaderColorStart, mShaderColorEnd, Shader.TileMode.CLAMP);
		mPaint.setShader(shader);
		canvas.drawPath(createPath(), mPaint);
		
		
		boolean needUpdate = false;

//		if (mShaderColorStart != mTargetColorStart){
//			needUpdate = true;
//			if (mShaderColorStart > mTargetColorStart) {
//				mShaderColorStart--;
//			}else{
//				mShaderColorStart++;
//			}
//		}
//		if (mShaderColorEnd != mTargetColorEnd) {
//			needUpdate = true;
//			if (mShaderColorEnd > mTargetColorEnd) {
//				mShaderColorEnd--;
//			}else{
//				mShaderColorEnd++;
//			}
//		}
		
		if (mShaderColorStart != mTargetColorStart) {
			needUpdate = true;
			mShaderColorStart = caculateColor(mShaderColorStart, mTargetColorStart, mPer1 -= 0.1f);
		}
		
		if (mShaderColorEnd != mTargetColorEnd) {
			needUpdate = true;
			mShaderColorEnd = caculateColor(mShaderColorEnd, mTargetColorEnd, mPer2 -= 0.1f);
		}
		
		if (needUpdate) {
			postInvalidateDelayed(5);
		} else {
			mTargetIndex++;
			if (mTargetIndex >= colors.length){
				mTargetIndex = 0;
			}
			mTargetColorStart = colors[mTargetIndex][0];
			mTargetColorEnd = colors[mTargetIndex][1];
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
	
	
	private List<ColorItem[]> colorList = new ArrayList<>();
	private void initColor(){
		for (int i = 0; i < colors.length; i++) {
			int[] c = colors[i];
			colorList.add(new ColorItem[]{parseColor(c[0]), parseColor(c[1])});
		}
	}
	
	private ColorItem parseColor(int color){
		int alpha = (color & 0xff000000) >> 24;
		int red = (color & 0x00ff0000) >> 16;
		int green = (color & 0x0000ff00) >> 8;
		int blue = (color & 0x000000ff);
		
		return new ColorItem(alpha, red, green, blue);
	}
	
	public ColorItem caculateColor(ColorItem start, ColorItem end) {
		if (start.alpha != end.alpha) {
			start.alpha = (start.alpha > end.alpha) ? --start.alpha : ++start.alpha;
		}
		
		if (start.red != end.red) {
			start.red = (start.red > end.red) ? --start.red : ++start.red;
		}
		
		if (start.green != end.green) {
			start.green = (start.green > end.green) ? --start.green : ++start.green;
		}
		
		if (start.blue != end.blue) {
			start.blue = (start.blue > end.blue) ? --start.blue : ++start.blue;
		}
		
		return new ColorItem(start.alpha, start.red, start.green, start.blue);
	}
	
	public class ColorItem{
		int alpha;
		int red;
		int green;
		int blue;
		
		public ColorItem(int alpha, int red, int green, int blue) {
			this.alpha = alpha;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
		
		public int getAlpha() {
			return alpha;
		}
		
		public void setAlpha(int alpha) {
			this.alpha = alpha;
		}
		
		public int getRed() {
			return red;
		}
		
		public void setRed(int red) {
			this.red = red;
		}
		
		public int getGreen() {
			return green;
		}
		
		public void setGreen(int green) {
			this.green = green;
		}
		
		public int getBlue() {
			return blue;
		}
		
		public void setBlue(int blue) {
			this.blue = blue;
		}
	}
	
	
	public static void main(String[] args) {
		int color = 0xaa112233;
		
		// >>> 无符号位移
		// >> 有符号位移
		int alpha = (color & 0xff000000) >>> 24;
		int red = (color & 0x00ff0000) >> 16;
		int green = (color & 0x0000ff00) >> 8;
		int blue = (color & 0x000000ff);
		
		System.out.println("a:" + Integer.toHexString(alpha) + " " + alpha + " , r:" + Integer.toHexString(red) + " , g:" + Integer.toHexString(green) + " , b:" + Integer.toHexString(blue));
	}
	
}
