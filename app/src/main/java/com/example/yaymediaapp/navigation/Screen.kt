package com.example.yaymediaapp.navigation

sealed class Screen(val route : String) {
    object Login : Screen("Login")
    object Register : Screen("Register")
    object ProfileCreate : Screen("ProfileCreate")
    object PostList : Screen("PostList")
    object CretePost : Screen("CretePost")
}