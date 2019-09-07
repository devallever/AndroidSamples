package com.allever.mysimple.nearbyProject.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.allever.mysimple.R;
import com.allever.mysimple.nearbyProject.adapter.NearbyNewsBaseAdapter;
import com.allever.mysimple.nearbyProject.listener.LoadDataScrollController;
import com.allever.mysimple.nearbyProject.mvp.presenter.NearbyNewsPresenterImpl;
import com.allever.mysimple.nearbyProject.mvp.view.NearbyNewsView;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsItem;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsRoot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2017/1/21.
 */

public class NearbyNewsFragment extends Fragment implements NearbyNewsView,LoadDataScrollController.OnRecycleRefreshListener{

    private RecyclerView recyclerView;
    private NearbyNewsBaseAdapter nearbyNewsBaseAdapter;

    private int page = 1;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ProgressDialog progressDialog;
    private NearbyNewsPresenterImpl nearbyNewsPresenter;
    private List<NearbyNewsItem> listNearbyNewsItem = new ArrayList<>();

    private LoadDataScrollController mController;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nearby_news_fg_layout,container,false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在加载...");

        recyclerView  = (RecyclerView)view.findViewById(R.id.id_nearby_news_fg_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        nearbyNewsBaseAdapter = new NearbyNewsBaseAdapter(getActivity(),listNearbyNewsItem);
//        recyclerView.setAdapter(nearbyNewsBaseAdapter);

        nearbyNewsPresenter = new NearbyNewsPresenterImpl(this);
        nearbyNewsPresenter.getNearbyNews("113.00001","22.0003",page +"");

        mController = new LoadDataScrollController(this);
        recyclerView.addOnScrollListener(mController);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_nearby_news_fg_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(mController);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorGreen700, R.color.colorGreen700,
                R.color.colorGreen700, R.color.colorGreen700);

        return view;
    }

    @Override
    public void showProgressDialog() {
        //progressDialog.show();
        //swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
//        if (progressDialog.isShowing()){
//            progressDialog.hide();
//        }
//        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        //progressDialog.hide();
        //swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayNearbyNews(NearbyNewsRoot nearbyNewsRoot) {
        //传统做法：返回数据
        listNearbyNewsItem = nearbyNewsPresenter.getDisplayNewsData(nearbyNewsRoot);
        nearbyNewsBaseAdapter = new NearbyNewsBaseAdapter(getActivity(),listNearbyNewsItem);
        recyclerView.setAdapter(nearbyNewsBaseAdapter);
        swipeRefreshLayout.setRefreshing(false);
        //nearbyNewsBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayNextNearbyNewsPage(NearbyNewsRoot nearbyNewsRoot) {
        listNearbyNewsItem = nearbyNewsPresenter.getDisplayNextNewsData(listNearbyNewsItem,nearbyNewsRoot);
        nearbyNewsBaseAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


    /*
     * OnRecycleRefreshListener回调接口方法
     * */
    @Override
    public void loadMore() {
        page++;
        nearbyNewsPresenter.getNextPageNearbyNews("112.0003","23.001",page+"");
    }
    /*
     * OnRecycleRefreshListener回调接口方法
     * */
    @Override
    public void refresh() {
        page=1;
        nearbyNewsPresenter.getNearbyNews("113.00001","22.0003",page +"");
    }
}
