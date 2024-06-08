package com.example.foodrecipeapplicaiton.view.navigation

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipeapplicaiton.view.routes.Routes
import com.example.foodrecipeapplicaiton.view.screens.ChatScreen
import com.example.foodrecipeapplicaiton.view.screens.DetailScreen
import com.example.foodrecipeapplicaiton.view.screens.FavoriteScreen
import com.example.foodrecipeapplicaiton.view.screens.LoginScreen
import com.example.foodrecipeapplicaiton.view.screens.MainScreen
import com.example.foodrecipeapplicaiton.view.screens.SignUpScreen
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    recipeViewModel: RecipeViewModel,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
    paddingValues: PaddingValues,
    uriState: MutableStateFlow<String>
) {
    NavHost(navController = navController, startDestination = Routes.MAIN) {
        composable(Routes.SIGN_UP) { SignUpScreen(navController = navController) }
        composable(Routes.LOGIN) { LoginScreen(navController = navController) }
        composable(Routes.MAIN) {
            Log.d("AppNavHost", "Navigating to MAIN screen")
            MainScreen(navController = navController, recipeViewModel = recipeViewModel)
        }
        composable(Routes.FAVORITE_SCREEN) {
            FavoriteScreen(navController = navController, viewModel = recipeViewModel)
        }

        composable(Routes.CHAT_SCREEN) {
            ChatScreen(
                navController = navController,
                imagePicker = imagePicker,
                paddingValues = paddingValues,
                uriState = uriState
            )
        }

        composable("${Routes.DETAIL_SCREEN}/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            if (recipeId != null) {
                Log.d("AppNavHost", "Navigating to DETAIL screen with recipeId: $recipeId")
                DetailScreen(recipeId = recipeId, recipeViewModel = recipeViewModel, navController = navController)
            } else {
                Log.e("AppNavHost", "recipeId is null, arguments: ${backStackEntry.arguments}")
            }
        }
    }
}
