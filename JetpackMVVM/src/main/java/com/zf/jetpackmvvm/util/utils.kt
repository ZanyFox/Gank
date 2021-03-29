package com.zf.jetpackmvvm.util

import android.os.Environment
import com.google.gson.Gson

inline fun <reified T> fromJson(json: String):T {

    val gson = Gson()
    return gson.fromJson(json, T::class.java)
}

fun externalStorageMounted() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED