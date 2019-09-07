package com.allever.mysimple.main;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2016/12/19.
 */

public class MySimpleMainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_main_activity_layout);
        TextView tv = (TextView)findViewById(R.id.id_simple_main_tv);
        tv.setText("yyyyy");
    }
}