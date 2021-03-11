package com.example.cryptoverse.Chart

import com.robinhood.spark.SparkAdapter

class CryptoSparkAdapter(private val users: ArrayList<CryptoData>):SparkAdapter() {

    var metric = Metric.close
    var daysAgo = TimeScale.Max
    override fun getCount()=users.size

    override fun getItem(index: Int)=users[index]

    override fun getY(index: Int): Float {
        val crypto_item = users[index]

        return when(metric){
            Metric.close->crypto_item.close.toFloat()
            Metric.open->crypto_item.open.toFloat()
            Metric.high->crypto_item.high.toFloat()
            Metric.low->crypto_item.low.toFloat()
        }

    }


}
