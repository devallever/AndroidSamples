package com.allever.mysimple.FirstAndroid.chapter4Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.FirstAndroid.chapter4Fragment.bestPractice.NewsActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/3/9.
 */

public class FragmentDemoActivity extends FirstAndroidBaseActivity {
    private Button btn_fragment_test;
    private Button btn_fragment_best_practice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_demo_activity_layout);

        btn_fragment_test = (Button)findViewById(R.id.id_fragment_demo_btn_fragment_test);
        btn_fragment_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FragmentDemoActivity.this, FragmentTestActivity.class);
                startActivity(intent);
            }
        });

        btn_fragment_best_practice = (Button)findViewById(R.id.id_fragment_demo_btn_fragment_best_practice);
        btn_fragment_best_practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FragmentDemoActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });
    }
}
