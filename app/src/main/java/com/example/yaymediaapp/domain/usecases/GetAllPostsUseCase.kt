package com.example.yaymediaapp.domain.usecases

import com.example.yaymediaapp.data.model.PostWithData
import com.example.yaymediaapp.data.model.User
import com.example.yaymediaapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend fun getAllPosts(): Flow<List<PostWithData>> {
        return postRepository.getAllPosts()
    }

    suspend fun likeOrDislikePost(currentUser : User, postId : String){
        postRepository.likeOrDislikePost(currentUser,postId)
    }
}