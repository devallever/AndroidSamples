package com.allever.mysimple.nearbyProject.mvp.view;

import com.allever.mysimple.mvpTest.bean.User;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsItem;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsRoot;

import java.util.List;

/**
 * Created by Allever on 2017/1/21.
 */

public interface NearbyNewsView {
    void showProgressDialog();
    void hideProgressDialog();
    void displayNearbyNews(NearbyNewsRoot nearbyNewsRoot);
    void displayNextNearbyNewsPage(NearbyNewsRoot nearbyNewsRoot);
    void showErrorMessage(String message);
}
