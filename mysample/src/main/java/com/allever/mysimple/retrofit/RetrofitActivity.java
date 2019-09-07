package com.allever.mysimple.retrofit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.retrofit.been.AddNewsBeen;
import com.allever.mysimple.retrofit.been.LoginUser;
import com.allever.mysimple.retrofit.been.ModifyHeadBeen;
import com.allever.mysimple.retrofit.been.UserBeen;
import com.allever.mysimple.retrofit.service.IUserService;
import com.allever.mysimple.retrofit.util.SharedPreferenceUtil;
import com.allever.mysimple.util.CommentUtil;
import com.allever.mysimple.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Allever on 2016/12/6.
 */

public class RetrofitActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_get;
    private Button btn_post;
    private Button btn_tongbu;
    private Button btn_async;
    private Button btn_load_pic;
    private Button btn_choose_pic;
    private Button btn_download_pic;
    private Button btn_login;
    private Button btn_chooose_pic;
    private Button btn_upload_pic;
    private Button btn_upload_more_pic;
    private Button btn_download_apk;


    private Handler handler;

    private TextView tv_data;
    private TextView tv_path;

    private ImageView iv_pic;
    private byte[] head_b;
    private Bitmap bitmap;

    private Retrofit retrofit;
    private IUserService iUserService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_activity_layout);

        handler = new Handler();

        setToolbar((Toolbar) findViewById(R.id.id_toolbar), "Retrofit");

        //setToolbar();
        initView();
        initData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CommentUtil.REQUEST_CODE_CHOOSE_PIC:
                if (resultCode == RESULT_OK){
                    CommentUtil.startPhotoZoom(this, data.getData());
                }
                break;
            case CommentUtil.REQUEST_CODE_CUTTING:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            bitmap = extras.getParcelable("data");
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            iv_pic.setImageDrawable(drawable);
                            head_b = CommentUtil.Bitmap2Bytes(bitmap);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_retrofit_activity_btn_get) {
            getListDataWithGet();
        } else if (id == R.id.id_retrofit_activity_btn_post) {
            getListDataWithPost();
        } else if (id == R.id.id_retrofit_activity_btn_tongbu) {
            getListDataTongbu();
        } else if (id == R.id.id_retrofit_activity_btn_async) {
            getListDataWithGet();
        } else if (id == R.id.id_retrofit_activity_btn_download_pic) {
            downloadPic();
        } else if (id == R.id.id_retrofit_activity_btn_login) {
            login();
        } else if (id == R.id.id_retrofit_activity_btn_choose_pic) {
            CommentUtil.startPicChoiceIntent(this);
        } else if (id == R.id.id_retrofit_activity_btn_upload_pic) {
            uploadPic();
        } else if (id == R.id.id_retrofit_activity_btn_upload_more_pic) {//addNews();//多个Part方式
            addNewsPartMap();//PartMap方式
        } else if (id == R.id.id_retrofit_activity_btn_download_apk) {
            downloadAPK();
        }
    }

    private void initData(){

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .addHeader("Cookie","JSESSIONID=" + SharedPreferenceUtil.getSessionId());
                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });
        OkHttpClient okHttpClient = clientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://27.54.249.252:8080/SocialServer/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        iUserService = retrofit.create(IUserService.class);
    }

    private void initView(){
        tv_data = (TextView)findViewById(R.id.id_retrofit_activity_tv_data);
        tv_path = (TextView)findViewById(R.id.id_retrofit_activity_tv_img_path);


        iv_pic = (ImageView)findViewById(R.id.id_retrofit_activity_iv_img);

        btn_get = (Button)findViewById(R.id.id_retrofit_activity_btn_get);
        btn_get.setOnClickListener(this);


        btn_post = (Button)findViewById(R.id.id_retrofit_activity_btn_post);
        btn_post.setOnClickListener(this);

        btn_tongbu = (Button)findViewById(R.id.id_retrofit_activity_btn_tongbu);
        btn_tongbu.setOnClickListener(this);

        btn_async = (Button)findViewById(R.id.id_retrofit_activity_btn_async);
        btn_async.setOnClickListener(this);

        btn_load_pic = (Button)findViewById(R.id.id_retrofit_activity_btn_upload_pic);
        btn_load_pic.setOnClickListener(this);

        btn_download_pic = (Button)findViewById(R.id.id_retrofit_activity_btn_download_pic);
        btn_download_pic.setOnClickListener(this);

        btn_login = (Button)findViewById(R.id.id_retrofit_activity_btn_login);
        btn_login.setOnClickListener(this);

        btn_choose_pic = (Button)findViewById(R.id.id_retrofit_activity_btn_choose_pic);
        btn_choose_pic.setOnClickListener(this);

        btn_upload_more_pic = (Button)findViewById(R.id.id_retrofit_activity_btn_upload_more_pic);
        btn_upload_more_pic.setOnClickListener(this);

        btn_download_apk = (Button)findViewById(R.id.id_retrofit_activity_btn_download_apk);
        btn_download_apk.setOnClickListener(this);

//        btn_async = (Button)findViewById(R.id.id_retrofit_activity_btn_async);
//        btn_async.setOnClickListener(this);
    }

    private void downloadAPK(){
        new AsyncTask<Void, Long, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Call<ResponseBody> call = iUserService.downloadAPK("http://27.54.249.252:8080/SocialServer/apk/social_0.18.01.apk");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    handleDownloadAPK(response.body());
                                }
                            }).start();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                return null;
            }

        }.execute();
    }

    private void handleDownloadAPK(ResponseBody body){
        String dir = Environment.getExternalStorageDirectory() + "/00000/";
        String fileName = "download.apk";
        try {
            FileUtil.saveFile(CommentUtil.inputStramToByte(body.byteStream()),dir,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }


//        InputStream inputStream = body.byteStream();
//
//        FileOutputStream fos;
//        String filePath= "";
//        try{
//            byte[] b = CommentUtil.inputStramToByte(inputStream);
//            filePath = Environment.getExternalStorageDirectory()+ "/social.apk";
//            System.out.println("path = " + filePath);
//            fos = new FileOutputStream(filePath);
//            fos.write(b);
//            fos.close();
//        }catch (Exception e){
//
//        }
//
//        //通知栏显示
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("下载完成");
        builder.setContentTitle("Social.apk");
        builder.setContentText("下载完成");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setContentInfo("This is content info");
        builder.setAutoCancel(true);

        Intent intent =  FileUtil.getFileIntent(new File(dir + fileName));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(3, builder.build());
        //打开文件
        startActivity(intent);
    }

   private void downloadPic(){
        Call<ResponseBody> call = iUserService.downloadPic("http://27.54.249.252:8080/SocialServer/images/head/xm.jpg");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //不知为什么直接用response对象会不行
                handleDownloadPic(response.body());//具体操作
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void handleDownloadPic(ResponseBody responseBody){
        try {
            InputStream inputStream = responseBody.byteStream();
            byte[] b  = CommentUtil.inputStramToByte(inputStream);
            iv_pic.setImageBitmap(CommentUtil.byteToBitmap(b,null));
            CommentUtil.saveFile(CommentUtil.byteToBitmap(b,null), "xm.jpg");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void uploadPic(){

        //part
        if (head_b==null || head_b.length==0) return;

        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),head_b);
        MultipartBody.Part part = MultipartBody.Part.createFormData("head_img","head_img",body);//partName  filename

        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"),"修改头像");

        Call<ModifyHeadBeen> call = iUserService.modifyHead(part,description);
        call.enqueue(new Callback<ModifyHeadBeen>() {
            @Override
            public void onResponse(Call<ModifyHeadBeen> call, Response<ModifyHeadBeen> response) {
                ModifyHeadBeen modifyHeadBeen = response.body();
                tv_path.setText(modifyHeadBeen.getHead_path());
            }
            @Override
            public void onFailure(Call<ModifyHeadBeen> call, Throwable t) {

            }
        });
    }

    private void addNews(){

        byte[] b1 = head_b;
        byte[] b2 = head_b;
        byte[] b3 = head_b;

        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), head_b);
        //part1
        MultipartBody.Part part1 = MultipartBody.Part.createFormData("part1","",body);//partName, unKnow, requestBody
        MultipartBody.Part part2 = MultipartBody.Part.createFormData("part2","",body);
        MultipartBody.Part part3 = MultipartBody.Part.createFormData("part3","",body);

        String content = "今天天气不错";
        RequestBody requestBody_Content = RequestBody.create(null,content);

        Call<AddNewsBeen> call = iUserService.addNews(part1,part2,part3,null,null,null,requestBody_Content,
                RequestBody.create(null,"113.0002"),
                RequestBody.create(null,"22.0001"),
                RequestBody.create(null,"广州"));
        call.enqueue(new Callback<AddNewsBeen>() {
            @Override
            public void onResponse(Call<AddNewsBeen> call, Response<AddNewsBeen> response) {
                AddNewsBeen addNewsBeen = response.body();
                tv_data.setText(addNewsBeen.getNews().getContent());
            }
            @Override
            public void onFailure(Call<AddNewsBeen> call, Throwable t) {
            }
        });

    }


    private void addNewsPartMap(){
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), head_b);
        //MultipartBody.Part part1 = MultipartBody.Part.createFormData("part1","",body);
        Map<String, RequestBody> photos = new HashMap<>();
        photos.put("part1\"; filename=\"part1.png", body);
        photos.put("part2\"; filename=\"part2.png", body);
        photos.put("part3\"; filename=\"part3.png", body);
        photos.put("part4\"; filename=\"part4.png", body);
        Call<AddNewsBeen> call = iUserService.addNews(photos,
                RequestBody.create(null,"天气不错"),
                RequestBody.create(null,"113.0001"),
                RequestBody.create(null,"22.0001"),
                RequestBody.create(null,"四会"));
        call.enqueue(new Callback<AddNewsBeen>() {
            @Override
            public void onResponse(Call<AddNewsBeen> call, Response<AddNewsBeen> response) {
                AddNewsBeen addNewsBeen = response.body();
                tv_data.setText(addNewsBeen.getNews().getContent());
            }

            @Override
            public void onFailure(Call<AddNewsBeen> call, Throwable t) {

            }
        });
    }

    private void login(){
        Call<LoginUser> call = iUserService.login("xm","dixm","112.0001","22.0001");
        call.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                LoginUser loginUser = response.body();
                if (loginUser.isSeccess()) Toast.makeText(RetrofitActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(RetrofitActivity.this,"登录失败",Toast.LENGTH_LONG).show();
                    return;
                }
                //tv_data.setText(loginUser.getUser().getNickname());
                SharedPreferenceUtil.setSessionId(loginUser.getSession_id()+"");
                tv_data.setText( "session_id = " +SharedPreferenceUtil.getSessionId());
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {

            }
        });
    }

    private void getListDataTongbu(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<UserBeen> call = iUserService.getUserDataWithGet("xm","112.0001","22.0001");
                Response<UserBeen> response;
                try {
                    response = call.execute();
                    final UserBeen userBeen = response.body();
                    final String result = response.body().toString();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_data.setText("同步" +  userBeen.getUser().getNickname() + "\n" +
                                    userBeen.getUser().getSignature());
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void getListDataWithGet(){
        Call<UserBeen> call = iUserService.getUserDataWithGet("xm","112.0001","22.0001");
        call.enqueue(new Callback<UserBeen>() {
            @Override
            public void onResponse(Call<UserBeen> call, Response<UserBeen> response) {
                UserBeen userBeen = response.body();
                Log.d("RESPONSE", userBeen.toString());
                tv_data.setText( "get 方法 异步" + userBeen.getUser().getNickname() + "\n" +
                userBeen.getUser().getSignature());
        }

            @Override
            public void onFailure(Call<UserBeen> call, Throwable t) {

            }
        });
    }

    private void getListDataWithPost(){
        //调用一致
        Call<UserBeen> call = iUserService.getUserDataWithPost("xm","112.0001","22.0001");
        call.enqueue(new Callback<UserBeen>() {
            @Override
            public void onResponse(Call<UserBeen> call, Response<UserBeen> response) {
                UserBeen userBeen = response.body();
                //Log.d("RESPONSE", userBeen.toString());
                tv_data.setText( "post方法 异步" + userBeen.getUser().getNickname() + "\n" +
                        userBeen.getUser().getSignature());
            }

            @Override
            public void onFailure(Call<UserBeen> call, Throwable t) {

            }
        });
    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "xm.jpg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                byte[] b = CommentUtil.inputStramToByte(inputStream);
                iv_pic.setImageBitmap(CommentUtil.byteToBitmap(b, null));
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("Download", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (Exception e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
