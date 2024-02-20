package com.example.yaymediaapp.presenter.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yaymediaapp.data.model.LoginUser
import com.example.yaymediaapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isValid by remember {
        mutableStateOf(true)
    }

    val loginStatus = viewModel.loginStatus.observeAsState().value

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp),
            value = email,
            onValueChange = { newText ->
                email = newText
            },
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Enter your Email") },

            )

        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp),
            value = password,
            onValueChange = { newText ->
                password = newText
            },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your Password") }
        )


        if (!isValid) {
            Text(
                text = "Invalid email or password",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        loginStatus?.let{
            if (loginStatus.result) {
                navController.navigate(Screen.ProfileCreate.route){
                    popUpTo(Screen.Register.route)
                }
            } else if (loginStatus.error.isNotBlank()) {
                Text(
                    text = "Registration Failed. Please try again",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Button(
            onClick = {
                isValid = viewModel.isValidCredentials(email, password)
                if (isValid) {
                    viewModel.register(LoginUser(email, password))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Register")
        }
    }
}

