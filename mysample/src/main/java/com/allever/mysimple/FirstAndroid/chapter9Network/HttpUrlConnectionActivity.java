package com.allever.mysimple.FirstAndroid.chapter9Network;

import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * Created by allever on 17-4-6.
 */

public class HttpUrlConnectionActivity extends FirstAndroidBaseActivity implements View.OnClickListener{


    private static final String TAG = "HttpUrlConnectionActivi";

    private String session_id = "";
    private String user_id;

    private final String CRLF = "\r\n";

    private Button btn_http_get;
    private Button btn_http_post;
    private Button btn_register;
    private Button btn_like;
    private Button btn_download;
    private Button btn_get_user_info;

    //private TextView tv_result;
    private EditText et_username;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_url_connection_activity_layout);

        btn_http_get = (Button)findViewById(R.id.id_http_url_connection_activity_btn_http_get);
        btn_http_get.setOnClickListener(this);
        btn_http_post = (Button)findViewById(R.id.id_http_url_connection_activity_btn_http_post);
        btn_http_post.setOnClickListener(this);
        btn_register = (Button)findViewById(R.id.id_http_url_connection_activity_btn_multipart_register);
        btn_register.setOnClickListener(this);
        btn_like = (Button)findViewById(R.id.id_http_url_connection_activity_btn_like);
        btn_like.setOnClickListener(this);
        btn_download = (Button)findViewById(R.id.id_http_url_connection_activity_btn_download);
        btn_download.setOnClickListener(this);
        //tv_result = (TextView)findViewById(R.id.id_http_url_connection_activity_tv_result);
        et_username = (EditText)findViewById(R.id.id_http_url_connection_activity_et_username);
        btn_get_user_info = (Button)findViewById(R.id.id_http_url_connection_activity_btn_get_user_info);
        btn_get_user_info.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_http_url_connection_activity_btn_http_get) {
            sendHttpGet();
        } else if (id == R.id.id_http_url_connection_activity_btn_http_post) {
            sendHttpPost();
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = "xm";
                        String password = "dixm";
                        String sex = "男";
                        String age = "23";
                        String url = "http://10.42.0.1:8080/SocialServer/LoginServlet";
                        File file = new File(Environment.getExternalStorageDirectory() + "/xm.jpg");
                        String result = UploadUtils.regist(file,url,username,password,sex,age);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(HttpUrlConnectionActivity.this,result,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
                */
        } else if (id == R.id.id_http_url_connection_activity_btn_multipart_register) {
            if (!et_username.getText().equals("")) {
                username = et_username.getText().toString();
                //sendRegister();
                register();
            }
        } else if (id == R.id.id_http_url_connection_activity_btn_like) {
            likeNews();
        } else if (id == R.id.id_http_url_connection_activity_btn_download) {
            downloadFile2();
        } else if (id == R.id.id_http_url_connection_activity_btn_get_user_info) {
            getUserInfo();
        }
    }

    private void getUserInfo(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                InputStream inputStream = null;
                DataOutputStream dataOutputStream = null;
                try {
                    URL url = new URL("http://119.23.30.121:80/JFitness/userController/getUserInfo");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置每次传输的流大小
                    httpURLConnection.setChunkedStreamingMode(128 * 1024); //128K
                    //允许输入输出流
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.connect();

                    // POST请求
                    DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
                    //JSONObject obj = new JSONObject();
                    //String json = java.net.URLEncoder.encode(obj.toString(), "utf-8");
                    String json = "{\n" +
                            "\t\"xid\":\"9875642\"\n" +
                            "}";
                    out.writeBytes(json);
                    out.flush();
                    out.close();
                    // 读取响应
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String lines;
                    StringBuffer sb = new StringBuffer("");
                    while ((lines = reader.readLine()) != null) {
                        lines = URLDecoder.decode(lines, "utf-8");
                        sb.append(lines);
                    }
                    System.out.println(sb);
                    Log.d(TAG, "get user info = " + sb);
                    reader.close();
                    // 断开连接
                    httpURLConnection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
    }

    private void downloadFile2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection httpURLConnection = null;
                FileOutputStream fileOutputStream = null;
                InputStream inputStream = null;
                ByteArrayOutputStream byteArrayOutputStream = null;
                BufferedInputStream bufferedInputStream = null;
                try {
                    url = new URL("http://10.42.0.1:8080/SocialServer/apk/360.apk");
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setRequestMethod("GET");

                    bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int len;
                    fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/newFile.apk");
                    Log.d(TAG, "run: dataDirectory = " + Environment.getDataDirectory().getPath());

                    while ((len = bufferedInputStream.read(bytes)) > 0) {
                        fileOutputStream.write(bytes,0,len);//可以
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        if (fileOutputStream != null) fileOutputStream.close();
                        if (httpURLConnection!=null) httpURLConnection.disconnect();
                        if (inputStream != null) inputStream.close();
                        if (byteArrayOutputStream != null) byteArrayOutputStream.close();
                        if (bufferedInputStream != null) bufferedInputStream.close();
                    }catch (IOException ioe){
                        ioe.printStackTrace();;
                    }
                }
            }
        }).start();
    }

    private void downloadFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection httpURLConnection = null;
                FileOutputStream fileOutputStream = null;
                BufferedInputStream bufferedInputStream = null;
                ByteArrayOutputStream byteArrayOutputStream=null;
                try {
                    url = new URL("http://10.42.0.1:8080/SocialServer/apk/360.apk");
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setRequestMethod("GET");

                    byte[] b = new byte[1024];
                    bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    int len;
                    while ((len = bufferedInputStream.read(b))>0){
                        byteArrayOutputStream.write(b,0,len);
                    }

                    fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/download.apk"));
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());
                    byte[] bb = byteArrayOutputStream.toByteArray();
                    Log.d(TAG, "run: ");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        if (bufferedInputStream!=null) bufferedInputStream.close();
                        if (byteArrayOutputStream!=null) byteArrayOutputStream.close();
                        if (fileOutputStream != null) fileOutputStream.close();
                        if (httpURLConnection!=null) httpURLConnection.disconnect();
                    }catch (IOException ioe){
                        ioe.printStackTrace();;
                    }
                }
            }
        }).start();
    }

    private void likeNews(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                StringBuilder stringBuilder = null;
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                DataOutputStream dataOutputStream = null;
                try {
                    URL url = new URL("http://10.42.0.1:8080/SocialServer/LikeServlet");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置每次传输的流大小
                    //httpURLConnection.setChunkedStreamingMode(128 * 1024); //128K
                    //允许输入输出流
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                    //使用POST方法
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Charset", "UTF-8");
                    httpURLConnection.setRequestProperty("Cookie","id=1; " + "JSESSIONID=" + session_id);
                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());

                    stringBuilder = new StringBuilder();
                    dataOutputStream.writeBytes("news_id=1");
                    Log.d(TAG, stringBuilder.toString());
                    //ResponseCode可用于判断错误类型
                    //int status = httpURLConnection.getResponseCode();
                    InputStream is = httpURLConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    //String info = br.readLine(); //服务端打印内容
                    //handleRespon(info);

                    int statusCode = httpURLConnection.getResponseCode();
                    if (statusCode == 200) {
                        inputStream = httpURLConnection.getInputStream();

                    }


                    inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line);
                    }
                    //
                    //handleRespon(builder.toString());
                    handleLike(builder.toString());
                    dataOutputStream.close();
                    is.close();
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                            ;
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
    }

    private void likeNewsWithWrong(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection httpURLConnection = null;
                DataOutputStream dataOutputStream = null;   //写数据到服务器
                String boundary = "********************";
                String end = "\r\n";
                String twoHypens = "--";
                InputStreamReader inputStreamReader = null;
                StringBuilder stringBuilder = new StringBuilder();

                try {
                    /**
                     * A Part of Header
                     Accept-Encoding: gzip, deflate

                     @id user_id
                     Cookie: id=1; JSESSIONID=517FA5B66D8540CBE60926A700A12426  //

                     Connection: keep-alive
                     */
                    url = new URL("http://10.42.0.1:8080/SocialServer/LikeServlet");
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setConnectTimeout(8000);

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Connection","Keep-Alive");
                    httpURLConnection.setRequestProperty("Charset","UTF-8");
                    httpURLConnection.setRequestProperty("Cookie","id=1; " + "JSESSIONID=" + session_id);
                    httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());

                    //第一个表单数据
                    stringBuilder.append("--" + boundary + end);
                    dataOutputStream.writeBytes("--" + boundary + end);
                    //stringBuilder.append();
                    stringBuilder.append("Content-Disposition: form-data; name=\"news_id\"" + end + end + "1" + end);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"username\""+ end   + end +"1" + end);
                    dataOutputStream.writeBytes(twoHypens + boundary + twoHypens + end);


                    //stringBuilder.append(twoHypens + boundary + end);
                    //stringBuilder.append("Content-Disposition:form-data; name=\"username\"" + end + end + "xm" + end);
                    //stringBuilder.append(twoHypens + boundary + end);
                    //stringBuilder.append("Content-Disposition:form-data; name=\"news_id\"" + end + end + "1" + end);
                    //stringBuilder.append(twoHypens + boundary + twoHypens + end);
                    //Log.d(TAG, stringBuilder.toString());
                    //dataOutputStream.writeBytes(stringBuilder.toString());
                    //dataOutputStream.writeBytes(twoHypens + boundary + end);
                    //dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"news_id\"" + end + end + "1" + end);
                    //dataOutputStream.writeBytes(twoHypens + boundary + twoHypens + end);
                    //dataOutputStream.flush();

                    if (httpURLConnection.getResponseCode() == 200){
                        inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                        BufferedReader bufferReader = new BufferedReader(inputStreamReader);
                        String line = "";
                        StringBuilder builder = new StringBuilder();
                        while ((line = bufferReader.readLine()) != null){
                            builder.append(line);
                        }

                        handleLike(builder.toString());
                    }



                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        if (inputStreamReader!=null) inputStreamReader.close();
                        if (dataOutputStream!=null) dataOutputStream.close();
                        if (httpURLConnection!=null) httpURLConnection.disconnect();
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }

                }

            }
        }).start();
    }

    private void handleLike(String result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HttpUrlConnectionActivity.this,result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                String end = "\r\n";
                String twoHyphens = "--";
                String boundary = "******";
                StringBuilder stringBuilder = null;
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                DataOutputStream dataOutputStream = null;
                try {
                    URL url = new URL("http://10.42.0.1:8080/SocialServer/RegistServlet");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置每次传输的流大小
                    httpURLConnection.setChunkedStreamingMode(128 * 1024); //128K
                    //允许输入输出流
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                    //使用POST方法
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Charset", "UTF-8");
                    httpURLConnection.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());

                    stringBuilder = new StringBuilder();
                    //第一个表单数据
                    stringBuilder.append("--" + boundary + end);
                    dataOutputStream.writeBytes("--" + boundary + end);
                    //stringBuilder.append();
                    stringBuilder.append("Content-Disposition: form-data; name=\"username\"" + end + end + username + end);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"username\""+ end   + end +username + end);

                    String password = "dixm";
                    //第个表单数据
                    stringBuilder.append("--" + boundary + end);
                    dataOutputStream.writeBytes("--" + boundary + end);
                    stringBuilder.append("Content-Disposition: form-data; name=\"password\"" + end  + end + password + end);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"password\"" + end  + end + password + end);

                    //第个表单数据
                    stringBuilder.append("--" + boundary + end);
                    dataOutputStream.writeBytes("--" + boundary + end);
                    stringBuilder.append("Content-Disposition: form-data; name=\"name\"" + end + end + "XM" + end);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"name\"" + end + end + "XM" + end);
                    //第个表单数据
                    stringBuilder.append("--" + boundary + end);
                    dataOutputStream.writeBytes("--" + boundary + end + "--");
                    dataOutputStream.writeBytes("--" + boundary + end);
                    stringBuilder.append("Content-Disposition:form-data; name=\"birthday\"" + end + end + "1993-04-26" + end);
                    dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"birthday\"" + end + end + "1993-04-26" + end);
                    //第个表单数据
                    stringBuilder.append("--" + boundary + end);
                    dataOutputStream.writeBytes("--" + boundary + end);
                    stringBuilder.append("Content-Disposition:form-data; name=\"phone\"" + end + end + "1993" + end);
                    dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"phone\"" + end + end + "1993" + end);

                    //第个表单数据
                    stringBuilder.append("--" + boundary + end);
                    dataOutputStream.writeBytes("--" + boundary + end);
                    stringBuilder.append("Content-Disposition:form-data; name=\"sex\"" + end + end + "男" + end);
                    dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"sex\"" + end + end + "男" + end);

                    //第个表单数据
                    stringBuilder.append("--" + boundary + end);
                    dataOutputStream.writeBytes("--" + boundary + end);
                    stringBuilder.append("Content-Disposition:form-data; name=\"age\"" + end + end + "23" + end);
                    dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"age\"" + end + end + "23" + end);

                    stringBuilder.append(twoHyphens + boundary + end);
                    dataOutputStream.writeBytes(twoHyphens + boundary + end);
                    //注意这里的file是上传文件的name，类似标签，必须与服务端一致！
                    //stringBuilder.append();
                    stringBuilder.append("Content-Disposition: form-data; name=\"photo\"; filename=\"xm.jpg\"" + end);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"photo\"; filename=\"xm.jpg\"" + end);
                    stringBuilder.append(end);
                    dataOutputStream.writeBytes(end);
                    FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/xm.jpg");
                    byte[] buffer = new byte[8192]; // 8k
                    int count = 0;
                    //读取文件
                    while ((count = fis.read(buffer)) != -1) {
                        dataOutputStream.write(buffer, 0, count);
                    }
                    fis.close();
                    stringBuilder.append("字符" + end);
                    stringBuilder.append(end);
                    dataOutputStream.writeBytes(end);
                    stringBuilder.append(twoHyphens + boundary + twoHyphens + end);
                    dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + end);
                    dataOutputStream.flush();
                    Log.d(TAG, stringBuilder.toString());
                    //ResponseCode可用于判断错误类型
                    //int status = httpURLConnection.getResponseCode();
                    InputStream is = httpURLConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    //String info = br.readLine(); //服务端打印内容
                    //handleRespon(info);

                    int statusCode = httpURLConnection.getResponseCode();
                    if (statusCode == 200) {
                        inputStream = httpURLConnection.getInputStream();

                    }


                    inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line);
                    }
                    handleRespon(builder.toString());

                    dataOutputStream.close();
                    is.close();
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                            ;
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
    }

    private void handleLogin(String result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Root root = gson.fromJson(result,Root.class);
                session_id = root.session_id;
                Toast.makeText(HttpUrlConnectionActivity.this,"session_id = " + session_id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleRespon(String result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //tv_result.setText(result);
                Toast.makeText(HttpUrlConnectionActivity.this,result,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendRegister(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                HttpURLConnection httpURLConnection = null;
                FileInputStream fileInputStream = null;
                DataOutputStream dataOutputStream = null;
                File imageFile = null;
                InputStreamReader inputStreamReader = null;
                StringBuilder stringBuilder;

                try {
                    URL url = new URL("http://10.42.0.1:8080/SocialServer/RegistServlet");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    String boundary = UUID.randomUUID().toString();
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setRequestMethod("POST");

                    //构建entity form
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                    httpURLConnection.setRequestProperty("Charset", "UTF-8");

                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    stringBuilder = new StringBuilder();
                    //第一个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    //stringBuilder.append();
                    stringBuilder.append("Content-Disposition: form-data; name=\"username\""+ CRLF + CRLF + username + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"username\"" + CRLF + CRLF + username + CRLF);

                    //第个表单数据
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"password\""+ CRLF + CRLF + "dixm" + CRLF);


                    //第个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition: form-data; name=\"ensurepassword\""+ CRLF + CRLF + "dixm" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"ensurepassword\""+ CRLF + CRLF + "dixm" + CRLF);

                    //第个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition: form-data; name=\"name\""+ CRLF + CRLF + "XM" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"name\""+ CRLF + CRLF + "XM" + CRLF);
                    //第个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition:form-data; name=\"birthday\""+ CRLF + CRLF + "1993-04-26" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"birthday\""+ CRLF + CRLF + "1993-04-26" + CRLF);
                    //第个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition:form-data; name=\"phone\""+ CRLF + CRLF + "1993" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"phone\""+ CRLF + CRLF + "1993" + CRLF);

                    //第个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition:form-data; name=\"sex\""+ CRLF + CRLF + "男" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"sex\""+ CRLF + CRLF + "男" + CRLF);

                    //第个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition:form-data; name=\"age\""+ CRLF + CRLF + "23" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition:form-data; name=\"age\""+ CRLF + CRLF + "23" + CRLF);

                    //文件数据

                    //stringBuilder.append();
                    imageFile = new File(Environment.getExternalStorageDirectory(),"xm.jpg");
                    fileInputStream = new FileInputStream(imageFile);

                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition:form-data;name=\"photo\";filename=\"xm.jpg\"" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition:form-data;name=\"photo\";filename=\"xm.jpg\"" + CRLF);
                    stringBuilder.append("Content-Type:image/jpg" + CRLF);
                    dataOutputStream.writeBytes("Content-Type:image/jpg" + CRLF);
                    stringBuilder.append(CRLF);
                    dataOutputStream.writeBytes(CRLF);
                    int bytesRead;
                    byte[] buffer  = new byte[1024];
                    while ((bytesRead = fileInputStream.read(buffer)) != -1){
                        dataOutputStream.write(buffer, 0, bytesRead);
                    }
                    dataOutputStream.flush();
                    stringBuilder.append("字符");
                    stringBuilder.append(CRLF);
                    dataOutputStream.writeBytes(CRLF);
                    dataOutputStream.flush();
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + "--" + CRLF);/////注意最后的分隔符后面有"--"
                    dataOutputStream.flush();
                    fileInputStream.close();
                    Log.d(TAG, "run: \n" + stringBuilder.toString());



                    //-----------------------------------------------------------------------------

                    int statusCode = httpURLConnection.getResponseCode();
                    if (statusCode == 200){
                        inputStream = httpURLConnection.getInputStream();

                    }


                    inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ( (line = bufferedReader.readLine()) != null){
                        builder.append(line);
                    }
                    handleRespon(builder.toString());




                    //addFormData("username", username);


                }catch (MalformedURLException me){
                    me.printStackTrace();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();;
                        }catch (IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                    if (dataOutputStream != null){
                        try {
                            dataOutputStream.close();
                        }catch (IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                    if (fileInputStream != null){
                        try {
                            fileInputStream.close();
                        }catch (IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null)
                        httpURLConnection.disconnect();
                }

            }
        }).start();
    }

    //private StringBuilder addFormData(String fieldName, String value){
     //   dataOutputStream.writeBytes(boundary + CRLF);
    //}

    private void sendHttpPost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStreamReader inputStreamReader = null;
                HttpURLConnection httpURLConnection = null;
                DataOutputStream dataOutputStream = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    URL url = new URL("http://10.42.0.1:8080/SocialServer/LoginServlet");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);

                    /*
                    //new add------------------------------------------------------------------------------

                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);

                    //构建entity form
                    String boundary = UUID.randomUUID().toString();

                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    httpURLConnection.setRequestProperty("Cache-Control", "no-cache");

                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    stringBuilder = new StringBuilder();
                    //第一个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    //stringBuilder.append();
                    stringBuilder.append("Content-Disposition: form-data; name=\"username\""+ CRLF + CRLF + "xm" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"username\"" + CRLF + "xm" + CRLF);

                    //第个表单数据
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"password\""+ CRLF + CRLF + "dixm");


                    //第个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition: form-data; name=\"longitude\""+ CRLF + CRLF + "22.3333" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"longitude\""+ CRLF + CRLF + "22.3333" + CRLF);

                    //第个表单数据
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);
                    stringBuilder.append("Content-Disposition: form-data; name=\"latitude\""+ CRLF + CRLF + "113.001" + CRLF);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"latitude\""+ CRLF + CRLF + "113.001" + CRLF);
                    stringBuilder.append("--"+boundary + CRLF);
                    dataOutputStream.writeBytes("--"+boundary + CRLF);

                    Log.d(TAG, "run: \n" + stringBuilder.toString());


/*
                    Map<String, List<String>> map = httpURLConnection.getHeaderFields();
                    for(Map.Entry<String, List<String>> entity:map.entrySet()){
                        Log.d(TAG, "key = " + entity.getKey());
                        for (String value: entity.getValue()){
                            Log.d(TAG, "value = " + value);
                        }
                    }
                    */

                    //----------------------------------------------------------------------------

                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream.writeBytes("username=xm&password=dixm&longitude=22.3333&latitude=113.001");

                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ( (line = bufferedReader.readLine()) != null){
                        builder.append(line);
                    }
                    //handleRespon(builder.toString());
                    handleLogin(builder.toString());
                }catch (MalformedURLException m){
                    m.printStackTrace();
                }catch (IOException ie){
                    ie.printStackTrace();
                }finally {
                    if (inputStreamReader != null){
                        try {
                            inputStreamReader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }



    private void sendHttpGet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStreamReader inputStreamReader = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);


                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ( (line = bufferedReader.readLine()) != null){
                        builder.append(line);
                    }
                    handleRespon(builder.toString());
                }catch (MalformedURLException m){
                    m.printStackTrace();
                }catch (IOException ie){
                    ie.printStackTrace();
                }finally {
                    if (inputStreamReader != null){
                        try {
                            inputStreamReader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }




    class Root{
        boolean seccess;
        String message;
        String session_id;
        User user;
    }

    class User{
        String id;
        String username;
        String nickname;
        String imagepath;
        double longitude;
        double latiaude;
        String phone;
        String email;
        String user_head_path;
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
        int is_recommended;
        String autoreaction;
        String onlinestate;
    }
}
