package com.allever.mysimple.mvpTest.retrofit;

import com.allever.mysimple.mvpTest.bean.User;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Allever on 2017/1/19.
 */

public interface RetrofitService {
    @GET("UserDataServlet")
    Observable<User> getSignature(@Query("check_user_id") String username);
}
