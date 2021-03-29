package com.zf.gank.vm

import androidx.lifecycle.MutableLiveData
import com.zf.gank.ext.request
import com.zf.gank.ext.throwableToString
import com.zf.gank.pojo.Article
import com.zf.gank.pojo.Banner
import com.zf.gank.repository.RecommendRepository
import com.zf.gank.state.*
import com.zf.jetpackmvvm.base.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainPageViewModel @Inject constructor(private val repository: RecommendRepository) :
    BaseViewModel() {

    private var currentPageNumber: Int = 0

    val bannerLiveData = MutableLiveData<ViewState<Banner>>()
    val homeArticleLiveData = MutableLiveData<ListViewState<Article>>()




    fun getHomeArticle(action: ListViewState.Action) {

        homeArticleLiveData.value = ListViewState.Loading(action)
        currentPageNumber = when (action) {
            ListViewState.Action.LOAD_MORE -> +1
            ListViewState.Action.REFRESH -> 0
            ListViewState.Action.INITIALIZE -> 0
        }

        request({ repository.getRecommendArticleList(currentPageNumber) },
            {
                homeArticleLiveData.value =
                    ListViewState.LoadSuccess(
                        it?.datas,
                        isEmpty = it?.datas.isNullOrEmpty(),
                        action
                    )
            },
            { homeArticleLiveData.value = ListViewState.LoadFail(throwableToString(it), action) })
    }


}