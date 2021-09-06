package com.example.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val context: Context, private val listener: NewsItemClicked): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.news_title)
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val description: TextView = itemView.findViewById(R.id.news_description)
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutAdapter = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        val viewHolder =  NewsViewHolder(layoutAdapter)
        layoutAdapter.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val  item = items[position]
        holder.title.text = item.news_title
        holder.description.text = item.news_description
        holder.name.text = item.name
        Glide.with(this.context).load(item.image_url).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

interface NewsItemClicked{
    fun onItemClicked(item:News)
}
