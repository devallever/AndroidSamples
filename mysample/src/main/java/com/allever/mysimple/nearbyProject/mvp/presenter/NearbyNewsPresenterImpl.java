package com.allever.mysimple.nearbyProject.mvp.presenter;

import android.nfc.Tag;
import android.util.Log;

import com.allever.mysimple.mvpTest.bean.User;
import com.allever.mysimple.nearbyProject.mvp.model.NearbyNewsModelImpl;
import com.allever.mysimple.nearbyProject.mvp.view.NearbyNewsView;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsItem;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsRoot;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Allever on 2017/1/21.
 */

public class NearbyNewsPresenterImpl implements NearbyNewsPresenter{

    private static final String BASE_URL = "http://192.168.23.1:8080/SocialServer";
    //private static final String BASE_URL = "http://27.54.249.252:8080/SocialServer";
    private static final String TAG = "NearbyNewsPresenterImpl";
    private NearbyNewsView nearbyNewsView;
    private NearbyNewsModelImpl nearbyNewsModel;

    public NearbyNewsPresenterImpl(NearbyNewsView nearbyNewsView){
        this.nearbyNewsView = nearbyNewsView;
        nearbyNewsModel = new NearbyNewsModelImpl();
    }
    @Override
    public void getNearbyNews(String longitude, String latitude, String page) {
        Log.d(TAG, "NearbyNewsPresenterImpl->getNearbyNews: ");
        nearbyNewsView.showProgressDialog();
        nearbyNewsModel.getNearbyNews(new Subscriber<NearbyNewsRoot>() {
            @Override
            public void onCompleted() {
                nearbyNewsView.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                nearbyNewsView.showErrorMessage("加载失败..请重试");
            }

            @Override
            public void onNext(NearbyNewsRoot nearbyNewsRoot) {
                nearbyNewsView.displayNearbyNews(nearbyNewsRoot);
            }
        },longitude,latitude,page);
    }


    @Override
    public List<NearbyNewsItem> getDisplayNewsData(NearbyNewsRoot nearbyNewsRoot) {
        List<NearbyNewsItem> list = new ArrayList<>();
        NearbyNewsItem nearbyNewsItem;
        for (NearbyNewsRoot.NearbyNewsData nearbyNewsData : nearbyNewsRoot.getNews_list()){
            nearbyNewsItem = new NearbyNewsItem();
            Log.d(TAG, "head path = " + nearbyNewsData.getUser_head_path());
            nearbyNewsItem.setContent(nearbyNewsData.getContent());
            nearbyNewsItem.setNickname(nearbyNewsData.getNickname());
            nearbyNewsItem.setHead_path(BASE_URL + nearbyNewsData.getUser_head_path());
            if (nearbyNewsData.getNews_image_path()==null || nearbyNewsData.getNews_image_path().size()==0)
                nearbyNewsItem.setNews_img(BASE_URL + nearbyNewsData.getUser_head_path());
            else nearbyNewsItem.setNews_img(BASE_URL + nearbyNewsData.getNews_image_path().get(0));
            list.add(nearbyNewsItem);
        }
        return list;
    }

    @Override
    public List<NearbyNewsItem> getDisplayNextNewsData(List<NearbyNewsItem> listNearbyNewsItem, NearbyNewsRoot nearbyNewsRoot) {
        NearbyNewsItem nearbyNewsItem;
        for (NearbyNewsRoot.NearbyNewsData nearbyNewsData : nearbyNewsRoot.getNews_list()){
            nearbyNewsItem = new NearbyNewsItem();
            Log.d(TAG, "head path = " + nearbyNewsData.getUser_head_path());
            nearbyNewsItem.setContent(nearbyNewsData.getContent());
            nearbyNewsItem.setNickname(nearbyNewsData.getNickname());
            nearbyNewsItem.setHead_path(BASE_URL + nearbyNewsData.getUser_head_path());
            if (nearbyNewsData.getNews_image_path()==null || nearbyNewsData.getNews_image_path().size()==0)
                nearbyNewsItem.setNews_img(BASE_URL + nearbyNewsData.getUser_head_path());
            else nearbyNewsItem.setNews_img(BASE_URL + nearbyNewsData.getNews_image_path().get(0));
            listNearbyNewsItem.add(nearbyNewsItem);
        }
        return listNearbyNewsItem;
    }

    @Override
    public void getNextPageNearbyNews(String longitude, String latitude, String page) {
        Log.d(TAG, "NearbyNewsPresenterImpl->getNearbyNews: ");
        nearbyNewsView.showProgressDialog();
        nearbyNewsModel.getNearbyNews(new Subscriber<NearbyNewsRoot>() {
            @Override
            public void onCompleted() {
                nearbyNewsView.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                nearbyNewsView.showErrorMessage("加载失败..请重试");
            }

            @Override
            public void onNext(NearbyNewsRoot nearbyNewsRoot) {
                nearbyNewsView.displayNextNearbyNewsPage(nearbyNewsRoot);
            }
        },longitude,latitude,page);
    }
}
