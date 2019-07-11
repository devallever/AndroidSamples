package com.allever.android.sample.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

public interface APITest {
    @POST("heep")
    Call<ResponseBody> postRequest();
}
