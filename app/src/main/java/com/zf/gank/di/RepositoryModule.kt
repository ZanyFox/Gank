package com.zf.gank.di

import com.zf.gank.api.WanAndroidService
import com.zf.gank.repository.RecommendRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRecommendRepository(apiService: WanAndroidService): RecommendRepository {
        return RecommendRepository(apiService)
    }


}