package com.example.foodrecipeapplicaiton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipeapplicaiton.ui.theme.FoodRecipeApplicaitonTheme
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColor
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColorDark
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColor
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColorDark
import com.example.foodrecipeapplicaiton.view.routes.Routes
import com.example.foodrecipeapplicaiton.view.screens.LoginScreen
import com.example.foodrecipeapplicaiton.view.screens.MainScreen
import com.example.foodrecipeapplicaiton.view.screens.SignUpScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            FoodRecipeApplicaitonTheme(darkTheme = isSystemInDarkTheme) {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = Routes.LOGIN) {
                        composable(Routes.LOGIN) { LoginScreen(navController) }
                        composable(Routes.SIGN_UP) { SignUpScreen(navController) }
                        composable(Routes.MAIN) { MainScreen() }
                    }
                }
            }
        }
    }
}

@Composable
private fun MainContent() {
    val darkTheme = isSystemInDarkTheme()
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = if (darkTheme) TopAppBarColorDark else TopAppBarColor)
                        .height(60.dp)
                        .padding(horizontal = 25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "˗ˏˋ ★ ˎˊ˗",
                        fontSize = 20.sp,
                        color = if (darkTheme) TopBarTextColorDark else TopBarTextColor
                    )
                }
            }
        ) { paddingValues ->
            NavHost(navController = navController, startDestination = Routes.SIGN_UP, Modifier.padding(paddingValues)) {
                composable(Routes.SIGN_UP) { SignUpScreen(navController = navController)}
                composable(Routes.LOGIN) { LoginScreen(navController) }
                composable(Routes.MAIN) { MainScreen() }
            }
        }
    }
}

