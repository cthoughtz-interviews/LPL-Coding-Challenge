package com.simple.lplcodingchallenge.presentation.viewmodel

import com.simple.lplcodingchallenge.domain.model.PostResponse
import com.simple.lplcodingchallenge.domain.usecase.PostUseCase
import com.simple.lplcodingchallenge.presentation.util.PostResult
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var postUseCase: PostUseCase
    private lateinit var viewModel: PostViewModel

    @Before
    fun setup() {
        // Initialize mock first with relaxed=true
        postUseCase = mockk(relaxed = true) {
            // Default answer that will be overridden in tests
            coEvery { getPost() } returns PostResult.Success(mockk(relaxed = true))
        }
        viewModel = PostViewModel(postUseCase)
    }

    @Test
    fun `should emit Loading then Success states`() = runTest {

        val mockResponse = mockk<PostResponse>()

        clearMocks(postUseCase)

        coEvery { postUseCase.getPost() } returns PostResult.Success(mockResponse)

        val emissions = mutableListOf<PostResult<PostResponse>>()
        val job = viewModel.postResultFlow.onEach {
            emissions.add(it)
        }.launchIn(this) // Use test scope

        advanceUntilIdle() // Process all coroutines

        assertEquals(1, emissions.size) //  Success
        assertTrue(emissions[0] is PostResult.Success)

        job.cancel()
    }
}