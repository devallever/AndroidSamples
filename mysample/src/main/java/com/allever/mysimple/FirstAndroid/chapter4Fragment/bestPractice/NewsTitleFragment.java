package com.allever.mysimple.FirstAndroid.chapter4Fragment.bestPractice;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allever.mysimple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by allever on 17-3-13.
 */

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag,container,false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.id_news_title_frag_news_title_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NewsAdapter newsAdapter = new NewsAdapter(getNews());
        recyclerView.setAdapter(newsAdapter);
        return view;
    }

    private List getNews(){
        List<News> list = new ArrayList<>();
        for(int i=1; i<=50; i++){
            News news = new News();
            news.setTitle("This is title" + i);
            news.setContent(getRandomLengthContent("This is content" + i + "."));
            list.add(news);
        }
        return list;
    }

    private String getRandomLengthContent(String content){
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.id_news_activity_news_content_layout)!=null){
            isTwoPage = true;// double page
        }else{
            isTwoPage = false;
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
        private List<News> mNewsList;
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv_title;
            public ViewHolder(View itemView){
                super(itemView);
                tv_title = (TextView) itemView.findViewById(R.id.id_news_item_tv_news_title);
            }
        }

        public NewsAdapter(List<News> list){
            this.mNewsList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = mNewsList.get(viewHolder.getAdapterPosition());
                    if (isTwoPage){
                        NewsContentFragment newsContentFragment = (NewsContentFragment)getFragmentManager().findFragmentById(R.id.id_news_activity_news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());
                    }else{
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.tv_title.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}
