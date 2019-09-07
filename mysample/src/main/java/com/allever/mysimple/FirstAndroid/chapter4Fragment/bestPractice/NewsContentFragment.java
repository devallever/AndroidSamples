package com.allever.mysimple.FirstAndroid.chapter4Fragment.bestPractice;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allever.mysimple.R;

/**
 * Created by allever on 17-3-13.
 */

public class NewsContentFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    public void refresh(String title, String content){
        View visibilityLayout = view.findViewById(R.id.id_news_content_frag_visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView tv_title = (TextView)visibilityLayout.findViewById(R.id.id_news_content_frag_tv_news_title);
        TextView tv_content = (TextView)visibilityLayout.findViewById(R.id.id_news_content_frag_tv_news_content);
        tv_title.setText(title);
        tv_content.setText(content);
    }
}
