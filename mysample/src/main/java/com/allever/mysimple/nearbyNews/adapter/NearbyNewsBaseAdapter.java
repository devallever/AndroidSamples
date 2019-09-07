package com.allever.mysimple.nearbyNews.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allever.mysimple.R;
import com.allever.mysimple.nearbyNews.pojo.NearbyNewsItem;

import java.util.List;

/**
 * Created by Allever on 2016/11/21.
 */

public class NearbyNewsBaseAdapter extends RecyclerView.Adapter<NearbyNewsBaseAdapter.MyViewHolder> {
    private List<NearbyNewsItem> list_newrby_news_items;
    private Context context;

    public NearbyNewsBaseAdapter(Context context, List<NearbyNewsItem> list_newrby_news_items){
        this.context = context;
        this.list_newrby_news_items = list_newrby_news_items;
    }

    @Override
    public int getItemCount() {
        return list_newrby_news_items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NearbyNewsItem nearbyNewsItem = list_newrby_news_items.get(position);
        //Glide.with(context).load(nearbyNewsItem.getNews_img()).into(holder.iv_news_img);
        holder.iv_head.setImageDrawable(nearbyNewsItem.getDrawable_head());
        holder.iv_news_img.setImageDrawable(nearbyNewsItem.getDrawable_news_img());
        //Glide.with(context).load(nearbyNewsItem.getHead_path()).into(holder.iv_head);
        holder.tv_nickname.setText(nearbyNewsItem.getNickname());
        holder.tv_content.setText(nearbyNewsItem.getContent());
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.nearby_news_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_news_img;
        ImageView iv_head;
        TextView tv_content;
        TextView tv_nickname;
        public MyViewHolder(View itemView){
            super(itemView);
            iv_head = (ImageView)itemView.findViewById(R.id.id_nearby_news_item_iv_head);
            iv_news_img = (ImageView)itemView.findViewById(R.id.id_nearby_news_item_iv_news_img);
            tv_content = (TextView)itemView.findViewById(R.id.id_nearby_news_item_tv_content);
            tv_nickname = (TextView)itemView.findViewById(R.id.id_nearby_news_item_tv_nickname);
        }
    }
}
