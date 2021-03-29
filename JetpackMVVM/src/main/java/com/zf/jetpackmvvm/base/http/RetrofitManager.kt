package com.zf.jetpackmvvm.base.http

import com.zf.jetpackmvvm.base.http.ok.OkHttpManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitManager private constructor() {

    companion object {
        val INSTANCE: RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitManager() }
    }

    fun retrofitBuilder(client: OkHttpClient) =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)

    fun defaultRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(OkHttpManager.INSTANCE.defaultOkHttpClient().build())
}