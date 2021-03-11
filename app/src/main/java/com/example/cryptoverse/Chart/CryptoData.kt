package com.example.cryptoverse.Chart

import com.google.gson.annotations.SerializedName

data class CryptoData(
    @SerializedName("time")val time:Long,
    @SerializedName("high")val high:Double,
    @SerializedName("low")val low:Double,
    @SerializedName("open")val open:Double,
    @SerializedName("close")val close:Double
)