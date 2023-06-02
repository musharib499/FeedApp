package com.example.feedapp.base.component.baseApi

sealed class BaseApiResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : BaseApiResult<T>(data)
    class Error<T>(message: String, data: T? = null) : BaseApiResult<T>(data, message)
    class Loading<T> : BaseApiResult<T>()
}
