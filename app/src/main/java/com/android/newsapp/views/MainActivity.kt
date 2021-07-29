package com.android.newsapp.views

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.newsapp.adapters.NewsAdapter
import com.android.newsapp.databinding.ActivityMainBinding
import com.android.newsapp.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NewsAdapter.OnClickEvent {
    private var binding: ActivityMainBinding? = null
    private var newsViewModel: NewsViewModel? = null
    private var newsAdapter: NewsAdapter? = null
    private var connectivityManager: ConnectivityManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        registerNetworkCallback()

        newsAdapter = NewsAdapter(this, this)
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        newsViewModel?.mutableNewsList?.observe(this, {
            if (it.isSuccessful){
                binding?.progressBar?.visibility = View.GONE
                val list = it.body()?.articles?.toCollection(ArrayList())!!
                newsAdapter?.submitList(list)
                binding?.newsRecyclerView?.adapter = newsAdapter

            }else{
                Toast.makeText(this, "${it.errorBody()} error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getNewsData(){
        val queryMap = HashMap<String, String>()
        queryMap["apiKey"] = "69c4f55308f44d3ab75aeea26cbe1340"
        queryMap["sortBy"] = "publishedAt"
        queryMap["from"] = "2021-07-28"
        queryMap["q"] = "tesla"

        newsViewModel?.getData(queryMap)
    }

    override fun onNewsClick(articleUrl: String) {
        val intent = Intent(this, FullArticleActivity::class.java)
        intent.putExtra("url", articleUrl)
        startActivity(intent)
    }

    private fun registerNetworkCallback(){
        connectivityManager = getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        try{
            connectivityManager?.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: android.net.Network) {
                    super.onAvailable(network)
                    getNewsData()
                }

                override fun onLost(network: android.net.Network) {
                    super.onLost(network)
                    Snackbar.make(binding?.root!!, "No Connection!", Snackbar.LENGTH_LONG).show()
                }
            })
        }catch (e: Exception){
            Snackbar.make(binding?.root!!, "opps! Something went wrong", Snackbar.LENGTH_SHORT).show()
        }
    }
}