package com.allever.mysimple.FirstAndroid.chapter3Ui;

/**
 * Created by Allever on 2017/3/7.
 */

public class Fruit {
    private int imgResId;
    private String description;

    public Fruit(int imgResId, String description){
        this.imgResId = imgResId;
        this.description = description;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
