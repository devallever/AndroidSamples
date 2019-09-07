package com.allever.allsample.customView.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import androidx.core.widget.TextViewCompat;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by allever on 17-7-26.
 */

public class FreshTextView extends AppCompatTextView {

    private static final String TAG = "FreshTextView";

    private int mViewWidth = 0;
    private int mTranslate = 0;
    private Paint mPaint;
    private LinearGradient mLinearGradient;//线性渐变器
    private Matrix mMatrix;


    public FreshTextView(Context context){
        super(context,null);
    }

    public FreshTextView(Context context, AttributeSet attributeSet){
        super(context,attributeSet,0);
    }

    public FreshTextView(Context context, AttributeSet attributeSet, int style){
        super(context,attributeSet,style);
    }

    //
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ()");
        if (mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0){
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{Color.BLUE,0xffffffff,Color.BLUE},
                        null,
                        Shader.TileMode.CLAMP
                );
                mPaint.setShader(mLinearGradient);
                mMatrix = new Matrix();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ()");
        super.onDraw(canvas);
        if (mLinearGradient != null){
            mTranslate += mViewWidth/5;
            if (mTranslate > 2*mViewWidth){
                mTranslate = -mViewWidth;
            }
            //闪光
            mMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(100);
        }
    }
}
