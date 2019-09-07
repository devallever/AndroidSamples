package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.allever.mysimple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2017/3/7.
 */

public class FruitBaseAdapter extends BaseAdapter {
    private List<Fruit> fruits = new ArrayList<>();
    private Context context;
    public FruitBaseAdapter(Context context, List<Fruit> fruits){
        this.context = context;
        this.fruits = fruits;
    }

    @Override
    public View getView(int i, View contView, ViewGroup viewGroup) {
        Fruit fruit = fruits.get(i);
        ViewHolder viewHolder = null;
        View view;
        if (contView == null){
            view = LayoutInflater.from(context).inflate(R.layout.fruit_item_listview,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) view.findViewById(R.id.id_fruit_item_listview_iv);
            viewHolder.tv = (TextView)view.findViewById(R.id.id_fruit_item_listview_tv);
            view.setTag(viewHolder);
        }else{
            view = contView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.iv.setImageDrawable(context.getResources().getDrawable(fruit.getImgResId()));
        viewHolder.tv.setText(fruit.getDescription());
        return view;
    }

    @Override
    public int getCount() {
        return fruits.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return fruits.get(i);
    }

    private class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
