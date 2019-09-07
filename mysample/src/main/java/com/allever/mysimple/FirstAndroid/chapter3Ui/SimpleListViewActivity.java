package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/3/7.
 */

public class SimpleListViewActivity extends FirstAndroidBaseActivity {
    private ListView listView;
    private String[] title = {"Java", "Android","JavaScript","Java", "Android","JavaScript",
            "Java", "Android","JavaScript",
            "Java", "Android","JavaScript",
            "Java", "Android","JavaScript",
            "Java", "Android","JavaScript",};

    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_listview_activity_layout);

        listView = (ListView)findViewById(R.id.id_simple_listview_activity_list_view);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,title);
        listView.setAdapter(adapter);
    }
}
