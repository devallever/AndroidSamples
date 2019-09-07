package com.allever.mysimple.FirstAndroid.chapter6DataPersistence;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by allever on 17-3-25.
 */

public class User extends DataSupport {
    private int id;
    private String username;
    private String nickname;
    private double height;
    private Date birthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double heigh) {
        this.height = heigh;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
