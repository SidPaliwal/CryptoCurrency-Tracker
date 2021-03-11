package com.example.cryptoverse.Chart

enum class Metric{
    open,close,high,low
}

enum class TimeScale(val numdays:Int){

    Week(numdays = 7),
    Month(numdays=30),
    Max(numdays=-1)

}