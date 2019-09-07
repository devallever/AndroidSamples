package com.allever.allsample.mvp.view;

import com.allever.allsample.mvp.bean.UserBean;

/**
 * Created by allever on 17-10-11.
 */

public interface IUserView {
    UserBean getUser(int id);
    void setName(String name);
    void setAge(int age);
}
