package com.zf.gank.http

abstract class BaseResponse<T> {
    abstract fun isSuccess(): Boolean
    abstract fun responseData(): T
    abstract fun errorMsg(): String
    abstract fun errorCode(): Int
}