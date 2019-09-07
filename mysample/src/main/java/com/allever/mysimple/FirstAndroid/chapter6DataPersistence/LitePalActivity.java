package com.allever.mysimple.FirstAndroid.chapter6DataPersistence;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;

/**
 * Created by allever on 17-3-25.
 */

public class LitePalActivity extends FirstAndroidBaseActivity implements View.OnClickListener{

    private static final String TAG = "LitePalActivity";

    private EditText et;

    private Button btn_create;
    private Button btn_add;
    private Button btn_delete;
    private Button btn_update;
    private Button btn_query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pal_actvity_layout);

        et = (EditText)findViewById(R.id.id_lite_pal_activity_et);
        btn_create = (Button)findViewById(R.id.id_lite_pal_activity_btn_create_db);
        btn_create.setOnClickListener(this);
        btn_add = (Button)findViewById(R.id.id_lite_pal_activity_btn_add);
        btn_add.setOnClickListener(this);
        btn_delete = (Button)findViewById(R.id.id_lite_pal_activity_btn_delete);
        btn_delete.setOnClickListener(this);
        btn_update = (Button)findViewById(R.id.id_lite_pal_activity_btn_update);
        btn_update.setOnClickListener(this);
        btn_query = (Button)findViewById(R.id.id_lite_pal_activity_btn_query);
        btn_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_lite_pal_activity_btn_create_db) {
            Connector.getDatabase();
        } else if (id == R.id.id_lite_pal_activity_btn_add) {
            User xm = new User();
            xm.setUsername("xm");
            xm.setNickname("XM");
            xm.setBirthday(new Date());
            xm.setHeight(155.55);
            xm.save();

            User allever = new User();
            allever.setUsername("allever");
            allever.setNickname("Allever");
            allever.setHeight(160.02);
            allever.setBirthday(new Date());
            allever.save();
        } else if (id == R.id.id_lite_pal_activity_btn_query) {
            List<User> listUsers = DataSupport.findAll(User.class);
            for (User user : listUsers) {
                Log.d(TAG, "Username = " + user.getUsername());
                Log.d(TAG, "Nickname = " + user.getNickname());
                Log.d(TAG, "Height = " + user.getHeight());
                Log.d(TAG, "Birthday = " + user.getBirthday());
                Log.d(TAG, "\n");
            }

            listUsers.clear();
            listUsers = DataSupport.where("username=?", "allever")
                    .select("username", "nickname")
                    .order("nickname")
                    .find(User.class);
            for (User u : listUsers) {
                Log.d(TAG, "username = " + u.getUsername());
            }
        } else if (id == R.id.id_lite_pal_activity_btn_delete) {
            DataSupport.deleteAll(User.class, "username=?", "xm");
        } else if (id == R.id.id_lite_pal_activity_btn_update) {
            User aUser = new User();
            aUser.setUsername("winchen");
            aUser.setNickname("Winchen");
            aUser.updateAll("username=?", "xm");
        }
    }
}
