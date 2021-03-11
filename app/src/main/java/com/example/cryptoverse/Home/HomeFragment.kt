package com.example.cryptoverse.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.book_wizard.Currency
import com.example.cryptoverse.Chart.ChartActivity
import com.example.cryptoverse.News.News
import com.example.cryptoverse.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_home.*

import org.json.JSONException
import kotlin.collections.ArrayList


class HomeFragment : Fragment(),CryptoItemClicked {
    var loading = true

    private lateinit var madapter: CryptoAdapter
    var limit_toplist=10
    var page_toplist = 0
    var crypto_names = arrayOf("24H Volume",
            "24H Volume Top Tier", "Market Cap")
    private var endy = "totalvolfull"
    val numbersMap = mapOf("24H Volume" to "totalvolfull", "24H Volume Top Tier" to "totaltoptiervolfull", "Market Cap" to "mktcapfull")


    var users = ArrayList<Currency>()
    var displayList = ArrayList<Currency>()
    var selectedIndex = 0
     lateinit var pig:ProgressBar
    var endii:String="totalvolfull"
    lateinit var star:ImageView




    private lateinit var mLayoutManager:LinearLayoutManager


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)

        val menuitem = menu.findItem(R.id.action_search)
        if(menuitem!=null){

            val searchview = menuitem.actionView as SearchView


            searchview.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {



                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {


                    return true
                }

            })
        }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_search -> {

            }
            R.id.action_filter -> {
                selecteditem()

            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun selecteditem(){
        val context: Context = ContextThemeWrapper(this.context, R.style.AppTheme2)
        val langDialog = MaterialAlertDialogBuilder(context)

        var selectName = crypto_names[selectedIndex]

        langDialog.setTitle("Sort By")
        langDialog.setSingleChoiceItems(crypto_names, selectedIndex){ dialog, which->
            selectedIndex = which
            selectName = crypto_names[selectedIndex]
        }
        langDialog.setPositiveButton("Ok"){ dialog, which->
            TextMessage("$selectName is selected")
            val selecty = numbersMap[selectName]
            if (selecty != null) {
                getCryptos(selecty)
            }

        }
        langDialog.setNeutralButton("Cancel"){ dialog, which->
            dialog.dismiss()
        }
        langDialog.show()

    }

    private fun TextMessage(mag: String){
        Toast.makeText(this.context, "pressed $mag", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        setHasOptionsMenu(true)

        madapter  = CryptoAdapter(requireContext(),this)
        pig = view.findViewById(R.id.pg)

        pig.visibility = View.VISIBLE
        getCryptos(endii)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

         mLayoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.adapter = madapter

        super.onViewCreated(view, savedInstanceState)
    }




    private fun getCryptos(naf:String) {
        pig.visibility = View.VISIBLE
        madapter.newLoading()
           val url = "https://min-api.cryptocompare.com/data/top/$naf?limit=30&tsym=USD"



// Request a string response from the provided URL.
            val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    {
                        try {

                            val crypto_array = it.getJSONArray("Data")
                            for (i in 0 until crypto_array.length()) {
                                val crypto_json_object = crypto_array.getJSONObject(i)
                                val coin_info = crypto_json_object.getJSONObject("CoinInfo")
                                Log.d("coinfo","has been found")
                                val raw = crypto_json_object.getJSONObject("RAW")
                                val usd = raw.getJSONObject("USD")
                                val price = usd.getDouble("PRICE")
                                val marketCap = usd.getDouble("MKTCAP")
                                val hourly = usd.getDouble("CHANGEPCTHOUR")
                                val daily = usd.getDouble("CHANGEPCT24HOUR")
                                val weekly = usd.getDouble("CHANGEPCTDAY")
                                val name = coin_info.getString("FullName")
                                val abb_name = coin_info.getString("Name")
                                val image_url = usd.getString("IMAGEURL")
                                val crypto_url = "https://www.cryptocompare.com$image_url"
                                val new_user = Currency(abb_name,name, price, marketCap, hourly, daily, weekly, crypto_url)
                                users.addAll(listOf(new_user))


                            }
                            displayList.addAll(users)
                            Handler().postDelayed({
                                madapter.updateCrypto(displayList)

                                pig.visibility = View.GONE

                            }, 1000)



                            Log.d("success", "good response")

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


                    },
                    {
                        Log.d("error", "volley error")

                    })

// Add the request to the RequestQueue.
            MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest)
        }

    override fun OnCryptoClicked(item: Currency) {
       val intent = Intent(context,ChartActivity::class.java)
        intent.putExtra("abb_name",item.abb_name)
        intent.putExtra("fullname",item.Name)
        startActivity(intent)
    }
}


