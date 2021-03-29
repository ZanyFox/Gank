package com.zf.gank.di

import com.zf.gank.api.WanAndroidService
import com.zf.jetpackmvvm.base.http.RetrofitManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

/**
 * 为SingletonComponent添加Module
 * 作用域为Singleton 在全局范围内单例
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://www.wanandroid.com"


    @Singleton
    @Provides
    fun apiService(): WanAndroidService = RetrofitManager
        .INSTANCE
        .defaultRetrofitBuilder()
        .baseUrl(BASE_URL)
        .build()
        .create(WanAndroidService::class.java)


}