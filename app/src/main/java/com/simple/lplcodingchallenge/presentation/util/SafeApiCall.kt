package com.simple.lplcodingchallenge.presentation.util

import com.simple.lplcodingchallenge.data.networking.NetworkResult

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        apiCall().let {
            NetworkResult.Success(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        NetworkResult.Failure(e.message ?: "Something Went Wrong")
    }
}