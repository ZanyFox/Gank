package com.zf.gank.util

open class SingletonHolder<out T, in A>(private val creator: (A) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T = instance ?: synchronized(this) {
        instance ?: creator(arg).also {
            instance = it
        }

    }
}

