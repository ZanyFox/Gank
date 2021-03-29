package com.zf.gank.state

@Suppress("UNCHECKED_CAST")
sealed class Result<out T> {

    companion object {

        fun <T> success(data: T?): Result<T> = Success(data)
        fun failure(throwable: Throwable, code: Int, msg: String): Result<Nothing> =
            Failure(throwable, code, msg)
    }

    data class Success<T>(val data: T?) : Result<T>()
    data class Failure(val t: Throwable? = null, val code: Int = -1, val msg: String) :
        Result<Nothing>()
}

