package com.example.yaymediaapp.database.dao

import androidx.room.*
import com.example.yaymediaapp.data.model.Post
import com.example.yaymediaapp.data.model.PostWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(posts: List<Post>)

    @Transaction
    @Query("SELECT * FROM Post ORDER BY date DESC")
    fun loadAll(): Flow<List<PostWithData>>

}
