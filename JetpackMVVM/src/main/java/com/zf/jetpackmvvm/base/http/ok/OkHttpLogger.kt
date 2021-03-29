package com.zf.jetpackmvvm.base.http.ok

import com.zf.jetpackmvvm.util.debugLog
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpLogger : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        debugLog("OkHttp", message)
    }
}