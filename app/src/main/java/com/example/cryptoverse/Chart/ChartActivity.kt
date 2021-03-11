package com.example.cryptoverse.Chart

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.cryptoverse.Home.CryptoAdapter
import com.example.cryptoverse.Home.MySingleton
import com.example.cryptoverse.R
import com.example.cryptoverse.database.CryptoDatabase
import com.example.cryptoverse.database.CryptoEntity
import com.robinhood.spark.SparkView
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView

import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList



lateinit var url:String
class ChartActivity : AppCompatActivity() {

    private lateinit var currentdata: ArrayList<CryptoData>
    private lateinit var adapter: CryptoSparkAdapter
   lateinit var favorite_btn:Button
   lateinit var sparkView:SparkView
    lateinit var date:TextView
    lateinit var close:RadioButton
    lateinit var ohlcGroup:RadioGroup
    lateinit var tickerView:TickerView
    lateinit var crypto_coin_fullname:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        val users = ArrayList<CryptoData>()
        favorite_btn = findViewById(R.id.favorite_btn)
        sparkView = findViewById(R.id.sparkView)
        date = findViewById(R.id.date)
        close = findViewById(R.id.close)
        ohlcGroup = findViewById(R.id.ohlcGroup)
        tickerView = findViewById(R.id.tickerView)
        crypto_coin_fullname = findViewById(R.id.crypto_coin_fullname)


        val abb_name = intent.getStringExtra("abb_name")
        val fullname = intent.getStringExtra("fullname")
        val imageurl = intent.getStringExtra("imageurl")


        url =
            "https://min-api.cryptocompare.com/data/histoday?fsym=$abb_name&tsym=USD&limit=183&aggregate=2"

        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                {


                    val response = it.getJSONArray("Data")

                    for (i in 0 until response.length()) {
                        val respos_i = response.getJSONObject(i)
                        val time = respos_i.getLong("time")
                        val open = respos_i.getDouble("open")
                        val low = respos_i.getDouble("low")
                        val high = respos_i.getDouble("high")
                        val close = respos_i.getDouble("close")
                        val new_crypto_data = CryptoData(time, high, low, open, close)
                        users.add(new_crypto_data)





                    }
                    if (fullname != null) {
                        update_chart_data(users,fullname)
                    }
                    setUpeventlisteners()


                   



                },
                {
                    Log.d("error", "volley error chart")

                })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)





    }

    private fun setUpeventlisteners() {

        sparkView.isScrubEnabled = true
        sparkView.setScrubListener {itemdata->
            if(itemdata is CryptoData){
                updateinfordate(itemdata)
            }

        }
        ohlcGroup.setOnCheckedChangeListener { _, checked_id ->
            when(checked_id){
                R.id.open->updatedisplay(Metric.open)
                R.id.close->updatedisplay(Metric.close)
                R.id.low->updatedisplay(Metric.low)
                R.id.high->updatedisplay(Metric.high)

            }
        }

    }

    private fun updatedisplay(metric: Metric) {

        val colorRes = when (metric){
            Metric.open->R.color.teal_700
            Metric.close->R.color.yellow
            Metric.high->R.color.red
            Metric.low->R.color.green
        }

        @ColorInt val colorint = ContextCompat.getColor(this,colorRes)
        sparkView.lineColor = colorint
        tickerView.setTextColor(colorint)
        crypto_coin_fullname.setTextColor(colorint)
        adapter.metric = metric
        adapter.notifyDataSetChanged()
        updateinfordate(currentdata.last())

    }

    private fun update_chart_data(users: ArrayList<CryptoData>,fullname:String) {
            
        currentdata = users
         adapter = CryptoSparkAdapter(users)
        sparkView.adapter = adapter
        close.isChecked = true
        
        crypto_coin_fullname.text = fullname.toUpperCase(Locale.ROOT)
        updatedisplay(Metric.close)
        

    }

    private fun updateinfordate(first: CryptoData) {

        val numcases = when(adapter.metric){
            Metric.low->first.low
            Metric.close->first.close
            Metric.high->first.high
            Metric.open->first.open
        }
        val x = "$numcases $"


        tickerView.setCharacterLists(TickerUtils.provideNumberList())

        tickerView.text = x



       /*val outputDateformat = SimpleDateFormat("yyyy,MM,dd", Locale.getDefault())
        val date_s = outputDateformat.format(first.time)
        date.text = date_s*/
        val date_s = Date(first.time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())

        date.text = format.format(date_s)
         

    }

   

}
