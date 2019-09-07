package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.ListView;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2017/3/7.
 */

public class FixListViewActivity extends FirstAndroidBaseActivity {
    private ListView listView;
    private FruitBaseAdapter adapter;
    private List<Fruit> fruits = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fix_listview_activity_layout);

        fruits.add(new Fruit(R.mipmap.grape,"Apple"));
        fruits.add(new Fruit(R.mipmap.banana,"Banana"));
        fruits.add(new Fruit(R.mipmap.grape,"Grape"));
        fruits.add(new Fruit(R.mipmap.cheap,"Cheap"));
        fruits.add(new Fruit(R.mipmap.expensive,"expensive"));
        fruits.add(new Fruit(R.mipmap.apple,"Apple"));
        fruits.add(new Fruit(R.mipmap.banana,"Banana"));
        fruits.add(new Fruit(R.mipmap.grape,"Grape"));
        fruits.add(new Fruit(R.mipmap.cheap,"Cheap"));
        fruits.add(new Fruit(R.mipmap.expensive,"expensive"));
        fruits.add(new Fruit(R.mipmap.apple,"Apple"));
        fruits.add(new Fruit(R.mipmap.banana,"Banana"));
        fruits.add(new Fruit(R.mipmap.grape,"Grape"));
        fruits.add(new Fruit(R.mipmap.cheap,"Cheap"));
        fruits.add(new Fruit(R.mipmap.expensive,"expensive"));
        fruits.add(new Fruit(R.mipmap.apple,"Apple"));
        fruits.add(new Fruit(R.mipmap.banana,"Banana"));
        fruits.add(new Fruit(R.mipmap.grape,"Grape"));
        fruits.add(new Fruit(R.mipmap.cheap,"Cheap"));
        fruits.add(new Fruit(R.mipmap.expensive,"expensive"));

        listView = (ListView)findViewById(R.id.id_fix_listview_activity_list_view);
        adapter = new FruitBaseAdapter(this,fruits);
        listView.setAdapter(adapter);

    }
}
