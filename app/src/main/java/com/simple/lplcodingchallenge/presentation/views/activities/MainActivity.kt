package com.simple.lplcodingchallenge.presentation.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.simple.lplcodingchallenge.presentation.viewmodel.PostViewModel
import com.simple.lplcodingchallenge.presentation.views.pages.MainPage
import com.simple.lplcodingchallenge.ui.theme.LPLCodingChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LPLCodingChallengeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val postViewModel = hiltViewModel<PostViewModel>()
                    MainPage(postViewModel)
                }
            }
        }
    }
}

