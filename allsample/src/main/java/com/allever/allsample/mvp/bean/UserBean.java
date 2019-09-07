package com.allever.allsample.mvp.bean;

import com.allever.allsample.okhttp.bean.User;

/**
 * Created by allever on 17-10-11.
 */

public class UserBean {
    private int id;
    private String name;
    private int age;

    public UserBean(){}
    public UserBean(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
