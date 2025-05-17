package com.simple.lplcodingchallenge.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class ImageViewModelTest {

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: ImageViewModel
    private lateinit var imageUriStringsFlow: MutableStateFlow<Map<Int, String?>>

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Uri::class)

        imageUriStringsFlow = MutableStateFlow(emptyMap())
        savedStateHandle = mockk {
            every {
                getStateFlow<Map<Int, String?>>(
                    "imageUris",
                    any()
                )
            } returns imageUriStringsFlow
            every { set("imageUris", any<Map<Int, String?>>()) } answers { call ->
                imageUriStringsFlow.value = call.invocation.args[1] as Map<Int, String?>
            }
        }
        viewModel = ImageViewModel(savedStateHandle)
    }

    @After
    fun tearDown() {
        unmockkStatic(Uri::class)
        Dispatchers.resetMain()
    }

    @Test
    fun `imageUris should be empty initially`() = runBlocking {
        val initialImageUris = viewModel.imageUris.first()
        assertEquals(emptyMap<Int, Uri>(), initialImageUris)
    }

    @Test
    fun `selectItem should update selectedIndex`() {
        viewModel.selectItem(5)
        assertEquals(5, viewModel.selectedIndex)
    }

    @Test
    fun `updateImage with null Uri should update imageUris with null and save to SavedStateHandle`() =
        runTest {
            viewModel.selectItem(1)
            viewModel.updateImage(null)

            val updatedImageUris = viewModel.imageUris.first()
            assertEquals(1, updatedImageUris.size)
            assertEquals(null, updatedImageUris[1])

            val expectedSavedMap = mapOf(1 to null)
            verify { savedStateHandle.set("imageUris", expectedSavedMap) }
            assertEquals(expectedSavedMap, imageUriStringsFlow.value)
        }

}