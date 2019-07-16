package com.allever.android.sample.okhttp

import android.os.Bundle
import android.util.Log
import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity
import okhttp3.*
import java.io.IOException

class OkHttpTestActivity: BaseActivity() {
    private val TAG = OkHttpTestActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http_test)

        getOkHttpBase()
    }

    private fun getOkHttpBase(){
        val client = OkHttpClient()
        val request = Request.Builder()
            .get()
            .url("https://raw.githubusercontent.com/devallever/LotteryPrinterUsb/master/app/src/main/assets/print_config.json")
            .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse content = ${response.body()?.string()}")
            }

        })

        Thread(Runnable {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                Log.d(TAG, "onResponse content = ${response.body()?.string()}")
            } else {
                Log.d(TAG, "onFailure")
            }
        }).start()

    }
}