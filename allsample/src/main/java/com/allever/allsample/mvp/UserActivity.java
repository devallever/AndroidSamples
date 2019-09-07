package com.allever.allsample.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.allever.allsample.R;
import com.allever.allsample.mvp.bean.UserBean;
import com.allever.allsample.mvp.presenter.UserPresenter;
import com.allever.allsample.mvp.view.IUserView;

/**
 * Created by allever on 17-10-11.
 */

public class UserActivity extends MVPBaseActivity<IUserView,UserPresenter> implements IUserView {
    private EditText et_id;
    private EditText et_name;
    private EditText et_age;

    private Button btn_save;
    private Button btn_load;

    private UserPresenter mUserPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        mUserPresenter = new UserPresenter(this);

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserPresenter.loadUser();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserPresenter.saveUser(new Integer(et_id.getText().toString()),
                        et_name.getText().toString(),
                        new Integer(et_age.getText().toString()));
            }
        });


    }

    @Override
    public UserBean getUser(int id) {
        UserBean userBean = new UserBean();
        userBean.setId(new Integer(et_id.getText().toString()));
        userBean.setName(et_name.getText().toString());
        userBean.setAge(new Integer(et_age.getText().toString()));
        return userBean;
    }

    @Override
    public void setName(String name) {
        et_name.setText(name);
    }

    @Override
    public void setAge(int age) {
        et_age.setText(age+"");
    }

    @Override
    protected UserPresenter createPresenter() {
        return null;
    }
}
