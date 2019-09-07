package com.allever.allsample.main.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allever.allsample.R;
import com.allever.allsample.main.bean.ListItem;

import java.util.List;

/**
 * Created by allever on 17-6-29.
 */

public class MainRecyclerBaseAdapter extends RecyclerView.Adapter<MainRecyclerBaseAdapter.MainViewHolder> {

    private Context context;
    private List<ListItem> listItems;
    private OnItemClickListener listener;
    public MainRecyclerBaseAdapter(Context context, List<ListItem> listItems){
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        MainViewHolder mainViewHolder = new MainViewHolder(view);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {
        ListItem listItem = listItems.get(position);
        holder.tv_title.setText(listItem.getTitle());
        holder.tv_description.setText(listItem.getDescription());

        if (listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.itemView,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_description;
        public MainViewHolder(View itemView){
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.id_list_item_tv_title);
            tv_description = (TextView) itemView.findViewById(R.id.id_list_item_tv_description);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
