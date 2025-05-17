package com.simple.lplcodingchallenge.presentation.views.pages

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.simple.lplcodingchallenge.R
import com.simple.lplcodingchallenge.domain.model.PostResponse
import com.simple.lplcodingchallenge.presentation.util.PostResult
import com.simple.lplcodingchallenge.presentation.viewmodel.ImageViewModel
import com.simple.lplcodingchallenge.presentation.viewmodel.PostViewModel


private const val TAG = "MainPage"

@Composable
fun MainPage(postViewModel: PostViewModel, imageViewModel: ImageViewModel = viewModel()) {

    val postResult = postViewModel.postResultFlow.collectAsStateWithLifecycle()

    var updateState by remember { mutableStateOf(PostResponse()) }

    GetData(postResult, onShowData = {
        updateState = it
    })

    ShowDataOnScreen(updateState, imageViewModel)
}

@Composable
fun ShowDataOnScreen(updateState: PostResponse, imageViewModel: ImageViewModel) {

    val context = LocalContext.current
    val imageUris by imageViewModel.imageUris.collectAsState()

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageViewModel.updateImage(uri) }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickImageLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Prototype Calls",
            fontSize = 10.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 50.dp, start = 20.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            itemsIndexed(updateState) { index, post ->
                Column {
                    Divider(
                        color = Color.LightGray,
                        thickness = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 3.dp)
                    )
                    Row {

                        Image(
                            painter =
                                imageUris[index]?.let { uri ->
                                    rememberAsyncImagePainter(uri)
                                } ?: painterResource(R.drawable.person_profile_placeholder),
                            contentDescription = "person placeholder",
                            modifier = Modifier
                                .size(50.dp)
                                .weight(1f)
                                .clickable {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                                    } else {
                                        pickImageLauncher.launch("image/*")
                                    }

                                    imageViewModel.selectItem(index)
                                },
                        )

                        Column(
                            modifier = Modifier.weight(2f)
                        ) {
                            Text(
                                text = "${post.name}",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = "${post.id}",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = "${post.body}",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }

                        Text(
                            "${post.email}",
                            fontSize = 10.sp,
                            modifier = Modifier
                                .padding(2.dp)
                                .weight(2f)
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun GetData(postResult: State<PostResult<PostResponse>>, onShowData: (PostResponse) -> Unit) {
    when (val postData = postResult.value) {

        is PostResult.Failure -> Log.d(TAG, "GetData: ${postData.errorMessage}")
        is PostResult.Success -> {
            Log.d(TAG, "GetData: ${postData.data}")
            onShowData(postData.data)
        }

        else -> Log.d(TAG, "GetData: Something Went Wrong")
    }
}