package com.example.yaymediaapp.database.dao

import androidx.room.*
import com.example.yaymediaapp.data.model.Like
import com.example.yaymediaapp.data.model.User

@Dao
interface LikeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User, like: Like)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(likes: List<Like>)

    @Query("SELECT * FROM `Like` WHERE postId = :postId AND userId = :userId")
    suspend fun getLike(userId: String, postId: String): Like?

    @Delete
    suspend fun remove(like: Like)

}
