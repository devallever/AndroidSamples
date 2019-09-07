package com.allever.mysimple.nearbyProject.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2017/1/21.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragment_list;
    private List<String> title_list;
    public ViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        fragment_list = new ArrayList<>();
        title_list = new ArrayList<>();
    }

    public void bindFragment(Fragment fragment, String title){
        fragment_list.add(fragment);
        title_list.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title_list.get(position);
    }

    @Override
    public int getCount() {
        return fragment_list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragment_list.get(position);
    }
}
