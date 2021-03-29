package com.zf.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.zf.jetpackmvvm.base.vm.BaseViewModel


class TestViewModel  (private val handle: SavedStateHandle) : BaseViewModel() {


    val data = MutableLiveData<String>()
}