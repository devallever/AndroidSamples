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
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Allever on 2016/11/21.
 */

public class NearbyNewsBaseAdapterWithHeader extends RecyclerView.Adapter<NearbyNewsBaseAdapterWithHeader.MyViewHolder> {
    private List<NearbyNewsItem> list_newrby_news_items;
    private Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private View headerVew;
    public NearbyNewsBaseAdapterWithHeader(Context context, List<NearbyNewsItem> list_newrby_news_items){
        this.context = context;
        this.list_newrby_news_items = list_newrby_news_items;
    }

    public void setHeader(View headerView){
        this.headerVew = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView(){
        return this.headerVew;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerVew == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
        //return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return headerVew == null? list_newrby_news_items.size(): list_newrby_news_items.size()+1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_HEADER) return;

        NearbyNewsItem nearbyNewsItem = list_newrby_news_items.get(position-1);
        Glide.with(context).load(nearbyNewsItem.getNews_img()).into(holder.iv_news_img);
        Glide.with(context).load(nearbyNewsItem.getHead_path()).into(holder.iv_head);
        holder.tv_nickname.setText(nearbyNewsItem.getNickname());
        holder.tv_content.setText(nearbyNewsItem.getContent());
    }

    public int getRealPosition(RecyclerView.ViewHolder holder){
        int position = holder.getLayoutPosition();
        return headerVew == null ? position:position-1;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.nearby_news_recyclerview_header,parent,false));
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
