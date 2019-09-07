package com.allever.mysimple.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2016/11/7.
 */

public class AnimatorTestActivity extends BaseActivity {
    private static final String TAG = "AnimatorTestActivity";

    private FloatingActionButton btnMain;

    private TextView tv_alpha;
    private TextView tv_rotate;
    private TextView tv_translationX;
    private TextView tv_translationY;
    private TextView tv_scaleX;
    private TextView tv_animator_set;
    private TextView tv_animator_set_xml;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator_test_activity_layout);

/*        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        initView();

        //ValueAnimator
        //startValueAnimator();

        //ObjectAnimator
        //startObjectAnimator();

    }

    private void initView(){
        tv_alpha = (TextView)this.findViewById(R.id.id_animator_test_activity_tv_alpha);
        tv_rotate = (TextView)this.findViewById(R.id.id_animator_test_activity_tv_rotate);
        tv_translationX = (TextView)this.findViewById(R.id.id_animator_test_activity_tv_translation_x);
        tv_translationY = (TextView)this.findViewById(R.id.id_animator_test_activity_tv_translation_y);
        tv_scaleX = (TextView)this.findViewById(R.id.id_animator_test_activity_tv_scale_x);
        tv_animator_set = (TextView)this.findViewById(R.id.id_animator_test_activity_tv_animator_set);
        tv_animator_set_xml = (TextView)this.findViewById(R.id.id_animator_test_activity_tv_animator_set_xml);


        tv_animator_set_xml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimatorSetXML();
            }
        });

        tv_animator_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimatorSet();
            }
        });

        tv_scaleX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startObjectAnimatorScaleX();
            }
        });

        tv_translationY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startObjectAnimatorTranslationY();
            }
        });

        tv_translationX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startObjectAnimatorTranslationX();
            }
        });

        tv_alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startObjectAnimatorAlpha();
            }
        });
        tv_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startObjectAnimatorRotate();
            }
        });

    }

    private void startObjectAnimator(){

        //startObjectAnimatorAlpha();
        //startObjectAnimatorRotate();
        //startObjectAnimatorTranslationX();
        //startObjectAnimatorTranslationY();
        //startObjectAnimatorScaleX();
        //startAnimatorSet();
        //startAnimatorSetXML();

    }

    private  void startAnimatorSetXML(){
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animator_set);
        animator.setTarget(tv_animator_set_xml);
        animator.start();
    }



    private void startAnimatorSet(){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator rotation = ObjectAnimator.ofFloat(tv_animator_set,"rotation",0f, 360f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tv_animator_set,"scaleY", 1f, 3f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(tv_animator_set,"alpha", 1f, 0f, 1f);
        float curTranslationX = tv_animator_set.getTranslationX();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(tv_animator_set,"translationX",curTranslationX, -500, curTranslationX);
        animatorSet.play(rotation).with(scaleY).before(alpha);
        animatorSet.setDuration(5000);
        animatorSet.start();

        animatorSet.setInterpolator(new BounceInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void startObjectAnimatorAlpha(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv_alpha, "alpha", 1f, 0f, 1f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    private void startObjectAnimatorRotate(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv_rotate, "rotation", 1f, 360f);
        objectAnimator.setDuration(1000);
        //objectAnimator.setStartDelay(1000);
        objectAnimator.start();
    }

    private void startObjectAnimatorTranslationX(){
        float curTranslationX =  tv_translationX.getTranslationX();
        Toast.makeText(this,"curTranslationX = " + curTranslationX,Toast.LENGTH_LONG).show();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv_translationX, "translationX", curTranslationX, -500f, curTranslationX, 500f, curTranslationX);
        objectAnimator.setDuration(4000);
        //objectAnimator.setStartDelay(1000);
        objectAnimator.start();
    }

    private void startObjectAnimatorTranslationY(){
        float curTranslationY =  tv_translationX.getTranslationY();
        Toast.makeText(this,"curTranslationY = " + curTranslationY,Toast.LENGTH_LONG).show();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv_translationY, "translationY", curTranslationY, -1000f, curTranslationY, 1000f, curTranslationY);
        objectAnimator.setDuration(5000);
        //objectAnimator.setStartDelay(1000);
        objectAnimator.start();
    }

    private void startObjectAnimatorScaleX(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv_scaleX, "scaleX", 1f, 3f, 1f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }



    private void startValueAnimator(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(300);
        valueAnimator.setRepeatCount(2);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float)valueAnimator.getAnimatedValue();
                Log.d(TAG, "value = " + value);
            }
        });
    }
}
