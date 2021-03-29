package com.zf.jetpackmvvm.widget.easy_recycler_view

import androidx.recyclerview.widget.RecyclerView

abstract class EasyRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder>(
    protected open val data: MutableList<T> = arrayListOf()
) :
    RecyclerView.Adapter<VH>() {


    fun setList(list: Collection<T>?) {
        if (list !== data) {
            data.clear()
            if (!list.isNullOrEmpty()) {
                data.addAll(list)
            }
        } else {
            if (!list.isNullOrEmpty()) {
                data.clear()
                data.addAll(ArrayList(list))
            } else data.clear()
        }


        notifyDataSetChanged()
    }

    fun addData(list: Collection<T>?) {
        if (!list.isNullOrEmpty()) {
            data.addAll(list)
            notifyDataSetChanged()
        }
    }
}