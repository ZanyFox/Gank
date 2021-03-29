package com.zf.gank.repository

import com.zf.gank.api.WanAndroidService
import com.zf.gank.http.ApiResponse
import com.zf.gank.pojo.ArticleList
import com.zf.gank.pojo.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RecommendRepository constructor(private val apiService: WanAndroidService) {

    suspend fun getRecommendArticleList(pageNumber: Int): ApiResponse<ArticleList> =
        withContext(Dispatchers.IO) { apiService.getArticle(pageNumber) }

    suspend fun getBanner(): ApiResponse<List<Banner>> =
        withContext(Dispatchers.IO) { apiService.getBanner() }

    suspend fun getArticleList(): Flow<ApiResponse<ArticleList>> {

        return flow { apiService.getArticle(1) }
    }

}