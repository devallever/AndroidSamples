package com.allever.mysimple.FirstAndroid.chapter9Network;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by allever on 17-4-14.
 */

public class JsonParseActivity extends FirstAndroidBaseActivity implements View.OnClickListener {

    private static final String TAG = "JsonParseActivity";

    private String jsonData = "";

    private Button btn_json_object;
    private Button btn_gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_parse_activity_layout);

        btn_json_object = (Button)findViewById(R.id.id_json_parse_activity_btn_json_object);
        btn_json_object.setOnClickListener(this);

        btn_gson = (Button)findViewById(R.id.id_json_parse_activity_btn_gson);
        btn_gson.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_json_parse_activity_btn_json_object) {
            getUserData();
        } else if (id == R.id.id_json_parse_activity_btn_gson) {
            if (!jsonData.equals("")) parseJsonWithGson(jsonData);
            else Toast.makeText(this, "先获取json数据", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseJsonWithGson(String jsonData){
        Gson gson = new Gson();
        Result result = gson.fromJson(jsonData, Result.class);
        Toast.makeText(this, result.user.signature,Toast.LENGTH_SHORT).show();
    }


    private String getUserData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                BufferedReader bufferedReader = null;
                DataOutputStream dataOutputStream = null;
                try {
                    url = new URL("http://10.42.0.1:8080/SocialServer/UserDataServlet");
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setRequestMethod("POST");

                    //outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    //outputStreamWriter.write("check_user_id=xm");//不可以
                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream.writeBytes("check_user_id=xm");

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line + "\n");
                    }

                    jsonData = stringBuilder.toString();
                    Log.d(TAG, jsonData);
                    JSONObject jsonObject = new JSONObject(jsonData);
                    jsonObject = jsonObject.getJSONObject("user");
                    String signature = jsonObject.getString("signature");
                    JSONArray jsonArray = jsonObject.getJSONArray("list_news_img");
                    for (int i=0; i<jsonArray.length(); i++){
                        Log.d(TAG, jsonArray.get(0).toString());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(JsonParseActivity.this, signature,Toast.LENGTH_SHORT).show();

                        }
                    });

                }catch (IOException ioe){
                    ioe.printStackTrace();
                }catch (JSONException je){
                    je.printStackTrace();
                }finally {
                    try {
                        if (dataOutputStream != null) dataOutputStream.close();
                        if (bufferedReader != null ) bufferedReader.close();
                        if (httpURLConnection != null) httpURLConnection.disconnect();
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            }
        }).start();
        return "";
    }


    class Result{
        boolean seccess;
        String message;
        String session_id;
        User user;
    }

    class User{
        String id;
        String username;
        String nickname;
        double longitude;
        double latiaude;
        double distance;
        int is_friend;
        String phone;
        String user_head_path;
        String email;
        String signature;
        String city;
        String sex;
        int age;
        String occupation;
        String constellation;
        String hight;
        String weight;
        String figure;
        String emotion;
        int is_vip;
        int accept_video;
        int video_fee;
        int is_follow;
        String second_name;
        String friendgroup_name;
        List<String> list_news_img;
    }
}
