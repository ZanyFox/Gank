package com.zf.gank.http

import com.google.gson.annotations.SerializedName

/**
 * 响应数据基类
 */

data class ApiResponse<T>(
    @SerializedName("errorCode") private val code: Int,
    @SerializedName("errorMsg") private val msg: String,
    @SerializedName("data") private val data: T
) : BaseResponse<T>() {
    override fun isSuccess(): Boolean = code == 0

    override fun responseData(): T = data

    override fun errorMsg(): String = msg

    override fun errorCode(): Int = code
}