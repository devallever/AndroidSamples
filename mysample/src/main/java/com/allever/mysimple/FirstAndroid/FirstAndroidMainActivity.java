package com.allever.mysimple.FirstAndroid;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.FirstAndroid.chapter10Service.ServiceMainActivity;
import com.allever.mysimple.FirstAndroid.chapter12MaterialDesign.MaterialDesignActivity;
import com.allever.mysimple.FirstAndroid.chapter2.FirstActivity;
import com.allever.mysimple.FirstAndroid.chapter3Ui.Chapter3Activity;
import com.allever.mysimple.FirstAndroid.chapter4Fragment.FragmentDemoActivity;
import com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver.BroadcastReceiverActivity;
import com.allever.mysimple.FirstAndroid.chapter6DataPersistence.PersistenceActivity;
import com.allever.mysimple.FirstAndroid.chapter8Media.MediaActivity;
import com.allever.mysimple.FirstAndroid.chapter9Network.NetWorkMainActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.main.adapter.MyRecyclerViewAdapter;
import com.allever.mysimple.main.bean.ListItem;
import com.allever.mysimple.main.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2017/2/26.
 */

public class FirstAndroidMainActivity extends BaseActivity {
    private List<ListItem> listItems;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_android_main_layout);

        initData();
        initView();

    }
    private void initData(){
        listItems = new ArrayList<>();
        listItems.add(new ListItem("Hello Android", "Hello Android."));
        listItems.add(new ListItem("Chapter 2 Demo", "chapter 2 demo:"));
        listItems.add(new ListItem("Chapter 3 Demo", "chapter 3 demo:basic ui"));
        listItems.add(new ListItem("Chapter 4 Demo", "chapter 4 demo:fragment"));
        listItems.add(new ListItem("Chapter 5 Demo", "chapter 5 demo:broadcast Receiver"));
        listItems.add(new ListItem("Chapter 6 Demo", "chapter 6 demo:file, share_preference, sql_lite"));
        listItems.add(new ListItem("Chapter 8 Demo", "chapter 8 demo:notification, camera, audio,video"));
        listItems.add(new ListItem("Chapter 9 Demo", "chapter 9 demo:web view, httpurlconnection, okhttp, " +
                "json, retrofit..."));
        listItems.add(new ListItem("Chapter 10 Demo", "chapter 10 demo: Handler, AsyncTack, Service"));
        listItems.add(new ListItem("Chapter 12 Demo", "chapter 12 demo: Material Design;"));
    }

    private void initView(){

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        this.setToolbar(toolbar,"FirstAndroid");
        recyclerView = (RecyclerView)this.findViewById(R.id.id_first_android_main_recycler_view);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this,listItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(FirstAndroidMainActivity.this,listItems.get(position).getDescription(),Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (position){
                    case 0:
                        break;
                    case 1:
                        intent = new Intent(FirstAndroidMainActivity.this, FirstActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(FirstAndroidMainActivity.this, Chapter3Activity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(FirstAndroidMainActivity.this, FragmentDemoActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(FirstAndroidMainActivity.this, BroadcastReceiverActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent  = new Intent(FirstAndroidMainActivity.this, PersistenceActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(FirstAndroidMainActivity.this, MediaActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(FirstAndroidMainActivity.this, NetWorkMainActivity.class);
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(FirstAndroidMainActivity.this, ServiceMainActivity.class);
                        startActivity(intent);
                        break;
                    case 9:
                        intent  = new Intent(FirstAndroidMainActivity.this, MaterialDesignActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
}
