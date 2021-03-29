package com.zf.jetpackmvvm.base.http.ok

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zf.jetpackmvvm.base.appContext
import com.zf.jetpackmvvm.util.externalStorageMounted
import okhttp3.Cache
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

class OkHttpManager private constructor() {

    private val defaultCacheDirPath = appContext.cacheDir.absolutePath + File.separator + "okCache"
    private val defaultCacheSize: Long = 10 * 1024 * (1024L)

    companion object {
        val INSTANCE: OkHttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { OkHttpManager() }
    }

    fun defaultOkHttpClient() =

        okHttpBuilder()
            .addInterceptor(httpLoggingInterceptor())
//            .addInterceptor(CacheInterceptor(2))
            .cache(cache())
            .cookieJar(cookieJar())


    fun okHttpBuilder() = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)


    private fun httpLoggingInterceptor() =
        HttpLoggingInterceptor(OkHttpLogger()).apply { level = HttpLoggingInterceptor.Level.BODY }

    private fun cache(): Cache {
        val cacheDirPath =
            if (externalStorageMounted()) appContext.externalCacheDir!!.absolutePath + File.separator + "okCache" else defaultCacheDirPath
        val cacheFile = File(cacheDirPath)
        if (!cacheFile.exists()) {
            cacheFile.mkdir()
        }

        return Cache(cacheFile, defaultCacheSize)
    }

    // 使用PersistentCookieJar实现Cookie持久化
    fun cookieJar(): CookieJar =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
}