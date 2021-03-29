package com.zf.gank.ext

import com.zf.gank.http.ApiResponse
import com.zf.gank.state.Result

import okhttp3.ResponseBody
import retrofit2.Response

fun <T> fromResponse(response: Response<ResponseBody>): Result<T> {

    TODO("返回值为Response<ResponseBody>时的处理")
}

fun <T> fromApiResponse(response: ApiResponse<T>): Result<T> {

    return if (response.errorCode() == 0)
        Result.success(response.responseData())
    else {
        Result.failure(
            msg = response.errorMsg(),
            code = response.errorCode(),
            throwable = response.errorCode().errorCodeToThrowable()
        )
    }
}

fun <T> fromThrowable(t: Throwable): Result.Failure {
    return Result.Failure(msg = "")
}

fun errorCodeToString(code: Int): String =
    when (code) {
        10001 -> "出错了呦"
        else -> "出错了呦"
    }


fun Int.errorCodeToThrowable(): Throwable {
    return when (this) {
        10001 -> UnknownError("")
        else -> UnknownError("")
    }
}