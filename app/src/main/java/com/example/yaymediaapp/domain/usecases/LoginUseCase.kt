package com.example.yaymediaapp.domain.usecases

import com.example.yaymediaapp.data.model.LoginResult
import com.example.yaymediaapp.data.model.LoginUser
import com.example.yaymediaapp.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun login(user: LoginUser,result : (LoginResult) -> Unit) {
        loginRepository.login(user,result)
    }
}