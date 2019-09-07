package com.allever.allsample.mvp.model;

import com.allever.allsample.mvp.bean.UserBean;

/**
 * Created by allever on 17-10-11.
 */

public interface IUserModel {
    UserBean getUser(int id);
    int getUserId();
    void setId(int id);
    void setName(String name);
    void setAge(int age);
}
