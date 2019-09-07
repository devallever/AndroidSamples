package com.allever.mysimple.nearbyProject.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.TaskStackBuilder;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.nearbyProject.adapter.ViewPagerAdapter;
import com.allever.mysimple.nearbyProject.ui.fragment.NearbyNewsFragment;
import com.allever.mysimple.nearbyProject.ui.fragment.NearbyUserFragment;

/**
 * Created by Allever on 2017/1/21.
 */

public class NearbyActivity  extends BaseActivity{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_activity_layout);


        initView();
        setToolbar(toolbar,"附近");
    }

    private void initView(){
        toolbar = (Toolbar)findViewById(R.id.id_toolbar);
        viewPager = (ViewPager)findViewById(R.id.id_nearby_activity_viewpager);
        tabLayout = (TabLayout)findViewById(R.id.id_nearby_activity_tab_layout);
        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        NearbyUserFragment nearbyUserFragment = new NearbyUserFragment();
        NearbyNewsFragment nearbyNewsFragment = new NearbyNewsFragment();
        viewPagerAdapter.bindFragment(nearbyUserFragment,"附近人");
        viewPagerAdapter.bindFragment(nearbyNewsFragment,"附近动态");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        drawerLayout = (DrawerLayout)findViewById(R.id.id_nearby_activity_drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name,
                        R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
