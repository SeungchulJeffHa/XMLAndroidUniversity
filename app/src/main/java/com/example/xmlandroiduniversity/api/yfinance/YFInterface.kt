package com.example.xmlandroiduniversity.api.yfinance

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.xmlandroiduniversity.data.retrofit.*

interface YFInterface {
    @GET("/v1/finance/search")
    fun search(@Query("q") query: String): Call<YahooFinanceResponse>
}