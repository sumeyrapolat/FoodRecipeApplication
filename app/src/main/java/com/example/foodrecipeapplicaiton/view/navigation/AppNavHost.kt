package com.example.foodrecipeapplicaiton.view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipeapplicaiton.view.routes.Routes
import com.example.foodrecipeapplicaiton.view.screens.LoginScreen
import com.example.foodrecipeapplicaiton.view.screens.MainScreen
import com.example.foodrecipeapplicaiton.view.screens.SignUpScreen


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.SIGN_UP) {
        composable(Routes.SIGN_UP) { SignUpScreen(navController = navController) }
        composable(Routes.LOGIN) { LoginScreen(navController = navController) }
        composable(Routes.MAIN) { MainScreen() }
    }
}