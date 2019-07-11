package com.allever.android.sample.retrofit

import com.allever.android.sample.retrofit.bean.PrintData
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.http.*

interface PrintService {
    @GET("LotteryPrinterUsb/master/app/src/main/assets/print_config.json")
    fun printConfig(): Call<ResponseBody>

    //不行
    @POST("LotteryPrinterUsb/master/app/src/main/assets/print_config.json")
    fun postPrintConfig(): Call<ResponseBody>

    //不行
    @POST("api/weather/city/101030100")
    fun postWeather(): Call<ResponseBody>

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
}
