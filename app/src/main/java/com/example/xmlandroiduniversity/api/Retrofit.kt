package com.example.xmlandroiduniversity.api

import com.example.xmlandroiduniversity.api.yfinance.YFInterface
import com.example.xmlandroiduniversity.global.Constant.Companion.BASE_URL_YF
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YFRetrofitClient {

    val retrofitYF = Retrofit.Builder()
        .baseUrl(BASE_URL_YF)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): YFInterface {
        return retrofitYF.create(YFInterface::class.java)
    }
}