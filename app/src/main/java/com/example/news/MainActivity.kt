package com.example.news

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private val newsList: ArrayList<News> = ArrayList()
    private val newsUrl = "https://gnews.io/api/v4/top-headlines?country=in&token=ecbd80ff09b6921d04985ddbb59bd8e2&lang=en"
    private lateinit var mNewsAdapter : NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        fetchData()
        mNewsAdapter = NewsAdapter(this,this)
        recyclerView.adapter = mNewsAdapter
    }

    override fun onItemClicked(item: News) {

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))


    }

     private fun fetchData(){

         val request = JsonObjectRequest(Request.Method.GET,newsUrl, null,
                 {
                        val jsonArray =  it.getJSONArray("articles")
                         for(i in 0 until jsonArray.length()){

                             var jsonObject1 = jsonArray.getJSONObject(i)
                             val description = jsonObject1.getString("description")
                             val url = jsonObject1.getString("url")
                             val imageUrl = jsonObject1.getString("image")
                             val title = jsonObject1.getString("title")

                             val source = jsonObject1.getJSONObject("source")
                             val name = source.getString("name")

                             newsList.add(News(title,name,description,url,imageUrl))
                         }

                        mNewsAdapter.updateNews(newsList)

                 },
                 {
                             Toast.makeText(this,"News not Found!",Toast.LENGTH_LONG).show()
                 })

         MySingleton.getInstance(this).addToRequestQueue(request)
     }
}