package com.allever.mysimple.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Allever on 2016/12/6.
 */

public interface RetrofitAPIService {

    //获取分类
    @GET("api/lore/classify")
    Call<ClassfyBean> getClassfyData();

    //获取列表数据
    @GET("api/lore/list")
    Call<ClassfyBean> getListData(@Query("id") int id);

    //获取详细信息
    @GET("api/lore/show")
    Call<ClassfyBean> getDetailData(@Query("id") int id);



}
