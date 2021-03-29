package com.zf.jetpackmvvm.base.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.zf.jetpackmvvm.base.fragment.BaseFragment

abstract class LazyFragment(open val isLazy: Boolean = true) : BaseFragment() {


    // 是否已经加载数据
    private var isLoaded: Boolean = false



    override fun onResume() {
        super.onResume()
        // 如果不是懒加载或者可见  去加载数据
        if ((!isLazy || !isHidden) && !isLoaded) {
            preloadData()
            isLoaded = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            initializeView()
            initialize()

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        /*
        当此Fragment没有被隐藏并且此Fragment的状态是resume并且数据没有被初始化加载并且是懒加载时  初始化加载数据
         */
        if (isLazy && !hidden && lifecycle.currentState == Lifecycle.State.RESUMED && !isLoaded) {
            preloadData()
            isLoaded = true
        }
    }

    /**
     * 初始化View
     */
    open fun initializeView() {}

    /**
     * 初始化
     */
    open fun initialize() {

    }

    /**
     * 加载数据
     */
    open fun preloadData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }


}

