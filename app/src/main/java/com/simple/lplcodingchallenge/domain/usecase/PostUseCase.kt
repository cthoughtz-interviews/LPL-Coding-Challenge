package com.simple.lplcodingchallenge.domain.usecase

import com.simple.lplcodingchallenge.domain.repository.PostRepository
import javax.inject.Inject

class PostUseCase @Inject constructor(private val postRepository: PostRepository) {

    suspend fun getPost() = postRepository.getPost()
}