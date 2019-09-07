package com.allever.mysimple.main.bean;

/**
 * Created by Allever on 2016/11/3.
 */

public class ListItem {
    private String title;
    private String description;

    public ListItem(String title,String desc){
        this.title = title;
        this.description = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
