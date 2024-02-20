package com.example.yaymediaapp.domain.repository

import com.example.yaymediaapp.data.model.LoginResult
import com.example.yaymediaapp.data.model.LoginUser

interface LoginRepository {
    suspend fun login(user: LoginUser, result : (LoginResult) -> Unit)
    suspend fun register(user: LoginUser, result : (LoginResult) -> Unit)
}