package com.zf.jetpackmvvm.widget.easy_recycler_view

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    protected fun <T : View> bindView(@IdRes viewId: Int): T = itemView.findViewById(viewId)
    protected fun bindTextView(@IdRes viewId: Int): TextView = itemView.findViewById(viewId)
}