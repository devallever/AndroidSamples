package com.allever.allsample.customView.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.Dimension;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allever.allsample.R;

/**
 * Created by allever on 17-8-5.
 */

public class TitleBar extends RelativeLayout {
    //private int mLeftTextColor;
    //private int mRightTextColor;
    //private Drawable mLeftBackground;
    //private Drawable mRightBackground;
    //private String mLeftText;
    //private String mRightText;
    private float mTitleTextSize;
    //private float mLeftTextSize;
    //private float mRightTextSize;
    private int mTitleColor;
    private String mTitle;

    //private Button mLeftButton;
    //private Button mRightButton;
    private TextView mTitleTextView;

    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    private LayoutParams mTitleParams;

    private ImageView mLeftImage;
    private ImageView mRightImage;

    private Drawable mLeftRes;
    private Drawable mRightRes;

    private TitleBarClickListener mListener;






    public TitleBar(Context context, AttributeSet attributeSet){
        super(context,attributeSet,0);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleBar);
        mTitle = typedArray.getString(R.styleable.TitleBar_title);
        //mLeftText = typedArray.getString(R.styleable.TitleBar_leftText);
        //mRightText = typedArray.getString(R.styleable.TitleBar_rightText);

        mTitleColor = typedArray.getColor(R.styleable.TitleBar_titleTextColor,0);
        //mLeftTextColor = typedArray.getColor(R.styleable.TitleBar_leftTextColor,0);
        //mRightTextColor = typedArray.getColor(R.styleable.TitleBar_rightTextColor,0);

        mTitleTextSize = typedArray.getDimension(R.styleable.TitleBar_titleSize,10);
        //mLeftBackground = typedArray.getDrawable(R.styleable.TitleBar_leftBackground);
        //mRightBackground = typedArray.getDrawable(R.styleable.TitleBar_rightBackground);

        mLeftRes = typedArray.getDrawable(R.styleable.TitleBar_leftImage);
        mRightRes = typedArray.getDrawable(R.styleable.TitleBar_rightImage);

        //回收资源
        typedArray.recycle();

        //mLeftButton = new Button(context);
        //mRightButton = new Button(context);
        mTitleTextView = new TextView(context);

        //为创建的组件元素赋值
        //值来源于xml中对应的属性的赋值
        //mLeftButton.setText(mLeftText);
        //mLeftButton.setBackground(mLeftBackground);
        //mLeftButton.setTextColor(mLeftTextColor);

        //mRightButton.setText(mRightText);
        //mRightButton.setTextColor(mRightTextColor);
        //mRightButton.setBackground(mRightBackground);

        mTitleTextView.setText(mTitle);
        mTitleTextView.setTextColor(mTitleColor);
        mTitleTextView.setTextSize(mTitleTextSize);
        mTitleTextView.setGravity(Gravity.CENTER);

        mLeftImage = new ImageView(context);
        mLeftImage.setImageDrawable(mLeftRes);

        mRightImage = new ImageView(context);
        mRightImage.setImageDrawable(mRightRes);

        //为组建元素设置相应的布局元素
        mLeftParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
        );
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(mLeftImage,mLeftParams);
        //addView(mLeftButton, mLeftParams);


        mRightParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
        );
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        //addView(mRightButton,mRightParams);
        addView(mRightImage,mRightParams);

        mTitleParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
        );
        mTitleParams.addRule(CENTER_IN_PARENT,TRUE);
        addView(mTitleTextView, mTitleParams);

        mLeftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.leftClick();
            }
        });

        mRightImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.rightClick();
            }
        });



/*        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                mListener.leftClick();
            }
        });*/

/*        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                mListener.rightClick();
            }
        });*/



    }

    public interface TitleBarClickListener{
        void leftClick();
        void rightClick();
    }

    public void setOnTitleBarClickListener(TitleBarClickListener titleBarClickListener){
        this.mListener = titleBarClickListener;
    }


    public void setButtonVisable(int id, boolean flag){
        if (flag){
            if (id == 0){
                //mLeftButton.setVisibility(VISIBLE);
                mLeftImage.setVisibility(VISIBLE);
            }else {
                //mRightButton.setVisibility(VISIBLE);
                mRightImage.setVisibility(VISIBLE);
            }
        }else {
            if (id == 0){
                //mLeftButton.setVisibility(GONE);
                mLeftImage.setVisibility(GONE);
            }else {
                //mRightButton.setVisibility(GONE);
                mRightImage.setVisibility(GONE);
            }
        }
    }

    public void setTitle(String title){
        mTitle = title;
        invalidate();
    }


}
