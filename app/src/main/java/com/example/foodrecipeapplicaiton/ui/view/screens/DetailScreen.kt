package com.example.foodrecipeapplicaiton.ui.view.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.ui.view.components.RecipeCardDetail
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel

@Composable
fun DetailScreen(recipeId: Int, recipeViewModel: RecipeViewModel, navController: NavController, apiKey: String) {
    val recipe = recipeViewModel.selectedRecipe.collectAsState().value

    LaunchedEffect(recipeId) {
        recipeViewModel.fetchRecipeDetails(recipeId, apiKey)
    }

    Log.d("DetailScreen", "Recipe ID: $recipeId")
    if (recipe != null) {
        Log.d("DetailScreen", "Recipe found: ${recipe.title}")
    } else {
        Log.d("DetailScreen", "Recipe not found")
    }

    if (recipe != null) {
        val darkTheme = isSystemInDarkTheme()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                RecipeCardDetail(
                    title = recipe.title,
                    ingredients = recipe.extendedIngredients.joinToString(", ") { it.name },
                    imageUrl = recipe.image ?: if (darkTheme) R.drawable.darknoimage.toString() else R.drawable.lightnoimage.toString(),
                    instructions = recipe.instructions,
                    servings = recipe.servings,
                    readyInMinutes = recipe.readyInMinutes
                )
            }
        }
    } else {
        // Handle the case where the recipe is not found
        Text(text = "Recipe not found", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
    }
}