package com.example.yaymediaapp.data.repository

import com.example.yaymediaapp.data.model.Like
import com.example.yaymediaapp.data.model.LikeWithUser
import com.example.yaymediaapp.data.model.Post
import com.example.yaymediaapp.data.model.PostWithData
import com.example.yaymediaapp.data.model.User
import com.example.yaymediaapp.database.dao.LikeDao
import com.example.yaymediaapp.database.dao.PostDao
import com.example.yaymediaapp.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

class PostRepositoryImpl(
    private val postDao: PostDao,
    private val likeDao: LikeDao,
    private val appDispatcher: CoroutineDispatcher = Dispatchers.Default
) : PostRepository {
    override suspend fun getAllPosts(): Flow<List<PostWithData>> = flow {
        // observe the database, the unique source of true
        emitAll(postDao.loadAll())
    }.flowOn(appDispatcher)



    override suspend fun addPost(post: Post) {
        postDao.insert(post)
    }

    override suspend fun likeOrDislikePost(currentUser: User, postId: String) {
        @Suppress("MoveVariableDeclarationIntoWhen")
        val likeFromDb = likeDao.getLike(userId = currentUser.id, postId = postId)

        when (likeFromDb) {
            null -> {
                val likeWithUser = LikeWithUser(
                    like = Like(
                        id = "$postId-${currentUser.id}",
                        date = Date(),
                        userId = currentUser.id,
                        postId = postId
                    ),
                    user = currentUser
                )
                likeDao.insert(user = currentUser, like = likeWithUser.like)
            }

            else -> {
                // Remove from local DB if exists
                likeDao.remove(likeFromDb)
            }

        }
    }
}