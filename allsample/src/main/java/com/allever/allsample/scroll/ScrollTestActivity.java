package com.allever.allsample.scroll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-8-12.
 */

public class ScrollTestActivity  extends BaseActivity {
    private DragViewLayout dragViewLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_test_activity_layout);


    }
    public static void startActivity(Context context){
        Intent intent = new Intent(context,ScrollTestActivity.class);
        context.startActivity(intent);
    }
}
