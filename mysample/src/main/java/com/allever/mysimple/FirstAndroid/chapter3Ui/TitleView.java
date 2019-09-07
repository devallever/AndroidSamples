package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allever.mysimple.R;

import java.text.AttributedCharacterIterator;

/**
 * Created by Allever on 2017/3/5.
 */

public class TitleView extends RelativeLayout {
    public TitleView(Context context, AttributeSet set){
        super(context,set);
        LayoutInflater.from(context).inflate(R.layout.title_view,this);
        Button btn_back = (Button)findViewById(R.id.id_custom_activity_btn_back);
        Button btn_edit = (Button)findViewById(R.id.id_custom_activity_btn_edit);
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
        btn_edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"You click edit",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
