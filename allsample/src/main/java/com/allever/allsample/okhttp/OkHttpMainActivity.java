package com.allever.allsample.okhttp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;
import com.allever.allsample.okhttp.bean.LoginRoot;
import com.allever.allsample.okhttp.bean.User;
import com.allever.allsample.okhttp.util.OkHttpUtil;
import com.allever.allsample.okhttp.util.SharePreferenceUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by allever on 17-6-29.
 */

public class OkHttpMainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "OkHttpMainActivity";

    private EditText et_username;
    private EditText et_password;
    private EditText et_content;

    private String username;
    private String password;


    private Button btn_login_get;
    private Button btn_login_post;

    private Button btn_logout_tong_bu;
    private Button btn_logout_yi_bu;

    private Button btn_choose_pic;
    private Button btn_add_news;

    private ImageView iv_head;

    private TextView tv_result;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ok_http_activity_layout);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case OkHttpUtil.MESSAGE_LOGOUT:
                        handleLogout((String)msg.obj);
                        break;
                }
            }
        };

        initView();
    }

    private void initView(){
        et_username = (EditText)findViewById(R.id.id_ok_http_activity_et_username);
        et_password = (EditText)findViewById(R.id.id_ok_http_activity_et_password);
        et_content = (EditText)findViewById(R.id.id_ok_http_activity_et_news_content);

        btn_login_get = (Button)findViewById(R.id.id_ok_http_activity_btn_login_get);
        btn_login_get.setOnClickListener(this);
        btn_login_post = (Button)findViewById(R.id.id_ok_http_activity_btn_login_post);
        btn_login_post.setOnClickListener(this);

        btn_logout_tong_bu = (Button)findViewById(R.id.id_ok_http_activity_btn_logout_tong_bu);
        btn_logout_tong_bu.setOnClickListener(this);
        btn_logout_yi_bu = (Button)findViewById(R.id.id_ok_http_activity_btn_logout_yi_bu);
        btn_logout_yi_bu.setOnClickListener(this);

        btn_choose_pic = (Button)findViewById(R.id.id_ok_http_activity_btn_choose_pic);
        btn_choose_pic.setOnClickListener(this);
        btn_add_news = (Button)findViewById(R.id.id_ok_http_activity_btn_add_news);
        btn_add_news.setOnClickListener(this);

        iv_head = (ImageView)findViewById(R.id.id_ok_http_activity_iv_head);


        tv_result = (TextView)findViewById(R.id.id_ok_http_activity_tv_result);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_ok_http_activity_btn_login_get) {
            getLoginParams();
            doLoginGet();
        } else if (id == R.id.id_ok_http_activity_btn_login_post) {
            getLoginParams();
            doLoginPost();
        } else if (id == R.id.id_ok_http_activity_btn_logout_tong_bu) {
            doLogoutTongBu();
        } else if (id == R.id.id_ok_http_activity_btn_logout_yi_bu) {
            Toast.makeText(this, "同Login get post", Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_ok_http_activity_btn_choose_pic) {//File file = new File(Environment.getExternalStorageDirectory().getPath(),"jiaxin.png");
            Bitmap bitmep = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/" + "jiaxin.png");
            Log.d(TAG, "onClick: file_path = " + Environment.getExternalStorageDirectory().getPath() + "/" + "jiaxin.png");
            iv_head.setImageBitmap(bitmep);
        } else if (id == R.id.id_ok_http_activity_btn_add_news) {
            String contnet = et_content.getText().toString();
            if (contnet.equals("")) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_LONG).show();
            } else {
                doAddNews(contnet);
            }
        }
    }

    private void doAddNews(String content){
        OkHttpUtil.addNews(content, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: result = " + response.body().string());
            }
        });
    }

    private void doLogoutTongBu(){
        OkHttpUtil.logoutTongBu(handler);
    }

    private void handleLogout(String result){
        Log.d(TAG, "handleLogout: result = " + result);
        tv_result.setText(result);
        SharePreferenceUtil.saveSessionId(SharePreferenceUtil.getUsername(),"");
    }

    private void getLoginParams(){
        username = et_username.getText().toString();
        password = et_password.getText().toString();
    }

    private void doLoginGet(){
        final String url = OkHttpUtil.BASE_URL + "LoginServlet?username=" + username + "&password=" + password + "&longitude=22.111&latitude=111.2222";
        OkHttpUtil.loginGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                Log.d(TAG, "onResponse: result = " + result);
                handleLogin(result);
            }
        });
    }

    private void doLoginPost(){
        OkHttpUtil.loginPost(username, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleLogin(response.body().string());
            }
        });
    }

    private void handleLogin(final String result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                LoginRoot loginRoot = gson.fromJson(result,LoginRoot.class);
                User user = loginRoot.getUser();
                tv_result.setText(user.getNickname() + "\n" + user.getSignature());
                Toast.makeText(OkHttpMainActivity.this, loginRoot.getSession_id(),Toast.LENGTH_LONG).show();
                SharePreferenceUtil.saveSessionId(user.getUsername(),loginRoot.getSession_id());
            }
        });
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,OkHttpMainActivity.class);
        context.startActivity(intent);
    }


}

