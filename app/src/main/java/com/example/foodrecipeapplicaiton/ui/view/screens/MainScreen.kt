package com.example.foodrecipeapplicaiton.ui.view.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.api.key.Constants.API_KEY
import com.example.foodrecipeapplicaiton.view.components.CategoryTabs
import com.example.foodrecipeapplicaiton.view.components.RecipeCardAdd
import com.example.foodrecipeapplicaiton.view.routes.Routes
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
import com.example.foodrecipeapplicaiton.room.FavoriteRecipeDao

@Composable
fun MainScreen(navController: NavController, favoriteRecipeDao: FavoriteRecipeDao) {
    val darkTheme = isSystemInDarkTheme()
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    val recipes = recipeViewModel.recipes.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(10.dp))

        CategoryTabs(onCategorySelected = { category ->
            recipeViewModel.fetchRecipes(API_KEY, 250, category)
        })

        Spacer(modifier = Modifier.padding(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes) { recipe ->
                RecipeCardAdd(
                    id = recipe.id,
                    title = recipe.title,
                    ingredients = recipe.extendedIngredients.joinToString(", ") { it.name },
                    instructions = recipe.instructions,
                    servings = recipe.servings,
                    readyInMinutes = recipe.readyInMinutes,
                    imageUrl = recipe.image ?: if (darkTheme) R.drawable.darknoimage.toString() else R.drawable.lightnoimage.toString(),
                    onClick = {
                        Log.d("MainScreen", "Recipe card clicked, navigating to detail screen...")
                        navController.navigate(Routes.detailScreenRoute(recipe.id))
                    },
                    favoriteRecipeDao = favoriteRecipeDao // Pass favoriteRecipeDao directly
                )
            }
        }
    }

    LaunchedEffect(recipeViewModel) {
        recipeViewModel.fetchRecipes(API_KEY, 250)
    }
}
