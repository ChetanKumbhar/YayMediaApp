package com.example.yaymediaapp.presenter.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yaymediaapp.data.model.User
import com.example.yaymediaapp.domain.usecases.CreateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")
const val PROFILE_USER_ID = "CHETAN_ID"
@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase,
) : ViewModel(){
    private val _name = MutableStateFlow("")
    private val _username = MutableStateFlow("")
    private val _imageUri = MutableStateFlow(EMPTY_IMAGE_URI)
    private val _progress = MutableStateFlow(0f)

    private val _state = MutableStateFlow(CreateAccountViewState())
    val state: StateFlow<CreateAccountViewState>
        get() = _state

    val isUserCreated = mutableStateOf(false)

    init {

        viewModelScope.launch {
           val user =  createAccountUseCase.getUser(PROFILE_USER_ID)
            if(user!= null && user.id == PROFILE_USER_ID){
                isUserCreated.value = true
            }
        }

        viewModelScope.launch {
            combine(_name, _username, _imageUri, _progress) { name, username, imageUri, progress ->
                CreateAccountViewState(
                    name = name,
                    username = username,
                    imageUri = imageUri,
                    progress = progress,
                    inProgress = progress > 0
                )
            }.collect { _state.value = it }
        }
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setImageUri(imageUri: Uri) {
        _imageUri.value = imageUri
    }

    fun createAccount(result: (Boolean) -> Unit) {
        // If the user info are not valid
        if (!_state.value.enableCreateButton) {
            Log.e("chetan_logs","The user info are not valid: ${_state.value}")
            return
        }

        viewModelScope.launch {
            state.value.run {
                createAccountUseCase.createProfileAccount(User(PROFILE_USER_ID, name, username, imageUri.toString()))
                result(true)
            }.runCatching {
                result(false)
                Log.e("chetan_logs","Error in creating account: ${_state.value}")
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            createAccountUseCase.getUser(PROFILE_USER_ID)
        }
    }
}

data class CreateAccountViewState(
    val name: String = "",
    val username: String = "",
    val imageUri: Uri = EMPTY_IMAGE_URI,
    val progress: Float = 0f,
    val inProgress: Boolean = false
) {
    val enableCreateButton: Boolean =
        name.isNotBlank() && username.isNotBlank() && imageUri != EMPTY_IMAGE_URI && !inProgress
}
