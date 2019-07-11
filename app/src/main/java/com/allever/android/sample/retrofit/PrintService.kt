package com.allever.android.sample.retrofit

import com.allever.android.sample.retrofit.bean.PrintData

import retrofit2.Call
import retrofit2.http.GET

interface PrintService {
    @get:GET("LotteryPrinterUsb/master/app/src/main/assets/print_config.json")
    val printConfig: Call<PrintData>
}
