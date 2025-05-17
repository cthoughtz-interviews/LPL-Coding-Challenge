package com.simple.lplcodingchallenge.domain.repository

import com.simple.lplcodingchallenge.data.networking.ApiService
import com.simple.lplcodingchallenge.data.networking.toResult
import com.simple.lplcodingchallenge.domain.model.PostResponse
import com.simple.lplcodingchallenge.presentation.util.PostResult
import com.simple.lplcodingchallenge.presentation.util.safeApiCall
import javax.inject.Inject

interface PostRepository {
    suspend fun getPost(): PostResult<PostResponse>
}

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {
    override suspend fun getPost(): PostResult<PostResponse> {
        return safeApiCall { apiService.getPost() }.toResult()
    }
}