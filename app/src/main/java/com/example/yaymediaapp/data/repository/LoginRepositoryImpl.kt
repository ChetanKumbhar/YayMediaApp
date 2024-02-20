package com.example.yaymediaapp.data.repository

import com.example.yaymediaapp.data.model.LoginResult
import com.example.yaymediaapp.data.model.LoginUser
import com.example.yaymediaapp.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth

class LoginRepositoryImpl : LoginRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance() // Move this to hilt module
    override suspend fun login(user: LoginUser, result: (LoginResult) -> Unit) {
        mAuth.signInWithEmailAndPassword(user.userEmail, user.userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result(LoginResult(true))
                } else {
                    result(LoginResult(false, it.exception.toString()))
                }
            }
    }

    override suspend fun register(user: LoginUser, result: (LoginResult) -> Unit) {
        mAuth.createUserWithEmailAndPassword(user.userEmail, user.userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result(LoginResult(true))
                } else {
                    result(LoginResult(false, it.exception.toString()))
                }
            }
    }
}