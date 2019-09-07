package com.allever.android.sample.jfit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by Jerry on 2017/9/7.
 */

@SuppressLint("AppCompatCustomView")
public class GradientRadioButton extends RadioButton {
    public GradientRadioButton(Context context) {
        super(context);
    }

    public GradientRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onLayout(boolean changed,
                            int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            getPaint().setShader(new LinearGradient(
                    0, 0, 0, getHeight(),
                    Color.parseColor("#b4fffe"), Color.parseColor("#2ebde5"),
                    Shader.TileMode.CLAMP));
        }
    }
}
