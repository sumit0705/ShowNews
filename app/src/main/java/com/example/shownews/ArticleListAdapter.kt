package com.example.shownews

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.example.shownews.models.Article
import com.squareup.picasso.Picasso


class ArticleListAdapter(private val listener: List<Article>): RecyclerView.Adapter<ArticleViewHolder>() {

    private var articlesList: MutableList<Article> =ArrayList()
    private lateinit var context: Context

    constructor(articlesList: MutableList<Article>,context: Context) : this(articlesList) {
        this.articlesList = articlesList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ArticleViewHolder = ArticleViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.news_item_layout,parent,false))


    override fun getItemCount(): Int = articlesList.size


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = articlesList[position]

        holder.titleText.text = currentItem.title
        holder.authorText.text = StringBuilder().append("By ").append(currentItem.author)
        Picasso.get().load(currentItem.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(context, Uri.parse(articlesList[position].url))
        }
    }
}

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleText: TextView = itemView.findViewById(R.id.title)
    val authorText: TextView = itemView.findViewById(R.id.author)
    val newsImage: ImageView = itemView.findViewById(R.id.image)
}

