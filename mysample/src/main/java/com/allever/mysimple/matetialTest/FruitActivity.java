package com.allever.mysimple.matetialTest;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;
import com.bumptech.glide.Glide;

/**
 * Created by Allever on 2017/1/18.
 */

public class FruitActivity extends BaseActivity {
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IAMGE_ID = "fruit_image_id";

//    @BindView(R.id.id_fruit_activity_toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.id_fruit_activity_collapsing_toolbar)
//    CollapsingToolbarLayout collapsingToolbarLayout;
//    @BindView(R.id.id_fruit_activity_iv_fruit)
//    ImageView iv_fruit;
//    @BindView(R.id.id_fruit_activity_tv_fruit_content)
//    TextView tv_fruit_content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_activity_layout);

        //ButterKnife.bind(this);

        Intent intent = getIntent();
        String fruit_name = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IAMGE_ID,0);
        //toolbar.setTitle(fruit_name);
        Toolbar toolbar = (Toolbar)findViewById(R.id.id_fruit_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_36dp);
        }
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.id_fruit_activity_collapsing_toolbar);
        collapsingToolbarLayout.setTitle(fruit_name);
        ImageView iv_fruit = (ImageView)findViewById(R.id.id_fruit_activity_iv_fruit);
        Glide.with(this).load(fruitImageId).into(iv_fruit);
        TextView tv_fruit_content = (TextView)findViewById(R.id.id_fruit_activity_tv_fruit_content);
        String fruit_content = generateFruitContent(fruit_name);
        tv_fruit_content.setText(fruit_content);
    }

    private String generateFruitContent(String fruit_name){
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<250; i++){
            builder.append(fruit_name);
        }
        return builder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
