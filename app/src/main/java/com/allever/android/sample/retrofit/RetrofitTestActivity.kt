package com.allever.android.sample.retrofit

import android.os.Bundle
import com.allever.android.sample.R
import com.allever.android.sample.retrofit.bean.PrintData
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.DLog
import com.allever.lib.common.util.ToastUtils
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap

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
//        getAsyncRequestUrl()
        //GET异步下载大文件
//        downloadBigFile()
        //GET异步添加header
//        getAsyncRequestWithHeader()
//        getAsyncRequestWithHeader2()
        //POST异步上传图片(表单: 文件和文本)
//        postAsyncUploadFile()
//        postAsyncUploadFile2()
//        postAsyncUploadFile3()
        //POST异步上传json
        postJson()
    }

    private fun postJson() {
        val printData = PrintData()
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/devallever/")
            //要转换则需要添加addConverterFactory
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
            .postJson(printData)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    ToastUtils.show("onFailure")
                    DLog.d("onFailure")
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    ToastUtils.show("onResponse")
                    DLog.d("onResponse content = ${response.body()?.byteStream()}")
                }

            })
    }

    private fun postAsyncUploadFile3() {
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), ByteArray(1024))
        //MultipartBody.Part part1 = MultipartBody.Part.createFormData("part1","",body);
        val photos = HashMap<String, RequestBody>()
        photos["part1\"; filename=\"part1.png"] = body
        photos["part2\"; filename=\"part2.png"] = body
        photos["part3\"; filename=\"part3.png"] = body
        photos["part4\"; filename=\"part4.png"] = body

        val content = RequestBody.create(null, "今天天气不错")
        val longitude = RequestBody.create(null, "113.0002")
        val latitude = RequestBody.create(null, "22.0001")
        val city = RequestBody.create(null, "广州")

        Retrofit.Builder()
                .baseUrl("http://27.54.249.252:8080/SocialServer/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
                .addNews(photos,  content, longitude, latitude, city)
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

    private fun postAsyncUploadFile2() {
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), ByteArray(1024))
        //part1
        val part1 = MultipartBody.Part.createFormData("part1", "", body)//partName, unKnow, requestBody
        val part2 = MultipartBody.Part.createFormData("part2", "", body)
        val part3 = MultipartBody.Part.createFormData("part3", "", body)

        val content = RequestBody.create(null, "今天天气不错")
        val longitude = RequestBody.create(null, "113.0002")
        val latitude = RequestBody.create(null, "22.0001")
        val city = RequestBody.create(null, "广州")

        Retrofit.Builder()
                .baseUrl("http://27.54.249.252:8080/SocialServer/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
                .addNews(part1, part2, part3, null, null, null, content, longitude, latitude, city)
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

    private fun postAsyncUploadFile() {
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), ByteArray(1024))
        //head_img 是 partName  filename
        val part = MultipartBody.Part.createFormData("head_img", "head_img", body)

        val description = RequestBody.create(MediaType.parse("multipart/form-data"), "修改头像")

        Retrofit.Builder()
                .baseUrl("http://27.54.249.252:8080/SocialServer/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
                .modifyHead(part, description)
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

    private fun getAsyncRequestWithHeader2() {
        Retrofit.Builder()
                .baseUrl("http://rc.interlib.com.cn:8088/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
                .getActivityListWithHeader("P3GD0755006")
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


    private fun getAsyncRequestWithHeader() {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    .addHeader("Cookie", "JSESSIONID=456895235648524896")
            val request = requestBuilder.build()

            chain.proceed(request)
        }
        val okHttpClient = clientBuilder.build()

        Retrofit.Builder()
                .baseUrl("http://rc.interlib.com.cn:8088/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetrofitService::class.java)
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

    private fun downloadBigFile() {
        Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/devallever/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
                .downloadBigFile()
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        ToastUtils.show("onFailure")
                        DLog.d("onFailure")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        ToastUtils.show("onResponse")
                        DLog.d("onResponse content = ${response.body()?.byteStream()}")
                    }

                })
    }

    private fun getAsyncRequestUrl() {
        Retrofit.Builder()
                .baseUrl("http://rc.interlib.com.cn:8088/")
                //要转换则需要添加addConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
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
                .create(RetrofitService::class.java)
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
                .create(RetrofitService::class.java)
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
                .create(RetrofitService::class.java)
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
                    .create(RetrofitService::class.java)
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
                .create(RetrofitService::class.java)
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