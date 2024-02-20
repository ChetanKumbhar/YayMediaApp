package com.example.yaymediaapp.domain.usecases

import com.example.yaymediaapp.data.model.User
import com.example.yaymediaapp.domain.repository.UserRepository
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun createProfileAccount(user: User) {
        userRepository.createAccount(user)
    }
    suspend fun getUser(userId: String):User {
        return userRepository.getUser(userId)
    }
}