package com.allever.mysimple.FirstAndroid.chapter2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/2/28.
 */

public class ThirdActivity extends FirstAndroidBaseActivity {

    private Button btn_finish_all;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_android_third_activity_layout);

        btn_finish_all = (Button)findViewById(R.id.id_first_android_third_activity_btn_finish_all);
        btn_finish_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstAndroidCollector.removeAll();
            }
        });

    }
}
