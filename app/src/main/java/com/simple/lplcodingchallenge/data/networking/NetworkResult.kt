package com.simple.lplcodingchallenge.data.networking

import com.simple.lplcodingchallenge.presentation.util.PostResult

sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
}

fun <T> NetworkResult<T>.toResult() = when (this) {
    is NetworkResult.Failure -> PostResult.Failure(this.errorMessage)
    is NetworkResult.Success -> PostResult.Success(this.data)
}