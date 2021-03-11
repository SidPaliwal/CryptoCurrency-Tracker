package com.example.cryptoverse.Chart

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val base_url = "https://min-api.cryptocompare.com/data/"


interface ChartService {

    @GET("histoday?tsym=USD&limit=183&aggregate=2")
    fun getCryptoDataYearly(@Query("fsym")fsym:String):Call<List<CryptoData>>

    @GET("histohour?&tsym=USD&limit=240&aggregate=3")
    fun getDataMonthly(@Query("fsym")fsym:String):Call<List<CryptoData>>

    @GET("histominute?&tsym=USD&limit=144&aggregate=10")
    fun getDataDaily(@Query("fsym")fsym:String):Call<List<CryptoData>>

    @GET("histohour?&tsym=USD&limit=240&aggregate=14")
    fun getData3Months(@Query("fsym")fsym:String):Call<List<CryptoData>>

    @GET("histoday?&tsym=USD&allData=true")
    fun getAllData(@Query("fsym")fsym:String): Call<List<CryptoData>>
}