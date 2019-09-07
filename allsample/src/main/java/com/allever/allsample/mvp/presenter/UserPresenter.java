package com.allever.allsample.mvp.presenter;

import com.allever.allsample.mvp.bean.UserBean;
import com.allever.allsample.mvp.model.IUserModel;
import com.allever.allsample.mvp.model.UserModelImpl;
import com.allever.allsample.mvp.view.IUserView;

/**
 * Created by allever on 17-10-11.
 */

public class UserPresenter extends BasePresenter<IUserView> {
    private IUserModel mUserModel;
    private IUserView mUserView;

    public UserPresenter(IUserView userView){
        mUserView = userView;
        mUserModel = new UserModelImpl();
    }

    public void saveUser(int id, String name, int age){
        mUserModel.setId(id);
        mUserModel.setName(name);
        mUserModel.setAge(age);
    }

    public void loadUser(){
        UserBean userBean = mUserModel.getUser(0);
        mUserView.setName(userBean.getName());
    }

}
