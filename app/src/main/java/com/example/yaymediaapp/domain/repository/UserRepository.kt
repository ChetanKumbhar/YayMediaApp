package com.example.yaymediaapp.domain.repository

import com.example.yaymediaapp.data.model.User

interface UserRepository {
    suspend fun createAccount(user: User)
    suspend fun getUser(userID : String): User
}