package com.zf.gank.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zf.gank.R
import com.zf.gank.databinding.ArticleItemLayoutBinding
import com.zf.gank.pojo.Article
import com.zf.jetpackmvvm.widget.easyrecyclerview.BaseRecyclerViewHolder
import com.zf.jetpackmvvm.widget.easyrecyclerview.EasyRecyclerViewAdapter


class ArticleAdapter() :
    EasyRecyclerViewAdapter<Article, ArticleAdapter.ViewHolder>() {


    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ArticleItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(data[position], position)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {


        private val titleTextView = bindTextView(R.id.item_article_title)
        private val authorTextView = bindTextView(R.id.item_article_author)

        fun bind(articleItem: Article, position: Int) {
            authorTextView.text = articleItem.author
            titleTextView.text = articleItem.title
        }
    }
}