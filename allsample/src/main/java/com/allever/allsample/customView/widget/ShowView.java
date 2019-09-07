package com.allever.allsample.customView.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.allever.allsample.R;
import com.allever.allsample.util.ViewUtil;

import retrofit2.http.PUT;

/**
 * Created by allever on 17-8-5.
 */

public class ShowView extends View {

    private Paint mOutCirclePaint;
    private Paint mInCirclePaint;
    private Paint mTextPaint;

    private float centerXY;
    private float mLength;


    private float r;

    public ShowView(Context context){
        super(context);
    }

    public ShowView(Context context, AttributeSet attributeSet){
        super(context,attributeSet,0);

        mOutCirclePaint = new Paint();
        mOutCirclePaint.setColor(getResources().getColor(R.color.colorPrimary));
        mOutCirclePaint.setStyle(Paint.Style.STROKE);

        mInCirclePaint = new Paint();
        mInCirclePaint.setColor(Color.RED);
        mInCirclePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.YELLOW);
        mTextPaint.setTextSize(20f);





    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(ViewUtil.measureWidth(widthMeasureSpec),ViewUtil.measureHeight(heightMeasureSpec));
        mLength = Math.min(getMeasuredHeight(),getMeasuredWidth());
        centerXY = mLength/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerXY,centerXY,mLength/4,mInCirclePaint);

        canvas.drawArc(new RectF(
                mLength*0.1F,
                mLength*0.1F,
                mLength*0.9F,
                mLength*0.9F

        ),270,270,false,mOutCirclePaint);

        String showText = "Hello";
        //canvas.drawText("Hello", 0, showText.length(),centerXY,centerXY+(showText));
    }
}
