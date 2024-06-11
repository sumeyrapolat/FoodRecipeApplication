package com.example.foodrecipeapplicaiton.ui.view.navigation

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipeapplicaiton.api.key.Constants.API_KEY
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
import com.example.foodrecipeapplicaiton.room.FavoriteRecipeDao
import com.example.foodrecipeapplicaiton.ui.view.routes.Routes
import com.example.foodrecipeapplicaiton.ui.view.screens.ChatScreen
import com.example.foodrecipeapplicaiton.ui.view.screens.DetailScreen
import com.example.foodrecipeapplicaiton.ui.view.screens.FavoriteDetail
import com.example.foodrecipeapplicaiton.ui.view.screens.FavoriteScreen
import com.example.foodrecipeapplicaiton.ui.view.screens.LoginScreen
import com.example.foodrecipeapplicaiton.ui.view.screens.MainScreen
import com.example.foodrecipeapplicaiton.ui.view.screens.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    recipeViewModel: RecipeViewModel,
    favoriteRecipeDao: FavoriteRecipeDao,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
    paddingValues: PaddingValues,
    uriState: MutableStateFlow<String>,
) {
    val auth = FirebaseAuth.getInstance()
    val startDestination = if (auth.currentUser != null) Routes.MAIN else Routes.LOGIN

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.SIGN_UP) {
            SignUpScreen(navController = navController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(Routes.MAIN) {
            Log.d("AppNavHost", "Navigating to MAIN screen")
            MainScreen(navController = navController, favoriteRecipeDao = favoriteRecipeDao)
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
                DetailScreen(
                    recipeId = recipeId,
                    recipeViewModel = recipeViewModel,
                    navController = navController,
                    apiKey = API_KEY // Pass the API key
                )
            } else {
                Log.e("AppNavHost", "recipeId is null, arguments: ${backStackEntry.arguments}")
            }
        }
        composable("${Routes.FAVORITE_DETAIL_SCREEN}/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            if (recipeId != null) {
                Log.d("AppNavHost", "Navigating to DETAIL screen with recipeId: $recipeId")
                FavoriteDetail(recipeId = recipeId, navController = navController, viewModel = recipeViewModel)
            } else {
                Log.e("AppNavHost", "recipeId is null, arguments: ${backStackEntry.arguments}")
            }
        }
    }
}
