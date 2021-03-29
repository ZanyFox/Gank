package com.zf.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zf.gank.R

class RecyclerViewAdapter :
    ListAdapter<String, RecyclerViewAdapter.ViewHolder>(diffCallback) {


      class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv = itemView.findViewById<TextView>(R.id.item_tv)
        fun bind(s: String, pos: Int) {
            tv.text = s
        }
    }

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {

                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(currentList[position], position)
    }
}