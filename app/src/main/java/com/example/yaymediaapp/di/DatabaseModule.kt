package com.example.yaymediaapp.di

import android.content.Context
import com.example.yaymediaapp.database.AppDatabase
import com.example.yaymediaapp.database.dao.LikeDao
import com.example.yaymediaapp.database.dao.PostDao
import com.example.yaymediaapp.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.init(appContext)
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun providePostDao(appDatabase: AppDatabase): PostDao {
        return appDatabase.postDao()
    }

    @Provides
    @Singleton
    fun provideLikeDao(appDatabase: AppDatabase): LikeDao {
        return appDatabase.likeDao()
    }
}