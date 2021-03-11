package com.example.cryptoverse

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoverse.Home.CryptoAdapter
import com.example.cryptoverse.Home.HomeFragment
import com.example.cryptoverse.News.News
import com.example.cryptoverse.News.NewsFragment
import com.example.cryptoverse.News.NewsListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle
    lateinit var url:String
    private lateinit var madapter: CryptoAdapter
    private lateinit var madapter1:NewsListAdapter

    var news_user = ArrayList<News>()

    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame,HomeFragment())
            .commit()
        drawer_layout.closeDrawers()
        setuptoolbar()

        linearLayoutManager = LinearLayoutManager(this)
        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()



        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Home -> {

                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, HomeFragment())
                            .commit()

                    supportActionBar?.title = "CryptoCurrencies"
                    drawer_layout.closeDrawers()

                }

                R.id.favorites -> Toast.makeText(
                    applicationContext,
                    "clicked favorites",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.news -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame,NewsFragment())
                            .commit()
                    supportActionBar?.title = "Current News"
                    drawer_layout.closeDrawers()
                }

            }
            true

        }




    }

    private fun setuptoolbar() {
        setSupportActionBar(toolbar)
        toolbar.title = "CryptoVerse"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.Home){
            drawer_layout.openDrawer(GravityCompat.START)
        }
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }






}