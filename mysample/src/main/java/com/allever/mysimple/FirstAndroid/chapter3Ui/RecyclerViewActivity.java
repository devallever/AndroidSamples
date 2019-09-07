package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2017/3/7.
 */

public class RecyclerViewActivity extends FirstAndroidBaseActivity{

    private RecyclerView recyclerView;
    private List<Fruit> fruits = new ArrayList<>();
    private RecyclerViewBaseAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_veiw_activity_layout);


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

        recyclerView = (RecyclerView)findViewById(R.id.id_recycler_view_activity_recycler_view);
        adapter = new RecyclerViewBaseAdapter(this,fruits);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
