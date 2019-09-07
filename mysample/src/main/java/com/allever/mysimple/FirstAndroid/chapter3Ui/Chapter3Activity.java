package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/3/2.
 */

public class Chapter3Activity extends FirstAndroidBaseActivity implements View.OnClickListener {
    private Button btn_widget;
    private Button btn_layout;
    private Button btn_custome;

    private Button btn_simple_listview;
    private Button btn_fix_listview;

    private Button btn_recycler_view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_android_chapter3_activity_layout);

        btn_widget = (Button)findViewById(R.id.id_first_android_chapter_3_btn_widget);
        btn_widget.setOnClickListener(this);

        btn_layout = (Button)findViewById(R.id.id_first_android_chapter_3_btn_layout);
        btn_layout.setOnClickListener(this);

        btn_custome = (Button)findViewById(R.id.id_first_android_chapter_3_btn_custom_view);
        btn_custome.setOnClickListener(this);

        btn_simple_listview = (Button)findViewById(R.id.id_first_android_chapter_3_btn_simple_list_view);
        btn_simple_listview.setOnClickListener(this);

        btn_fix_listview = (Button)findViewById(R.id.id_first_android_chapter_3_btn_fix_list_view);
        btn_fix_listview.setOnClickListener(this);

        btn_recycler_view = (Button)findViewById(R.id.id_first_android_chapter_3_btn_recycler_view);
        btn_recycler_view.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        if (id == R.id.id_first_android_chapter_3_btn_widget) {
            intent = new Intent(this, WidgetActivity.class);
            startActivity(intent);
            //            case R.id.id_first_android_chapter_3_btn_layout:
//                intent  = new Intent(this,LayoutActivity.class);
//                startActivity(intent);
//                break;
        } else if (id == R.id.id_first_android_chapter_3_btn_custom_view) {
            intent = new Intent(this, CustomActivity.class);
            startActivity(intent);
        } else if (id == R.id.id_first_android_chapter_3_btn_simple_list_view) {
            intent = new Intent(this, SimpleListViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.id_first_android_chapter_3_btn_fix_list_view) {
            intent = new Intent(this, FixListViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.id_first_android_chapter_3_btn_recycler_view) {
            intent = new Intent(this, RecyclerViewActivity.class);
            startActivity(intent);
        }
    }
}
