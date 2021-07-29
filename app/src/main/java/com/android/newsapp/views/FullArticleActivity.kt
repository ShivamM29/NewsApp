package com.android.newsapp.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.newsapp.databinding.ActivityFullArticleBinding

class FullArticleActivity : AppCompatActivity() {
    private var binding: ActivityFullArticleBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullArticleBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val bundle = intent.extras
        val url = bundle?.getString("url")

        Log.i("FullArticle", "URl is $url")
        url?.let {
            binding?.newsWebView?.loadUrl(it)
        }
    }
}