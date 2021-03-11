package com.example.cryptoverse.Home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.book_wizard.Currency
import com.example.cryptoverse.News.News
import com.example.cryptoverse.News.NewsItemClicked
import com.example.cryptoverse.R
import java.math.RoundingMode
import java.text.DecimalFormat


class CryptoAdapter(val context: Context,private val listener: CryptoItemClicked): RecyclerView.Adapter<CryptoAdapter.CryptoHolder>() {

    private val items:ArrayList<Currency> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)

        val vew = CryptoHolder(view)
        view.setOnClickListener{
            listener.OnCryptoClicked(items[vew.adapterPosition])
        }


        return vew

    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        val currentItem = items[position]

        val abb_name = currentItem.abb_name
        holder.name.text = currentItem.Name+" ("+abb_name+")"
        holder.price.text = roundOffDecimal(currentItem.price).toString()+" $"
        holder.marketcap.text =  roundOffDecimal(currentItem.MarketCap).toString()
        holder.hourly.text =  roundOffDecimal(currentItem.hour).toString()+"%"
        holder.daily.text =  roundOffDecimal(currentItem.daily).toString()+"%"
        holder.weekly.text =  roundOffDecimal(currentItem.weekly).toString()+"%"

        Glide.with(context)
            .load(currentItem.crypto_image)
            .apply(RequestOptions().override(300, 200))
            .into(holder.crypto_image)



    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun newLoading(){
        items.clear()
    }

    fun updateCrypto(cryptos: ArrayList<Currency>){
        items.clear()
        items.addAll(cryptos)
        notifyDataSetChanged()

    }
    class CryptoHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name:TextView = itemView.findViewById(R.id.name_id)
        var price:TextView = itemView.findViewById(R.id.price_id)
        var marketcap:TextView =  itemView.findViewById(R.id.marketcap_id)
        var hourly:TextView = itemView.findViewById(R.id.hourly)
        var daily:TextView = itemView.findViewById(R.id.daily)
        var weekly:TextView = itemView.findViewById(R.id.weekly)
        var crypto_image:ImageView = itemView.findViewById(R.id.crypto_image)


    }
    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}
interface CryptoItemClicked {
    fun OnCryptoClicked(item: Currency)
}
