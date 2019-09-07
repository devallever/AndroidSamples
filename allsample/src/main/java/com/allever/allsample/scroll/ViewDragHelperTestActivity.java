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

public class ViewDragHelperTestActivity  extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_drag_helper_test_activity_layout);
    }


    public static void startActivity(Context context){
        Intent intent = new Intent(context, ViewDragHelperTestActivity.class);
        context.startActivity(intent);
    }
}
