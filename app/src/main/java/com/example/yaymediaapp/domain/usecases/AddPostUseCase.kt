package com.example.yaymediaapp.domain.usecases

import com.example.yaymediaapp.data.model.Post
import com.example.yaymediaapp.domain.repository.PostRepository
import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend fun addPost(post: Post){
        postRepository.addPost(post)
    }
}