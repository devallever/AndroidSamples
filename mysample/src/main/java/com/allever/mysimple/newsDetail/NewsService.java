package com.allever.mysimple.newsDetail;

import com.allever.mysimple.mvpTest.retrofit.RetrofitService;

import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allever on 17-5-26.
 */

public interface NewsService {
    /*
    @GET("T1348649654285/0-20.html")
    Observable<NewsSummary> getNewsList();
    */
/*    @GET("T1348649654285/0-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList();*/

    @GET("/T1348649654285/0-20.html")
    Observable<List<NewsSummary>> getNewsList();
}
