package com.allever.mysimple.main.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allever.mysimple.R;
import com.allever.mysimple.main.bean.ListItem;

import java.util.List;

/**
 * Created by Allever on 2016/11/3.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<ListItem> listItems;
    public MyRecyclerViewAdapter(Context context, List<ListItem> listItems){
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.tv_title.setText(listItem.getTitle());
        holder.tv_desc.setText(listItem.getDescription());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_desc;
        public MyViewHolder(View itemView){
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.id_list_item_tv_title);
            tv_desc = (TextView)itemView.findViewById(R.id.id_list_item_tv_desc);
        }
    }
}
