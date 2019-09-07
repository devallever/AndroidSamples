//package com.allever.android.sample.okhttp
//
//import android.os.Bundle
//import android.util.Log
//import com.allever.android.sample.R
//import com.allever.lib.common.app.BaseActivity
//import okhttp3.*
//import java.io.IOException
//
//class OkHttpTestActivity : BaseActivity() {
//    private val TAG = OkHttpTestActivity::class.java.simpleName
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_ok_http_test)
//
//        //okhttp get请求
////        getOkHttpBase()
//        //okhttp post请求
////        postOkHttpBase()
//        //OkHttp 上传文件表单
//        postOkHttpUploadFile()
//
//    }
//
//    private fun postOkHttpUploadFile() {
//        val requestBody = MultipartBody.Builder()
//            .addPart(
//                Headers.of(
//                    "Content-Disposition",
//                    "form-data; name=\"part1" + "\""
//                ),
//                RequestBody.create(MediaType.parse("application/octet-stream"), ByteArray(1024))
//            )
//            .addPart(RequestBody.create(MediaType.parse("application/octet-stream"), ByteArray(1024)))
//            .addPart(MultipartBody.create(MediaType.parse("application/octet-stream"), ByteArray(1024)))
//            .addFormDataPart("key", "value")
//            .build()
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .post(requestBody)
//            .url("http://27.54.249.252:8080/SocialServer/ModifyHeadServlet")
//            .addHeader("Cookie", "JSESSIONID=156826495215485624")
//            .build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d(TAG, "onFailure")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                Log.d(TAG, "onResponse content = ${response.body()?.string()}")
//            }
//
//        })
//    }
//
//    private fun postOkHttpBase() {
//        val requestBody = FormBody.Builder()
//            .add("libcode", "P3GD0755006")
//            .add("deviceId", "")
//            .add("cmd", "listSearchActions")
//            .build()
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .post(requestBody)
//            .url("http://rc.interlib.com.cn:8088/rcrobotsite/rest/web/api/action/mb/mobileApi")
//            .build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d(TAG, "onFailure")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                Log.d(TAG, "onResponse content = ${response.body()?.string()}")
//            }
//
//        })
//    }
//
//    private fun getOkHttpBase() {
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .get()
//            .url("https://raw.githubusercontent.com/devallever/LotteryPrinterUsb/master/app/src/main/assets/print_config.json")
//            .build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d(TAG, "onFailure")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                Log.d(TAG, "onResponse content = ${response.body()?.string()}")
//            }
//
//        })
//
//        Thread(Runnable {
//            val response = client.newCall(request).execute()
//            if (response.isSuccessful) {
//                Log.d(TAG, "onResponse content = ${response.body()?.string()}")
//            } else {
//                Log.d(TAG, "onFailure")
//            }
//        }).start()
//
//    }
//}