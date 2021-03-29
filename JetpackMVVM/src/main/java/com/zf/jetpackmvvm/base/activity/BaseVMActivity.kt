package com.zf.jetpackmvvm.base.activity

import androidx.viewbinding.ViewBinding
import com.zf.jetpackmvvm.base.vm.BaseViewModel

abstract class BaseVMActivity<VB : ViewBinding, VM : BaseViewModel> : BaseVBActivity<VB>() {


    protected val mViewModel: VM by lazy { viewModel() }

    abstract fun viewModel(): VM
    abstract fun bindView()


    override fun preInit() {
        super.preInit()
        lifecycle.addObserver(mViewModel)
        bindView()
    }
}