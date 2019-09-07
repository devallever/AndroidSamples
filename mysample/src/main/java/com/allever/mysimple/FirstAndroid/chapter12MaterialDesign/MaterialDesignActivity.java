package com.allever.mysimple.FirstAndroid.chapter12MaterialDesign;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.matetialTest.adapter.FruitAdapter;
import com.allever.mysimple.matetialTest.bean.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by allever on 17-4-21.
 */

public class MaterialDesignActivity extends FirstAndroidBaseActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_activity_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.id_material_design_activity_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.id_material_design_activity_drawer_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_36dp);
        }

        navigationView = (NavigationView)findViewById(R.id.id_material_design_navigation_view);
        navigationView.setCheckedItem(R.id.id_nav_menu_item_like);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.id_nav_menu_item_like) {
                    Toast.makeText(MaterialDesignActivity.this, "Like", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.id_nav_menu_item_account) {
                    Toast.makeText(MaterialDesignActivity.this, "Account", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.id_nav_menu_item_alarm) {
                    Toast.makeText(MaterialDesignActivity.this, "Alarm", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.id_nav_menu_item_setting) {
                    Toast.makeText(MaterialDesignActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();

        fab = (FloatingActionButton)findViewById(R.id.id_material_design_activity_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Remind!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("I know.", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MaterialDesignActivity.this,"OK", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        initFruit();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.id_material_design_activity_recycler_view);
        fruitAdapter = new FruitAdapter(fruitList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(fruitAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.id_material_design_activity_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorGreen700);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //fruitList.clear();
                //fruitAdapter.notifyDataSetChanged();
                refreshFruit();
            }
        });

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.id_material_design_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.id_bottom_navigation_menu_home) {
                    Toast.makeText(MaterialDesignActivity.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.id_bottom_navigation_menu_music) {
                    Toast.makeText(MaterialDesignActivity.this, "Music", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.id_bottom_navigation_menu_photo) {
                    Toast.makeText(MaterialDesignActivity.this, "Photo", Toast.LENGTH_SHORT).show();
                }
                return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_menu_notification) {
            Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.id_menu_sms) {
            Toast.makeText(this, "SMS", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.id_menu_person) {
            Toast.makeText(this, "Contacts", Toast.LENGTH_SHORT).show();
        } else if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    private void initFruit(){
        fruitList.clear();
        for (int i=0; i<20; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

}
