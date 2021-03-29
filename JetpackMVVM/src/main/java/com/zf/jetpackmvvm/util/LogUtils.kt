package com.zf.jetpackmvvm.util

import android.text.TextUtils
import android.util.Log

private const val DEFAULT_TAG = "TAG"

fun debugLog(tag: String? = DEFAULT_TAG, msg: String) {
    if (TextUtils.isEmpty(msg))
        return

    Log.d(tag, msg)
}

fun infoLog(tag: String? = DEFAULT_TAG, msg: String) {
    if (TextUtils.isEmpty(msg))
        return

    Log.i(tag, msg)
}


fun errorLog(tag: String? = DEFAULT_TAG, msg: String) {
    if (TextUtils.isEmpty(msg))
        return

    Log.e(tag, msg)
}

fun warnLog(tag: String? = DEFAULT_TAG, msg: String) {
    if (TextUtils.isEmpty(msg))
        return

    Log.w(tag, msg)
}

