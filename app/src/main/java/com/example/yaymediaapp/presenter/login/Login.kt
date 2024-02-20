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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yaymediaapp.data.model.LoginResult
import com.example.yaymediaapp.data.model.LoginUser
import com.example.yaymediaapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    if (viewModel.isLoggedIn()) {
        navController.navigate(Screen.ProfileCreate.route) {
            navController.popBackStack()
        }
    }else{
        LoginScreenDetails(viewModel, navController)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoginScreenDetails(
    viewModel: LoginViewModel,
    navController: NavController
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isValid by remember {
        viewModel.isValid
    }

    if (viewModel.isLoggedIn()) {
        navController.navigate(Screen.ProfileCreate.route) {
            navController.popBackStack()
        }
    }

    val loginStatus = viewModel.loginStatus.observeAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 24.sp, modifier = Modifier.padding(10.dp))

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
                text = "Please enter valid email or password ",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        loginStatus?.let {
            if (it.result) {
                navController.navigate(Screen.ProfileCreate.route) {
                    navController.popBackStack()
                }
            } else if (it.error.isNotBlank()) {
                Text(
                    text = "Wrong email or password. Please try again",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Button(
            onClick = {
                viewModel.loginStatus.value = LoginResult(false)
                isValid = viewModel.isValidCredentials(email, password)
                if (isValid) {
                    viewModel.login(LoginUser(email, password))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Login")
        }

        Button(
            onClick = {
                navController.navigate(Screen.Register.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Register")
        }
    }
}