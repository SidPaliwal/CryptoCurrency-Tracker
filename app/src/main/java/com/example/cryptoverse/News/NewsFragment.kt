package com.example.cryptoverse.News

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.cryptoverse.Home.MySingleton
import com.example.cryptoverse.R
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment(),NewsItemClicked {

    var news_user = ArrayList<News>()
    lateinit var madapter:NewsListAdapter
    private lateinit var url:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_news, container, false)






        madapter = NewsListAdapter(this.requireContext(),this)
        getNews()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recycler_view2.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        recycler_view2.adapter = madapter
        super.onViewCreated(view, savedInstanceState)

    }
    private fun getNews() {

        url = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val news_array = it.getJSONArray("Data")

                for (i in 0 until news_array.length()) {
                    val news_json_object = news_array.getJSONObject(i)
                    val imageurl = news_json_object.getString("imageurl")
                    val news_url = news_json_object.getString("url")
                    val title = news_json_object.getString("title")
                    val body = news_json_object.getString("body")
                    val source_object = news_json_object.getJSONObject("source_info")
                    val source = source_object.getString("name")

                    val news = News(source, title, body, news_url, imageurl)
                    news_user.addAll(listOf(news))


                }


                madapter.updateNews(news_user)
                Log.d("success", "good response")


            },
            {
                Log.d("error", "volley error")

            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest)
    }

    override fun OnItemClicked(item: News) {
       val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(item.url))



    }


}