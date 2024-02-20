package com.example.yaymediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yaymediaapp.presenter.login.LoginScreen
import com.example.yaymediaapp.presenter.login.RegisterScreen
import com.example.yaymediaapp.presenter.posts.CreatePost
import com.example.yaymediaapp.presenter.posts.PostList
import com.example.yaymediaapp.presenter.profile.CreateProfileScreen

@Composable
fun setupNavHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(route = Screen.Login.route){
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Register.route){
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.ProfileCreate.route){
            CreateProfileScreen(navController = navController)
        }
        composable(route = Screen.PostList.route){
            PostList(navController = navController)
        }
        composable(route = Screen.CretePost.route){
            CreatePost(navController = navController)
        }
    }
}