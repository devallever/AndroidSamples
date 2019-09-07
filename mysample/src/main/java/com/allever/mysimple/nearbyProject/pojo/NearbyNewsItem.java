package com.allever.mysimple.nearbyProject.pojo;

import android.graphics.drawable.Drawable;

/**
 * Created by Allever on 2016/11/21.
 */

public class NearbyNewsItem {

    private String nickname;
    private String content;
    private String head_path;
    private String news_img;
    private Drawable drawable_head;
    private Drawable drawable_news_img;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHead_path() {
        return head_path;
    }

    public void setHead_path(String head_path) {
        this.head_path = head_path;
    }

    public String getNews_img() {
        return news_img;
    }

    public void setNews_img(String news_img) {
        this.news_img = news_img;
    }

    public Drawable getDrawable_head() {
        return drawable_head;
    }

    public void setDrawable_head(Drawable drawable_head) {
        this.drawable_head = drawable_head;
    }

    public Drawable getDrawable_news_img() {
        return drawable_news_img;
    }

    public void setDrawable_news_img(Drawable drawable_news_img) {
        this.drawable_news_img = drawable_news_img;
    }
}
