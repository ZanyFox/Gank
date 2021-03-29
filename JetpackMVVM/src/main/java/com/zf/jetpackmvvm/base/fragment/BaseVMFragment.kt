package com.zf.jetpackmvvm.base.fragment

import androidx.viewbinding.ViewBinding
import com.zf.jetpackmvvm.base.vm.BaseViewModel

abstract class BaseVMFragment<VB : ViewBinding, VM : BaseViewModel> : BaseVBFragment<VB>() {


    protected val mViewModel by lazy { viewModel() }


    abstract fun viewModel(): VM
    abstract fun bindView()



    override fun initializeView() {
        super.initializeView()
    }

    override fun initialize() {
        bindView()
    }

}