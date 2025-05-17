package com.simple.lplcodingchallenge.presentation.util

sealed class PostResult<out T> {

    data object Loading : PostResult<Nothing>()
    data class Success<T>(val data: T) : PostResult<T>()
    data class Failure<T>(val errorMessage: String) : PostResult<T>()
    data object None : PostResult<Nothing>()
}