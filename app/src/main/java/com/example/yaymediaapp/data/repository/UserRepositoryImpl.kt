package com.example.yaymediaapp.data.repository

import com.example.yaymediaapp.data.model.User
import com.example.yaymediaapp.database.dao.UserDao
import com.example.yaymediaapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : UserRepository {
    override suspend fun createAccount(user: User) {
        userDao.insert(user)
    }

    override suspend fun getUser(userID : String):User {
       return userDao.getUser(userID)
    }
}
