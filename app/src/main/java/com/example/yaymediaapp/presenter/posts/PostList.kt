package com.example.yaymediaapp.presenter.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yaymediaapp.data.repository.PostRepositoryImpl
import com.example.yaymediaapp.database.AppDatabase
import com.example.yaymediaapp.domain.usecases.AddPostUseCase
import com.example.yaymediaapp.domain.usecases.GetAllPostsUseCase
import com.example.yaymediaapp.navigation.Screen

@Composable
fun PostList(viewModel: PostsViewModel = hiltViewModel(), navController: NavController? = null) {
    val state by viewModel.state.collectAsState()
    //val listState = rememberLazyListState()


    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Button(
            onClick = { navController?.navigate(Screen.CretePost.route) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()

        ) {
            Text("Add Post + ")
        }

        //Scaffold() { innerPadding ->
        LazyColumn(modifier = Modifier
            .padding(10.dp)
            .weight(1f)) {

            items(
                items = state.posts,
                key = { it.post.id }
            ) { post ->
                PostCard(
                    postWithData = post,
                    currentUserId = viewModel.user?.id,
                    onLikeOrDislike = { postId -> viewModel.likeOrDislikePost(postId = postId) },
                )
            }


        }
    }
}

@Preview
@Composable
fun previewScreen() {
    val appDatabase = AppDatabase.init(LocalContext.current)
    val param1 =
        GetAllPostsUseCase(PostRepositoryImpl(appDatabase.postDao(), appDatabase.likeDao()))
    val param2 = AddPostUseCase(PostRepositoryImpl(appDatabase.postDao(), appDatabase.likeDao()))
    //PostList(PostsViewModel(param1, param2, appDatabase.userDao()))
}