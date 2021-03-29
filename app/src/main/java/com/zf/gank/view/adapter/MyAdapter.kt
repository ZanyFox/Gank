package com.zf.gank.view.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zf.gank.R
import com.zf.jetpackmvvm.widget.easy_recycler_view.EasyRecyclerViewAdapter

class MyAdapter : EasyRecyclerViewAdapter<String, MyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv = itemView.findViewById<TextView>(R.id.item_tv)
        fun bind(s: String, pos: Int) {
            tv.text = s
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener {
            Log.d("TAG", "这是第${data[holder.absoluteAdapterPosition]}条数据")
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}