package com.example.yaymediaapp.di

import com.example.yaymediaapp.data.repository.LoginRepositoryImpl
import com.example.yaymediaapp.data.repository.PostRepositoryImpl
import com.example.yaymediaapp.data.repository.UserRepositoryImpl
import com.example.yaymediaapp.database.dao.LikeDao
import com.example.yaymediaapp.database.dao.PostDao
import com.example.yaymediaapp.database.dao.UserDao
import com.example.yaymediaapp.domain.repository.LoginRepository
import com.example.yaymediaapp.domain.repository.PostRepository
import com.example.yaymediaapp.domain.repository.UserRepository
import com.example.yaymediaapp.domain.usecases.AddPostUseCase
import com.example.yaymediaapp.domain.usecases.CreateAccountUseCase
import com.example.yaymediaapp.domain.usecases.GetAllPostsUseCase
import com.example.yaymediaapp.domain.usecases.LoginUseCase
import com.example.yaymediaapp.domain.usecases.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoginRepository(): LoginRepository = LoginRepositoryImpl()


    @Provides
    @Singleton
    fun provideLoginUseCases( repository: LoginRepository): LoginUseCase = LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideRegisterUseCases( repository: LoginRepository): RegisterUseCase = RegisterUseCase(repository)


    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepositoryImpl(userDao)

    @Provides
    @Singleton
    fun provideCreateAccountUseCase(repository: UserRepository): CreateAccountUseCase = CreateAccountUseCase(repository)

    @Provides
    @Singleton
    fun providePostRepository(postDao: PostDao, likeDao: LikeDao,): PostRepository = PostRepositoryImpl(postDao, likeDao)

    @Provides
    @Singleton
    fun provideGetAllPostsUseCase(repository: PostRepository): GetAllPostsUseCase = GetAllPostsUseCase(repository)

    @Provides
    @Singleton
    fun provideAddPostUseCase(repository: PostRepository): AddPostUseCase = AddPostUseCase(repository)
}