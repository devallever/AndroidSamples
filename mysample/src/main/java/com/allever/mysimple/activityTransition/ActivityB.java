package com.allever.mysimple.activityTransition;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.Window;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2016/11/23.
 */

public class ActivityB extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        int flag = getIntent().getExtras().getInt("flag");
        switch (flag){
            case 0:
                //getWindow().setEnterTransition(new Explode());
                break;
            case 1:
                //getWindow().setEnterTransition(new Slide());
                break;
            case 2:
                //getWindow().setEnterTransition(new Fade());
                //getWindow().setExitTransition(new Fade());
                break;
            case 3:
                break;

        }

        setContentView(R.layout.activity_b_activity_layout);

        setToolbar((Toolbar)findViewById(R.id.id_toolbar),"过渡动画");
    }
}
