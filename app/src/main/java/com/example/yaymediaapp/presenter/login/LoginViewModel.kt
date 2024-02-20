package com.example.yaymediaapp.presenter.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yaymediaapp.data.model.LoginResult
import com.example.yaymediaapp.data.model.LoginUser
import com.example.yaymediaapp.domain.usecases.LoginUseCase
import com.example.yaymediaapp.domain.usecases.RegisterUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance() // Move this to hilt module
    var isValid: MutableState<Boolean> = mutableStateOf(true)
    val loginStatus: MutableLiveData<LoginResult> = MutableLiveData<LoginResult>()


    fun isValidCredentials(email: String, password: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        val passwordPattern = Regex("^(?=.*[0–9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
        return emailPattern.matches(email) && password.isNotBlank()
    }

    fun login(user: LoginUser) {
        viewModelScope.launch {
            loginUseCase.login(user) {
                loginStatus.value = it
            }
        }
    }

    fun register(user: LoginUser) {
        loginStatus.value = LoginResult(false)
        viewModelScope.launch {
            registerUseCase.register(user) {
                loginStatus.value = it
            }
        }
    }

    fun isLoggedIn(): Boolean {
        val user = mAuth.currentUser
        return !user?.email.isNullOrEmpty()
    }
}