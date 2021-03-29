package com.zf.gank.vm

import androidx.lifecycle.MutableLiveData
import com.zf.gank.ext.request
import com.zf.gank.ext.throwableToString
import com.zf.gank.pojo.Article
import com.zf.gank.pojo.Banner
import com.zf.gank.repository.RecommendRepository
import com.zf.gank.state.ListViewState
import com.zf.gank.state.ViewState
import com.zf.jetpackmvvm.base.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RecommendViewModel @Inject constructor(private val repository: RecommendRepository) :
    BaseViewModel() {


    val bannerLiveData = MutableLiveData<ViewState<List<Banner>>>()

    val recommendLiveData = MutableLiveData<ListViewState<Article>>()

    private var currentPageNumber: Int = 0

    fun getRecommendArticle(action: ListViewState.Action) {

        recommendLiveData.value = ListViewState.Loading(action)
        currentPageNumber = when (action) {
            ListViewState.Action.LOAD_MORE -> +1
            ListViewState.Action.REFRESH -> 0
            ListViewState.Action.INITIALIZE -> 0
        }

        request({ repository.getRecommendArticleList(currentPageNumber) },
            {
                recommendLiveData.value =
                    ListViewState.LoadSuccess(
                        it?.datas,
                        isEmpty = it?.datas.isNullOrEmpty(),
                        action
                    )
            },
            { recommendLiveData.value = ListViewState.LoadFail(throwableToString(it), action) })
    }


    fun getBanner() {

        request({ repository.getBanner() }, {
            bannerLiveData.value = ViewState.Success(it)
        }, {
            bannerLiveData.value = ViewState.Failure(throwableToString(it))
        })
    }

}