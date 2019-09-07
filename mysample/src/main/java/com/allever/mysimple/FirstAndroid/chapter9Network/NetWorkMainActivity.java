package com.allever.mysimple.FirstAndroid.chapter9Network;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-4-6.
 */

public class NetWorkMainActivity extends FirstAndroidBaseActivity implements View.OnClickListener{
    private Button btn_web_view;
    private Button btn_http_url_connection;
    private Button btn_xml;
    private Button btn_json;
    private Button btn_best;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_activity_layout);
        btn_web_view = (Button)findViewById(R.id.id_network_activity_btn_web_view);
        btn_web_view.setOnClickListener(this);

        btn_http_url_connection = (Button)findViewById(R.id.id_network_activity_btn_http_url_connection);
        btn_http_url_connection.setOnClickListener(this);

        btn_xml = (Button)findViewById(R.id.id_network_activity_btn_xml);
        btn_xml.setOnClickListener(this);

        btn_json = (Button)findViewById(R.id.id_network_activity_btn_json);
        btn_json.setOnClickListener(this);

        btn_best = (Button)findViewById(R.id.id_network_activity_btn_best_practice);
        btn_best.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        if (id == R.id.id_network_activity_btn_web_view) {
            intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.id_network_activity_btn_http_url_connection) {
            intent = new Intent(this, HttpUrlConnectionActivity.class);
            startActivity(intent);
        } else if (id == R.id.id_network_activity_btn_xml) {
            intent = new Intent(this, XMLParseActivity.class);
            startActivity(intent);
        } else if (id == R.id.id_network_activity_btn_json) {
            intent = new Intent(this, JsonParseActivity.class);
            startActivity(intent);
        } else if (id == R.id.id_network_activity_btn_best_practice) {
            HttpUtil.getUserData("xm", new HttpUtil.HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NetWorkMainActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }


}
