package com.example.yaymediaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yaymediaapp.data.model.Like
import com.example.yaymediaapp.data.model.Post
import com.example.yaymediaapp.data.model.User
import com.example.yaymediaapp.database.dao.LikeDao
import com.example.yaymediaapp.database.dao.PostDao
import com.example.yaymediaapp.database.dao.UserDao



@Database(
    entities = [Post::class, User::class, Like::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun likeDao(): LikeDao

    companion object {

        /**
         * Initialize [AppDatabase]
         * @param context the applicationContext
         * @param dbName the name of the database
         */
        fun init(context: Context, dbName: String = "YayApp.db"): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
    }

}
