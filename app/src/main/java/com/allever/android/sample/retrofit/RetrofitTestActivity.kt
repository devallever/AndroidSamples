package com.allever.android.sample.retrofit

import android.os.Bundle
import com.allever.android.sample.R
import com.allever.android.sample.retrofit.bean.PrintData
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.DLog
import com.allever.lib.common.util.ToastUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_test)

        Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/devallever/")
                //要转换则需要添加addConverterFactory
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrintService::class.java)
                .printConfig
                .enqueue(object : Callback<PrintData> {
                    override fun onFailure(call: Call<PrintData>, t: Throwable) {
                        ToastUtils.show("onFailure")
                        DLog.d("onFailure")
                    }

                    override fun onResponse(call: Call<PrintData>, response: Response<PrintData>) {
                        ToastUtils.show("onResponse")
                        DLog.d("onResponse")
                    }

                })
    }
}