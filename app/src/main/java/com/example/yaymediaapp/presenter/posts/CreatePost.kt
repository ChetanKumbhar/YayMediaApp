package com.example.yaymediaapp.presenter.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yaymediaapp.data.repository.PostRepositoryImpl
import com.example.yaymediaapp.database.AppDatabase
import com.example.yaymediaapp.domain.usecases.AddPostUseCase
import com.example.yaymediaapp.domain.usecases.GetAllPostsUseCase
import com.example.yaymediaapp.presenter.profile.CreateAccountTextField
import com.example.yaymediaapp.presenter.profile.ProfileImageChooser
import com.example.yaymediaapp.ui.theme.YayMediaAppTheme


@Composable
fun CreatePost(viewModel: PostsViewModel = hiltViewModel(), navController: NavController){


    val viewState by viewModel.addPostState.collectAsState()


    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = "Select Image", color = Color.Green, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(5.dp))
        ProfileImageChooser(
            imageUri = viewState.imageUri,
            setImageUri = {  viewModel.setImageUri(it)  },
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        CreateAccountTextField(
            text = viewState.description,
            setText = { viewModel.setDescription(it) },
            label = "Description",
            placeholder = "Enter Description",
        )

        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {
                viewModel.createPost{
                    if(it){
                        navController.navigateUp()
                    }
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text("Add Post to Profile")
        }
    }
}

@Preview
@Composable
fun previewMyScreen(){
    YayMediaAppTheme {
        val appDatabase = AppDatabase.init(LocalContext.current)
        val param1 = GetAllPostsUseCase(PostRepositoryImpl(appDatabase.postDao(),appDatabase.likeDao()))
        val param2 = AddPostUseCase(PostRepositoryImpl(appDatabase.postDao(),appDatabase.likeDao()))
       // CreatePost(PostsViewModel(param1,param2,appDatabase.userDao()), navController = NavController(LocalContext.current))
    }

}
