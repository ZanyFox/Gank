package com.zf.jetpackmvvm.base.activity

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {


    open fun initializeView() {

    }

    open fun initialize() {}
}