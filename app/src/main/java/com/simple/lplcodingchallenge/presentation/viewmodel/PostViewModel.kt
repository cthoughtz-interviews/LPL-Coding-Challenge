package com.simple.lplcodingchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.lplcodingchallenge.domain.model.PostResponse
import com.simple.lplcodingchallenge.domain.usecase.PostUseCase
import com.simple.lplcodingchallenge.presentation.util.PostResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postUseCase: PostUseCase) : ViewModel() {

    private val _postResultFlow = MutableStateFlow<PostResult<PostResponse>>(PostResult.None)
    val postResultFlow = _postResultFlow.asStateFlow()

    init {
        getPost()
    }

    private fun getPost() {
        _postResultFlow.update { PostResult.Loading }
        viewModelScope.launch {
            val result = postUseCase.getPost()
            _postResultFlow.update { result }
        }
    }
}