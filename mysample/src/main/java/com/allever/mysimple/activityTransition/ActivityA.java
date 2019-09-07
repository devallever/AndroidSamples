package com.allever.mysimple.activityTransition;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2016/11/23.
 */

public class ActivityA extends BaseActivity implements View.OnClickListener{

    private FloatingActionButton fab;
    private Intent intent;

    private Button btn_explode;
    private Button btn_slide;
    private Button btn_fade;
    private Button btn_share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //分解动画 minSDK 21
        //getWindow().setEnterTransition(new Explode());
        //滑动动画
        //getWindow().setEnterTransition(new Slide());
        //淡出动画
        //getWindow().setEnterTransition(new Fade());
        setContentView(R.layout.activity_a_activity_layout);

        setToolbar((Toolbar)findViewById(R.id.id_toolbar),"过度动画");

        initView();

    }

    private void initView(){
        fab = (FloatingActionButton)findViewById(R.id.id_activity_a_activity_fab);
        fab.setBackgroundColor(getResources().getColor(R.color.colorGreen700));

        intent = new Intent(this, ActivityB.class);

        btn_explode = (Button)findViewById(R.id.id_activity_a_activity_btn_explode);
        btn_slide = (Button)findViewById(R.id.id_activity_a_activity_btn_slide);
        btn_fade = (Button)findViewById(R.id.id_activity_a_activity_btn_fade);
        btn_share = (Button)findViewById(R.id.id_activity_a_activity_btn_share);

        btn_explode.setOnClickListener(this);
        btn_slide.setOnClickListener(this);
        btn_fade.setOnClickListener(this);
        btn_share.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_activity_a_activity_btn_explode) {
            intent.putExtra("flag", 0);
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else if (id == R.id.id_activity_a_activity_btn_slide) {
            intent.putExtra("flag", 1);
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else if (id == R.id.id_activity_a_activity_btn_fade) {
            intent.putExtra("flag", 2);
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else if (id == R.id.id_activity_a_activity_btn_share) {//View fab = findViewById(R.id.id_activity_a_activity_fab);
            intent.putExtra("flag", 3);
            //创建单个共享元素
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,view,"share").toBundle());
            //创建多个共享元素
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(view,"share"),Pair.create(fab,"fab")).toBundle());
        }
    }
}
