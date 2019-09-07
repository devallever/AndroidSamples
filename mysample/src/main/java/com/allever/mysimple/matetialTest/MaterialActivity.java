package com.allever.mysimple.matetialTest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.matetialTest.adapter.FruitAdapter;
import com.allever.mysimple.matetialTest.bean.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by Allever on 2017/1/17.
 */

public class MaterialActivity extends BaseActivity {
    private static final String TAG = "MaterialActivity";

    private Fruit[] fruits = {
            new Fruit("Apple",R.mipmap.apple),
            new Fruit("Banana",R.mipmap.banana),
            new Fruit("Cheap", R.mipmap.cheap),
            new Fruit("Cheery",R.mipmap.cherry),
            new Fruit("Grape",R.mipmap.grape),
            new Fruit("Expensive",R.mipmap.expensive)
    };
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter fruitAdapter;

//    @BindView(R.id.id_material_activity_swipe_refresh_layout)
        SwipeRefreshLayout swipeRefreshLayout;
//
//    @BindView(R.id.id_material_activity_recycler_view)
//    RecyclerView recyclerView;
//
//    @BindView(R.id.id_material_activity_toolbar)
//    Toolbar toolbar;
//
//    @BindView(R.id.id_material_activity_drawer_layout)
        DrawerLayout drawerlayout;
//
//    @BindView(R.id.id_material_activity_nav_view)
//    NavigationView navigationView;
//
//    @BindView(R.id.id_material_activity_fab)
        FloatingActionButton fab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_activity_layout);

        ButterKnife.bind(this);

        initView();
    }
    private void initView(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.id_material_activity_toolbar);
        toolbar.setTitle("Material Design");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.mipmap.ic_list_white_24dp);
        }
        drawerlayout = (DrawerLayout)findViewById(R.id.id_material_activity_drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.id_material_activity_nav_view);
        navigationView.setCheckedItem(R.id.id_nav_menu_item_like);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.id_nav_menu_item_like) {
                    drawerlayout.closeDrawers();
                } else if (itemId == R.id.id_nav_menu_item_alarm) {
                    drawerlayout.closeDrawers();
                } else if (itemId == R.id.id_nav_menu_item_setting) {
                    drawerlayout.closeDrawers();
                } else if (itemId == R.id.id_nav_menu_item_account) {
                    drawerlayout.closeDrawers();
                }
                return true;
            }
        });

        fab = (FloatingActionButton)findViewById(R.id.id_material_activity_fab);
        fab.setOnClickListener(v->{
            //Toast.makeText(this,"FAB",Toast.LENGTH_LONG).show();
            Snackbar.make(v,"Remind me",Snackbar.LENGTH_LONG)
                    .setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MaterialActivity.this,"FAB",Toast.LENGTH_LONG).show();
                        }
                    }).show();
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.syncState();


        initFruit();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.id_material_activity_recycler_view);
        fruitAdapter = new FruitAdapter(fruitList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(fruitAdapter);


        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.id_material_activity_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorGreen700);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //fruitList.clear();
                //fruitAdapter.notifyDataSetChanged();
                refreshFruit();
            }
        });

    }

    private void refreshFruit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        fruitAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruit(){
        fruitList.clear();
        for (int i=0; i<20; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.marerial_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            drawerlayout.openDrawer(GravityCompat.START);
        } else if (itemId == R.id.id_material_menu_item_sms) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
        } else if (itemId == R.id.id_material_menu_item_notification) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
        } else if (itemId == R.id.id_material_menu_item_contact) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
