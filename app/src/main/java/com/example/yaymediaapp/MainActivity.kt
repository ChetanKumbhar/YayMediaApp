package com.example.yaymediaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.yaymediaapp.navigation.setupNavHost
import com.example.yaymediaapp.ui.theme.YayMediaAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YayMediaAppTheme {
                setupNavHost()
            }
        }
    }
}