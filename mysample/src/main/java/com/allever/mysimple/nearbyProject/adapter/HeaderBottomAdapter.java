package com.allever.mysimple.nearbyProject.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allever.mysimple.R;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsItem;

import java.util.List;

/**
 * Created by Allever on 2016/11/24.
 */

public class HeaderBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private int mHeaderCount=1;//头部View个数

    private List<NearbyNewsItem> list_newrby_news_items;
    private Context context;

    public HeaderBottomAdapter(Context context, List<NearbyNewsItem> list_newrby_news_items){
        this.context =  context;
        this.list_newrby_news_items = list_newrby_news_items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.nearby_news_recyclerview_header, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.nearby_news_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
        } else if (holder instanceof MyViewHolder) {

            NearbyNewsItem nearbyNewsItem = list_newrby_news_items.get(position-1);
            //Glide.with(context).load(nearbyNewsItem.getNews_img()).into(holder.iv_news_img);
            ((MyViewHolder) holder).iv_head.setImageDrawable(nearbyNewsItem.getDrawable_head());
            ((MyViewHolder) holder).iv_news_img.setImageDrawable(nearbyNewsItem.getDrawable_news_img());
            //Glide.with(context).load(nearbyNewsItem.getHead_path()).into(holder.iv_head);
            ((MyViewHolder) holder).tv_nickname.setText(nearbyNewsItem.getNickname());
            ((MyViewHolder) holder).tv_content.setText(nearbyNewsItem.getContent());

        }
    }


    //内容长度
    public int getContentItemCount(){
        return list_newrby_news_items.size();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + mHeaderCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        }else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    //内容 ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_news_img;
        ImageView iv_head;
        TextView tv_content;
        TextView tv_nickname;
        public MyViewHolder(View itemView){
            super(itemView);
            iv_head = (ImageView)itemView.findViewById(R.id.id_nearby_news_item_iv_head);
            iv_news_img = (ImageView)itemView.findViewById(R.id.id_nearby_news_item_iv_news_img);
            tv_content = (TextView) itemView.findViewById(R.id.id_nearby_news_item_tv_content);
            tv_nickname = (TextView)itemView.findViewById(R.id.id_nearby_news_item_tv_nickname);
        }
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
