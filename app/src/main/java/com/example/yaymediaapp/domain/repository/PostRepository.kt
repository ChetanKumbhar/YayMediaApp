package com.example.yaymediaapp.domain.repository

import com.example.yaymediaapp.data.model.Post
import com.example.yaymediaapp.data.model.PostWithData
import com.example.yaymediaapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getAllPosts(): Flow<List<PostWithData>>
    suspend fun addPost(post: Post)
    suspend fun likeOrDislikePost(currentUser: User, postId: String)
}