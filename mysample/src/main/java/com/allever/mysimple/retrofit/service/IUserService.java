package com.allever.mysimple.retrofit.service;

import com.allever.mysimple.retrofit.been.AddNewsBeen;
import com.allever.mysimple.retrofit.been.LoginUser;
import com.allever.mysimple.retrofit.been.ModifyHeadBeen;
import com.allever.mysimple.retrofit.been.UserBeen;
import com.allever.mysimple.retrofit.util.SharedPreferenceUtil;

import org.w3c.dom.ProcessingInstruction;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Allever on 2016/12/6.
 */

public interface IUserService {
    final String JSESSIONID = SharedPreferenceUtil.getSessionId();
    //public static final String BASE_URL = "http://192.168.23.1:8080/SocialServer/";
    @FormUrlEncoded
    @POST("UserDataServlet")
    Call<UserBeen> getUserDataWithPost(@Field("check_user_id") String username
            ,@Field("longitude") String str_longitude
            ,@Field("latitude") String str_latitude);

    @GET("UserDataServlet")
    Call<UserBeen> getUserDataWithGet(@Query("check_user_id") String username,
                               @Query("longitude") String str_longitude,
                               @Query("latitude") String str_latitude);

    //登录
    @FormUrlEncoded
    @POST("LoginServlet")
    Call<LoginUser> login(@Field("username") String username,
                          @Field("password") String password,
                          @Field("longitude") String str_longitude,
                          @Field("latitude") String str_latitude);

    //修改头像
    //普通表单 description
    @Multipart
    @POST("ModifyHeadServlet")
    Call<ModifyHeadBeen> modifyHead(@Part MultipartBody.Part head_img, @Part("description") RequestBody description);


    //发布动态
    //多文件/图片上传
    @Multipart
    @POST("AddNewsServlet")
    Call<AddNewsBeen> addNews(@Part MultipartBody.Part part1,
                              @Part MultipartBody.Part part2,
                              @Part MultipartBody.Part part3,
                              @Part MultipartBody.Part part4,
                              @Part MultipartBody.Part part5,
                              @Part MultipartBody.Part part6,
                              @Part("content") RequestBody content,
                              @Part("longitude") RequestBody longitude,
                              @Part("latitude") RequestBody latitude,
                              @Part("city") RequestBody city);


    @Multipart
    @POST("AddNewsServlet")
    Call<AddNewsBeen> addNews(@PartMap Map<String, RequestBody> partMap,
                              @Part("content") RequestBody content,
                              @Part("longitude") RequestBody longitude,
                              @Part("latitude") RequestBody latitude,
                              @Part("city") RequestBody city);

    //.addHeader("Cookie", "JSESSIONID=" + SharedPreferenceUtil.getSessionId())

    @GET()
    Call<ResponseBody> downloadPic(@Url String url);

    @Streaming
    @GET()
    Call<ResponseBody> downloadAPK(@Url String url);

}
