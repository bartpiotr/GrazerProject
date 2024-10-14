package com.bartekjobhunt.grazerproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bartekjobhunt.grazerproject.ui.login.LoginScreen
import com.bartekjobhunt.grazerproject.ui.theme.GrazerProjectTheme
import com.bartekjobhunt.grazerproject.ui.users.UsersScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            GrazerProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(onLoginSuccess = {
                                navController.navigate(
                                    "users"
                                ){
                                    popUpTo("login") { inclusive = true }
                                    launchSingleTop = true
                                }

                            }, modifier = Modifier.padding(innerPadding))
                        }
                        composable("users") { UsersScreen() }
                    }
                }
            }
        }
    }
}


