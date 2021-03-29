package com.zf.gank.api

import com.zf.gank.http.ApiResponse
import com.zf.gank.pojo.ArticleList
import com.zf.gank.pojo.Banner
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WanAndroidService {


    @GET("/article/list/{pageNum}/json")
    suspend fun getArticle(@Path("pageNum") pageNum: Int): ApiResponse<ArticleList>

    @GET("/banner/json")
    suspend fun getBanner(): ApiResponse<List<Banner>>

    @GET("/article/list/{pageNum}/json")
    fun getArticleResponse(@Path("pageNum") pageNum: Int): Call<ResponseBody>

}

