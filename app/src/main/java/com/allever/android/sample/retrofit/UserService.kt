package com.allever.android.sample.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by Allever on 2016/12/6.
 */

interface UserService {
    @FormUrlEncoded
    @POST("UserDataServlet")
    fun getUserDataWithPost(@Field("check_user_id") username: String, @Field("longitude") str_longitude: String, @Field("latitude") str_latitude: String): Call<ResponseBody>

    @GET("UserDataServlet")
    fun getUserDataWithGet(@Query("check_user_id") username: String,
                           @Query("longitude") str_longitude: String,
                           @Query("latitude") str_latitude: String)
            : Call<ResponseBody>

    //登录
    @FormUrlEncoded
    @POST("LoginServlet")
    fun login(@Field("username") username: String,
              @Field("password") password: String,
              @Field("longitude") str_longitude: String,
              @Field("latitude") str_latitude: String)
            : Call<ResponseBody>

    //修改头像
    //普通表单 description
    @Multipart
    @POST("ModifyHeadServlet")
    fun modifyHead(@Part head_img: MultipartBody.Part,
                   @Part("description") description: RequestBody)
            : Call<ResponseBody>


    //发布动态
    //多文件/图片上传
    @Multipart
    @POST("AddNewsServlet")
    fun addNews(@Part part1: MultipartBody.Part?,
                @Part part2: MultipartBody.Part?,
                @Part part3: MultipartBody.Part?,
                @Part part4: MultipartBody.Part?,
                @Part part5: MultipartBody.Part?,
                @Part part6: MultipartBody.Part?,
                @Part("content") content: RequestBody,
                @Part("longitude") longitude: RequestBody,
                @Part("latitude") latitude: RequestBody,
                @Part("city") city: RequestBody)
            : Call<ResponseBody>


    @Multipart
    @POST("AddNewsServlet")
    fun addNews(@PartMap partMap: Map<String, RequestBody>,
                @Part("content") content: RequestBody,
                @Part("longitude") longitude: RequestBody,
                @Part("latitude") latitude: RequestBody,
                @Part("city") city: RequestBody)
            : Call<ResponseBody>

    @GET
    fun downloadPic(@Url url: String): Call<ResponseBody>

    @Streaming
    @GET
    fun downloadAPK(@Url url: String): Call<ResponseBody>

}
