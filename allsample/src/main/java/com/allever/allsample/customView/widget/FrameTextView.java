package com.allever.allsample.customView.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.allever.allsample.R;

/**
 * Created by allever on 17-8-5.
 */

public class FrameTextView extends AppCompatTextView {

    private Paint mFramePaintOut;
    private Paint mFramePaintIn;

    public FrameTextView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        mFramePaintOut = new Paint();
        mFramePaintOut.setColor(getResources().getColor(R.color.colorPrimary));
        mFramePaintOut.setStyle(Paint.Style.FILL);
        mFramePaintIn = new Paint();
        mFramePaintIn.setColor(Color.WHITE);
        mFramePaintIn.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mFramePaintOut);
        canvas.drawRect(10,10,getMeasuredWidth()-10, getMeasuredHeight()-10,mFramePaintIn);
        canvas.save();
        //绘制文字前右下平移10px
        canvas.translate(10,10);
        super.onDraw(canvas);
        canvas.restore();

    }
}
