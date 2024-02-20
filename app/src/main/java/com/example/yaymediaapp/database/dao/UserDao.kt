package com.example.yaymediaapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yaymediaapp.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(users: List<User>)

    @Query("SELECT * FROM User WHERE id = :userId")
    suspend fun getUser(userId: String): User

}