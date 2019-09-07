package com.allever.allsample.customView.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allever.allsample.R;


/**
 * Created by allever on 17-8-31.
 */

public class HiddenButton extends ViewGroup {

    private Context mContext;

    private static final String TAG = "HiddenButton";

    private int mWidth;//控件宽度
    private int mHeight;//控件高度
    private int mCenter;//圆的半径
    //偏移量/变化量最好不要用int
    private float mX;//右边圆心x坐标,矩形右边x坐标,//收缩/展开变化量
    private float mRingWidth = 20;//小圆到大圆的圆环宽度
    private float mRightWidth_mX;//定值

    private static final int INCREASE = 0x01;
    private static final int DECREASE = 0x02;
    private boolean isIncrease = true;

    private Paint mCirclePaint;
    private Paint mSmallCirclePaint;

    private View mChild;
    private int mChildWidth;
    private int mChildHeight;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case INCREASE:
                    //mX += 30;
                    if ( mX < mWidth-mCenter){
                        mX += 30;
                        Log.d(TAG, "handleMessage: mRightWidth_mX = " + mRightWidth_mX);
                        //mRightWidth_mX是定值
                        mRingWidth = mRightWidth_mX*mX;
                        sendEmptyMessageDelayed(INCREASE,1);
                    }else {
                        mRingWidth = 20;
                        mX = mWidth - mCenter;
                        setEnabled(true);
                    }
                    break;
                case DECREASE:
                    //mX -= 30;
                    if (mX >= mCenter + 30){
                        mX -= 30;
                        Log.d(TAG, "handleMessage: mRightWidth_mX = " + mRightWidth_mX);
                        //随着mX的减小,mRingWidth圆环宽度也减小,即内部圆变大,根据比值变化，mX减少到最小值，
                        // mRingWidth也减少到最小值，使得两个类型的变换同步
                        mRingWidth = mRightWidth_mX*mX;
                        sendEmptyMessageDelayed(DECREASE,1);
                    }else {
                        mX = mCenter;//还原初始值
                        mRingWidth = 0;
                        setEnabled(true);
                    }
                    break;
            }
            invalidate();
        }
    };

    public HiddenButton(Context context){
        super(context);
        mContext = context;
        init();
    }
    public HiddenButton(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        mContext = context;
        init();
    }

    public HiddenButton(Context context, AttributeSet attributeSet,int deftStyle){
        super(context,attributeSet,deftStyle);
        mContext = context;
        init();
    }

    private void init(){
        mCirclePaint = new Paint();
        mSmallCirclePaint = new Paint();

        TextView tv = new TextView(mContext);
        tv.setText("文字");
        tv.setTextSize(16f);
        addView(tv);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mWidth == 0){
            //第一次测量
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
            Log.d(TAG, "onMeasure: mWidth = " + mWidth);
            Log.d(TAG, "onMeasure: mHeight = " + mHeight);

            //获取圆的半径
            mCenter = mHeight/2;//定值
            //矩形右边x坐标/
            mX = mWidth - mCenter;//定值

            mRightWidth_mX = mRingWidth/mX;//定值

            //获取子控件TextView
            mChild = getChildAt(0);
            measureChild(mChild,widthMeasureSpec,heightMeasureSpec);
            mChildWidth = mChild.getMeasuredWidth();
            mChildHeight = mChild.getMeasuredHeight();
            Log.d(TAG, "onMeasure: mChildWidth = " + mChildWidth);
            Log.d(TAG, "onMeasure: mChildHeight = " + mChildHeight);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mCirclePaint.setColor(getResources().getColor(R.color.colorAccent));
        //画左边圆:圆心(mCenter,mCenter) 半径:mCenter
        canvas.drawCircle(mCenter,mCenter,mCenter,mCirclePaint);
        //画右边圆:圆心(mWidth-mCenter,mCenter) 半径:mCenter
        canvas.drawCircle(mX,mCenter,mCenter,mCirclePaint);
        //矩形left:mCenter, top:0, right:mWidth-mCenter, bottom:mHeight
        canvas.drawRect(mCenter,0,mX,mHeight,mCirclePaint);
        mSmallCirclePaint.setColor(getResources().getColor(R.color.black));
        //左边小圆:圆心(mCenter,mCenter) 半径: mCenter-mRingWidth
        Log.d(TAG, "dispatchDraw: mCenter-mRingWidth = " + (mCenter-mRingWidth));
        canvas.drawCircle(mCenter,mCenter,mCenter-mRingWidth,mSmallCirclePaint);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    //展开
    private void startIncrease(){
        setEnabled(false);//执行动画期间不可以点击操作
        mHandler.sendEmptyMessageDelayed(INCREASE,50);
    }

    //收缩
    private void startDecrease(){
        setEnabled(false);//执行动画期间不可以点击操作
        mHandler.sendEmptyMessageDelayed(DECREASE,50);
    }

    //外部调用
    public void startScroll(){
        if (isIncrease){
            //当前是展开状态，执行收缩动画
            startDecrease();
        }else {
            //当前是收缩状态，执行展开动画
            startIncrease();
        }
        isIncrease = !isIncrease;
    }
}
