package com.allever.mysimple.rxRetrofit.retrofit;

import com.allever.mysimple.nearbyProject.pojo.NearbyNewsRoot;
import com.allever.mysimple.rxRetrofit.bean.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Allever on 2017/1/15.
 */

public interface RetrofitService {
    @Headers("")
    @GET("UserDataServlet")
    Observable<User> getSignature(@Query("check_user_id") String username);

    @FormUrlEncoded
    @POST("NearbyNewsListServlet")
    Observable<NearbyNewsRoot> getNearbyNews(@Field("longitude") String longitude,
                                             @Field("latitude") String latitude,
                                             @Field("page") String page);

//    @GET("NearbyNewsListServlet")
//    Observable<NearbyNewsRoot> getNearbyNews(@Query("longitude") String longitude,
//                                             @Query("latitude") String latitude,
//                                             @Query("page") String page);
}
