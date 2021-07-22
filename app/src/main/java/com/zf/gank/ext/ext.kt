package com.zf.gank.ext


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zf.gank.R
import com.zf.gank.http.ApiResponse
import com.zf.gank.state.ListViewState
import com.zf.gank.state.Result
import com.zf.gank.state.ViewState

import com.zf.jetpackmvvm.base.appContext
import com.zf.jetpackmvvm.base.vm.BaseViewModel
import com.zf.jetpackmvvm.widget.easyrecyclerview.EasyRecyclerView
import com.zf.jetpackmvvm.widget.easyrecyclerview.EasyRecyclerViewAdapter
import io.reactivex.rxjava3.annotations.NonNull
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.net.UnknownHostException


fun <T> BaseViewModel.request2(
    block: suspend () -> Flow<ApiResponse<T>>,
    success: (t: T?) -> Unit,
    failure: (t: Throwable) -> Unit
): Job {
    return viewModelScope.launch {
        runCatching {
            block.invoke()
        }.onSuccess {

        }.onFailure {
            failure.invoke(it)
        }
    }
}

/**
 * 请求扩展函数  消除重复代码
 */
fun <T> BaseViewModel.request(
    block: suspend () -> ApiResponse<T>,
    success: (t: T?) -> Unit,
    failure: (t: Throwable) -> Unit
): Job {
    return viewModelScope.launch {
        runCatching {
            block.invoke()
        }.onSuccess {
            when (val result = fromApiResponse(it)) {
                is Result.Success -> {
                    success.invoke(result.data)
                }
                is Result.Failure -> {
                    failure.invoke(result.code.errorCodeToThrowable())
                }
            }
        }.onFailure {
            failure.invoke(it)
        }
    }
}

/**
 * LiveData 观察函数 将LiveData作为参数
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, @NonNull observer: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { observer(it) } })
}

fun throwableToString(t: Throwable): String {

    return when (t) {
        is UnknownHostException -> appContext.resources.getString(R.string.no_network_dialog_msg)
        else -> ""
    }

}

/**
 * RecyclerView 初始化扩展函数
 */
fun RecyclerView.init(
    mLayoutManager: RecyclerView.LayoutManager,
    mAdapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = mLayoutManager
    adapter = mAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

/**
 * 渲染数据函数 消除重复代码
 */
fun <T> renderList(
    data: ListViewState<T>,
    hide: () -> Unit,
    recycler: EasyRecyclerView,
    adapter: EasyRecyclerViewAdapter<T, *>,
    swipeRefreshLayout: SwipeRefreshLayout
) {
   swipeRefreshLayout.isRefreshing = false
    when (data) {
        is ListViewState.LoadFail -> {
            // showFailure(data.msg)
        }
        is ListViewState.LoadSuccess -> {
            if (data.isEmpty) {
                // showEmpty()
            } else {
                when (data.action) {
                    ListViewState.Action.REFRESH -> {
                        adapter.setList(data.result)
                    }
                    ListViewState.Action.LOAD_MORE -> {
                        adapter.addData(data.result)
                    }
                    ListViewState.Action.INITIALIZE -> {
                        adapter.setList(data.result)
                    }
                }
            }
        }
        is ListViewState.Loading -> {

            when (data.action) {
                ListViewState.Action.INITIALIZE -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                ListViewState.Action.LOAD_MORE -> {

                }
                ListViewState.Action.REFRESH -> {
                    swipeRefreshLayout.isRefreshing = true
                }
            }

        }
    }
}


fun <T> renderState(
    state: ViewState<T>,
    success: ((t: T?) -> Unit)? = null,
    failure: ((m: String) -> Unit)? = null,
    loading: (() -> Unit)? = null
) {

    when (state) {
        is ViewState.Success -> {
            success?.invoke(state.data)
        }
        is ViewState.Failure -> {
            failure?.invoke(state.msg)
        }
        is ViewState.Loading -> {
            loading?.invoke()
        }
    }
}