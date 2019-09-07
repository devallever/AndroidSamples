package com.allever.mysimple.FirstAndroid.chapter9Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by allever on 17-4-15.
 */

public class HttpUtil {
    public static void getUserData(String username,HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                DataOutputStream dataOutputStream = null;
                BufferedReader bufferedReader = null;
                try {
                    url = new URL("http://10.42.0.1:8080/SocialServer/UserDataServlet");
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);

                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream.writeBytes("check_user_id=" + username);

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line + "\n");
                    }
                    if (listener != null) listener.onFinish(stringBuilder.toString());
                }catch (Exception me){
                    me.printStackTrace();
                    if (listener != null) listener.onError(me);
                }finally {
                    try {
                        if (dataOutputStream != null) dataOutputStream.close();
                        if (bufferedReader != null) bufferedReader.close();
                        if (httpURLConnection != null) httpURLConnection.disconnect();
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }

            }
        }).start();
    }

    public interface HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }
}
