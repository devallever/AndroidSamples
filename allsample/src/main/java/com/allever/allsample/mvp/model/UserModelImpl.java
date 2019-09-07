package com.allever.allsample.mvp.model;

import com.allever.allsample.mvp.bean.UserBean;

/**
 * Created by allever on 17-10-11.
 */

public class UserModelImpl implements IUserModel {
    private UserBean mUser;
    public UserModelImpl(){
        mUser = new UserBean();
    }
    @Override
    public int getUserId() {
        return mUser.getId();
    }

    @Override
    public UserBean getUser(int id) {
        return mUser;
    }

    @Override
    public void setAge(int age) {
        mUser.setAge(age);
    }

    @Override
    public void setName(String name) {
        mUser.setName(name);
    }


    @Override
    public void setId(int id) {
        mUser.setId(id);
    }
}
