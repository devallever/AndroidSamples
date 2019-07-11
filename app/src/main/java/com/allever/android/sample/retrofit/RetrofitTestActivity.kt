package com.allever.android.sample.retrofit

import android.os.Bundle
import com.allever.android.sample.R
import com.allever.android.sample.retrofit.bean.PrintData
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.DLog
import com.allever.lib.common.util.ToastUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_test)

        //GET异步请求
//        getAsyncRequest()
        //GET同步请求
        //getSyncRequest()
        //Post异步请求Field
//        postAsyncRequest()
        //GET异步请求Query
//        getAsyncRequestQuery()
        //GET异步请求Path动态url路径
//        getAsyncRequestPath()
        //GET异步转换url
        getAsyncRequestUrl()
    }

    private fun getAsyncRequestUrl() {
        Retrofit.Builder()
                .baseUrl("http://rc.interlib.com.cn:8088/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrintService::class.java)
                .getWeatherWithUrl("http://t.weather.itboy.net/api/weather/city/101030100")
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        ToastUtils.show("onFailure")
                        DLog.d("onFailure")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        ToastUtils.show("onResponse")
                        DLog.d("onResponse content = ${response.body()?.string()}")
                    }

                })
    }
    private fun getAsyncRequestPath() {
        Retrofit.Builder()
                .baseUrl("http://t.weather.itboy.net/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrintService::class.java)
                .getWeather("101030100")
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        ToastUtils.show("onFailure")
                        DLog.d("onFailure")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        ToastUtils.show("onResponse")
                        DLog.d("onResponse content = ${response.body()?.string()}")
                    }

                })
    }

    private fun getAsyncRequestQuery() {
        Retrofit.Builder()
                .baseUrl("http://rc.interlib.com.cn:8088/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrintService::class.java)
                .getActivityList("P3GD0755006")
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        ToastUtils.show("onFailure")
                        DLog.d("onFailure")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        ToastUtils.show("onResponse")
                        DLog.d("onResponse content = ${response.body()?.string()}")
                    }

                })
    }
    private fun postAsyncRequest() {
        Retrofit.Builder()
                .baseUrl("http://rc.interlib.com.cn:8088/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrintService::class.java)
                .postActivityList("P3GD0755006")
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        ToastUtils.show("onFailure")
                        DLog.d("onFailure")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        ToastUtils.show("onResponse")
                        DLog.d("onResponse content = ${response.body()?.string()}")
                    }

                })
    }

    private fun getSyncRequest() {
        Thread(Runnable {
            val response = Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com/devallever/")
                    //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PrintService::class.java)
                    .printConfig()
                    .execute()
            if (response.isSuccessful) {
                DLog.d("success content = ${response.body()?.string()}")
            } else {
                DLog.d("fail")
            }
        }).start()

    }

    private fun getAsyncRequest() {
        Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/devallever/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrintService::class.java)
                .printConfig()
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        ToastUtils.show("onFailure")
                        DLog.d("onFailure")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        ToastUtils.show("onResponse")
                        DLog.d("onResponse content = ${response.body()?.string()}")
                    }

                })
    }
}