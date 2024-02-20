package com.example.yaymediaapp.presenter.posts

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yaymediaapp.FileUtil
import com.example.yaymediaapp.data.model.Post
import com.example.yaymediaapp.data.model.PostWithData
import com.example.yaymediaapp.data.model.User
import com.example.yaymediaapp.database.dao.UserDao
import com.example.yaymediaapp.domain.usecases.AddPostUseCase
import com.example.yaymediaapp.domain.usecases.GetAllPostsUseCase
import com.example.yaymediaapp.presenter.profile.EMPTY_IMAGE_URI
import com.example.yaymediaapp.presenter.profile.PROFILE_USER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val userDao: UserDao,
    @ApplicationContext val appContext: Context
) : ViewModel() {





    var postId: Int = 0
    private val _state = MutableStateFlow(PostsState())
    val state: StateFlow<PostsState>
        get() = _state


    private val _addPostState = MutableStateFlow(AddPostViewState())
    val addPostState: StateFlow<AddPostViewState>
        get() = _addPostState

    var user: User? = null


    private val _description = MutableStateFlow("")
    private val _imageUri = MutableStateFlow(EMPTY_IMAGE_URI)

    fun setDescription(username: String) {
        _description.value = username
    }

    fun setImageUri(imageUri: Uri) {
        _imageUri.value = imageUri
    }

    init {
        viewModelScope.launch {
            user = userDao.getUser(PROFILE_USER_ID)
            getAllPostsUseCase.getAllPosts()
                .catch { throwable ->
                    // TODO: emit a UI error here. For now we'll just rethrow
                    throw throwable
                }
                .collect {
                    postId = it.size
                    _state.value = PostsState(it)
                }
        }

        viewModelScope.launch {
            combine(_description, _imageUri) { description, imageUri ->
                AddPostViewState(
                    description = description,
                    imageUri = imageUri,
                )
            }.collect { _addPostState.value = it }
        }
    }

    fun likeOrDislikePost(postId: String) {
        viewModelScope.launch {
            user?.let { user ->
                getAllPostsUseCase.likeOrDislikePost(currentUser = user, postId = postId)
            }
        }
    }


    fun createPost(result: (Boolean) -> Unit) {
        if (!_addPostState.value.enableCreateButton) {
            Log.e("chetan_logs", "The user info are not valid: ${_state.value}")
            return
        }

        //addPostState.value.imageUri.path

        viewModelScope.launch {
            user?.let {
                val imgpath = FileUtil.getPath(context = appContext,addPostState.value.imageUri) ?: ""
                val post = Post("${++postId}",imgpath, addPostState.value.description, Date(), it.id)
                addPostUseCase.addPost(post)
                result(true)
            }.runCatching {
                result(false)
            }
        }
    }



}


data class PostsState(
    val posts: List<PostWithData> = emptyList()
)

data class AddPostViewState(
    val description: String = "",
    val imageUri: Uri = EMPTY_IMAGE_URI
) {
    val enableCreateButton: Boolean =
        description.isNotBlank() && imageUri != EMPTY_IMAGE_URI
}



