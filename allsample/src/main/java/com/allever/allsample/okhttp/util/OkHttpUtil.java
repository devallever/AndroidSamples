package com.allever.allsample.okhttp.util;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.util.MarkEnforcingInputStream;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.security.MessageDigest;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;

/**
 * Created by allever on 17-6-29.
 */

public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil";

    public static final String BASE_URL = "http://39.108.9.138:8080/SocialServer/";
    public static final int MESSAGE_LOGOUT = 1000;
    private OkHttpUtil(){}

/*    public static OkHttpClient getInstance(){
        return OkHttpClientHolder.INSTANCE;
    }

    private static class OkHttpClientHolder{
        private static final OkHttpClient INSTANCE = new OkHttpClient();
    }

    *//**防止反序列化重新构建对象*//*
    private Object readResolve() throws ObjectStreamException {
        return OkHttpClientHolder.INSTANCE;
    }*/

    public static void loginGet(String url, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void loginPost(String username, String password, Callback callback){
        String url = BASE_URL + "LoginServlet";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("username",username);
        builder.add("password",password);
        builder.add("longitude","22.1111");
        builder.add("latitude","111.222");
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);

    }

    public static void logoutTongBu(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BASE_URL + "LogoutServlet";
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder builder = new FormBody.Builder();
                Log.d(TAG, "session_id = " + SharePreferenceUtil.getSessionId());
                if (!SharePreferenceUtil.getSessionId().equals("")){
                    //builder.add("JSESSION_ID",SharePreferenceUtil.getSessionId());
                    RequestBody requestBody = builder.build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .addHeader("Cookie", "JSESSIONID=" + SharePreferenceUtil.getSessionId())
                            .build();
                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                        String result = response.body().string();
                        Message message = new Message();
                        message.what = MESSAGE_LOGOUT;
                        message.obj = result;
                        handler.sendMessage(message);
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }

                }else {
                    Message message = new Message();
                    message.what = MESSAGE_LOGOUT;
                    message.obj = "未登录";
                    handler.sendMessage(message);
                }

            }
        }).start();
    }

    public static void addNews(String content,Callback callback){
        OkHttpClient client = new OkHttpClient();
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content",content)
                .addFormDataPart("city","番禺")
                .addFormDataPart("longitude","111.22")
                .addFormDataPart("latitude","22.22")
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"part1"+ "\""),
                        RequestBody.create(
                                MediaType.parse("application/octet-stream"),
                                new File(Environment.getExternalStorageDirectory().getPath()+"/","jiaxin.png")))
                .build();

        if (SharePreferenceUtil.getSessionId().equals("")){
        }
        Request request = new Request.Builder()
                .url(BASE_URL + "AddNewsServlet")
                .post(multipartBody)
                .addHeader("Cookie", "JSESSIONID=" + SharePreferenceUtil.getSessionId())
                .build();

        client.newCall(request).enqueue(callback);
    }
}
