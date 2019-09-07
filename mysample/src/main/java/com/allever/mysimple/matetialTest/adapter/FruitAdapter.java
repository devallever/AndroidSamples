package com.allever.mysimple.matetialTest.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allever.mysimple.R;
import com.allever.mysimple.matetialTest.FruitActivity;
import com.allever.mysimple.matetialTest.bean.Fruit;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Allever on 2017/1/17.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private List<Fruit> fruitList;
    private Context context;

    public FruitAdapter(List<Fruit> fruitList){
        this.fruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positon = viewHolder.getAdapterPosition();
                Fruit fruit = fruitList.get(positon);
                Intent intent = new Intent(context, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IAMGE_ID,fruit.getImageId());
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = fruitList.get(position);
        holder.tv_name.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(holder.iv_fruit);

    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView iv_fruit;
        TextView tv_name;
        public ViewHolder(View itemView){
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.id_fruit_item_card_view);
            iv_fruit = (ImageView)itemView.findViewById(R.id.id_fruit_item_iv_fruit);
            tv_name = (TextView)itemView.findViewById(R.id.id_fruit_item_tv_name);
        }
    }
}
