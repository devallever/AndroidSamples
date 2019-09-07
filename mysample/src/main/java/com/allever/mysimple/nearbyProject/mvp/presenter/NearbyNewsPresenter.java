package com.allever.mysimple.nearbyProject.mvp.presenter;

import com.allever.mysimple.nearbyProject.pojo.NearbyNewsItem;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsRoot;

import java.util.List;

/**
 * Created by Allever on 2017/1/21.
 */

public interface NearbyNewsPresenter {
    void getNearbyNews(String longitude,String latitude,String page);

    void getNextPageNearbyNews(String longitude,String latitude,String page);

    //
    List<NearbyNewsItem> getDisplayNewsData(NearbyNewsRoot nearbyNewsRoot);
    List<NearbyNewsItem> getDisplayNextNewsData(List<NearbyNewsItem> listNearbyNewsItem, NearbyNewsRoot nearbyNewsRoot);
}
