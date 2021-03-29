package com.zf.gank.state

sealed class ListViewState<out T>(open val action: Action) {

    data class Loading(override val action: Action) : ListViewState<Nothing>(action)

    data class LoadSuccess<T>(
        val result: List<T>? = null,
        val isEmpty: Boolean = false,
        override val action: Action
    ) : ListViewState<T>(action)

    data class LoadFail(
        val msg: String,
        override val action: Action
    ) : ListViewState<Nothing>(action)

    enum class Action {
        LOAD_MORE,
        REFRESH,
        INITIALIZE
    }
}