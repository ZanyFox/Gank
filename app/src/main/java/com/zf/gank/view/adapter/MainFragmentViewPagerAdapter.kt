package com.zf.gank.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainFragmentViewPagerAdapter(
    private val fragment: Fragment,
    private val fragments: Map<Int, Fragment>
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        if (fragments.containsKey(position))
            return fragments[position]!!
        else throw Exception("没有对应的Fragment")
    }



}