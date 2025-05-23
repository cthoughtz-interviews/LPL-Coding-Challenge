package com.simple.lplcodingchallenge.data.networking

import com.simple.lplcodingchallenge.domain.model.PostResponse
import retrofit2.http.GET

interface ApiService {

    @GET("posts/1/comments")
    suspend fun getPost(): PostResponse
}