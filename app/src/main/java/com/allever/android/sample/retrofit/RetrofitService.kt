package com.allever.android.sample.retrofit

import com.allever.android.sample.retrofit.bean.PrintData
import io.reactivex.Observable
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("LotteryPrinterUsb/master/app/src/main/assets/print_config.json")
    fun printConfig(msg: String=""): Call<ResponseBody>

    @GET("LotteryPrinterUsb/master/app/src/main/assets/print_config.json")
    fun printConfig(): Observable<ResponseBody>

    //不行
    @POST("LotteryPrinterUsb/master/app/src/main/assets/print_config.json")
    fun postPrintConfig(): Call<ResponseBody>

    @GET("api/weather/city/{cityId}")
    fun getWeather(@Path("cityId") cityId: String): Call<ResponseBody>

    @GET()
    fun getWeatherWithUrl(@Url url: String): Call<ResponseBody>

    /*
    val url = "${BuildConfig.BASE_ACTION}/rcrobotsite/rest/web/api/action/mb/mobileApi"
        val parameterList = listOf(
            "libcode" to BuildConfig.GZChildLib,
            "deviceId" to "",
            "cmd" to "listSearchActions"
        )
     */
    @FormUrlEncoded
    @POST("rcrobotsite/rest/web/api/action/mb/mobileApi")
    fun postActivityList(@Field("libcode") libcode: String,
                         @Field("deviceId") deviceId: String = "",
                         @Field("cmd") cmd: String = "listSearchActions")
            : Call<ResponseBody>

    @GET("rcrobotsite/rest/web/api/action/mb/mobileApi")
    fun getActivityList(@Query("libcode") libcode: String,
                         @Query("deviceId") deviceId: String = "",
                         @Query("cmd") cmd: String = "listSearchActions")
            : Call<ResponseBody>

    //@Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @Headers("Content-Type:application/json")
    @GET("rcrobotsite/rest/web/api/action/mb/mobileApi")
    fun getActivityListWithHeader(@Query("libcode") libcode: String,
                        @Query("deviceId") deviceId: String = "",
                        @Query("cmd") cmd: String = "listSearchActions")
            : Call<ResponseBody>


    @Streaming
    @GET("MyCoolWeather/master/app/simpleWeather1.4.apk")
    fun downloadBigFile(): Call<ResponseBody>

    @POST("rcrobotsite/rest/web/api/action/mb/mobileApi")
    fun postJson(@Body printData: PrintData): Call<ResponseBody>
}
