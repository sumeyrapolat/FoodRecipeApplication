package com.example.foodrecipeapplicaiton.view.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.view.components.RecipeCardDetail
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel

@Composable
fun DetailScreen(recipeId: Int, recipeViewModel: RecipeViewModel, navController: NavController) {
    val recipe = recipeViewModel.getRecipeById(recipeId)

    recipe?.let { selectedRecipe ->
        val darkTheme = isSystemInDarkTheme()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                RecipeCardDetail(
                    title = selectedRecipe.title,
                    ingredients = selectedRecipe.extendedIngredients.joinToString(", ") { it.name },
                    imageUrl = selectedRecipe.image ?: if (darkTheme) R.drawable.darknoimage.toString() else R.drawable.lightnoimage.toString(),
                    instructions = selectedRecipe.instructions,
                    servings = selectedRecipe.servings,
                    readyInMinutes = selectedRecipe.readyInMinutes
                )
            }
        }
    }
}



