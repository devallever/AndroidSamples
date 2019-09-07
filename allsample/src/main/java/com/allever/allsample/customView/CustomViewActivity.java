package com.allever.allsample.customView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;
import com.allever.allsample.customView.widget.HiddenButton;
import com.allever.allsample.customView.widget.TitleBar;

/**
 * Created by allever on 17-7-26.
 */

public class CustomViewActivity extends BaseActivity {
    private static final String TAG = "CustomViewActivity";
    private TitleBar titleBar;
    private HiddenButton hiddenButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_activity_layout);

        titleBar = (TitleBar)findViewById(R.id.id_custom_view_activity_title_bar);
        titleBar.setTitle("Hello");
        titleBar.setOnTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void leftClick() {
                //finish();
                showToast("Left");
                finish();
            }

            @Override
            public void rightClick() {
                showToast("right");
            }
        });

        hiddenButton = (HiddenButton)findViewById(R.id.id_custom_view_activity_hidden_button);
        hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenButton.startScroll();
            }
        });

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,CustomViewActivity.class);
        context.startActivity(intent);
    }
}
