package com.zf.gank.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zf.gank.R
import com.zf.gank.databinding.ArticleItemLayoutBinding
import com.zf.gank.pojo.Article
import com.zf.jetpackmvvm.widget.easy_recycler_view.BaseRecyclerViewHolder
import com.zf.jetpackmvvm.widget.easy_recycler_view.EasyRecyclerViewAdapter


class ArticleAdapter() :
    EasyRecyclerViewAdapter<Article, ArticleAdapter.ViewHolder>() {

    private lateinit var mViewBinding: ArticleItemLayoutBinding


    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mViewBinding =
            ArticleItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mViewBinding.root)
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