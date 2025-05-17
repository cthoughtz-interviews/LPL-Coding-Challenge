package com.simple.lplcodingchallenge.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class ImageViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _imageUriStrings: StateFlow<Map<Int, String?>> =
        savedStateHandle.getStateFlow("imageUris", emptyMap<Int, String>())

    val imageUris: StateFlow<Map<Int, Uri?>> = _imageUriStrings.map { uriMap ->
        uriMap.mapNotNull { (key, value) ->
            key to value?.let { Uri.parse(it) }
        }.toMap()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyMap()
    )

    var selectedIndex by mutableStateOf(-1)
        private set

    fun selectItem(index: Int) {
        selectedIndex = index
    }

    fun updateImage(uri: Uri?) {
        val updatedMap = _imageUriStrings.value.toMutableMap().apply {
            this[selectedIndex] = uri?.toString()
        }
        savedStateHandle["imageUris"] = updatedMap
    }
}