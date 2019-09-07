package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allever.mysimple.R;

import java.util.List;

/**
 * Created by Allever on 2017/3/7.
 */

public class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerViewBaseAdapter.MyViewHolder> {
    private List<Fruit> fruits;
    private Context context;

    public RecyclerViewBaseAdapter(Context context, List<Fruit> fruits){
        this.context = context;
        this.fruits = fruits;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fruit_item_recycler_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You click View",Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You click Image",Toast.LENGTH_SHORT).show();
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.iv.setImageDrawable(context.getResources().getDrawable(fruit.getImgResId()));
        holder.tv.setText(fruit.getDescription());
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        View fruitView;

        public MyViewHolder(View itemView){
            super(itemView);
            iv = (ImageView)itemView.findViewById(R.id.id_fruit_item_recycler_view_iv);
            tv = (TextView)itemView.findViewById(R.id.id_fruit_item_recycler_view_tv);
            fruitView = itemView;
        }
    }
}
