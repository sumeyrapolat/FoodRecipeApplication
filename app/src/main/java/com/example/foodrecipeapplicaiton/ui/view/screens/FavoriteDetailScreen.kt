package com.example.foodrecipeapplicaiton.ui.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodrecipeapplicaiton.ui.view.components.RecipeCardDetail
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel


@Composable
fun FavoriteDetail(
    recipeId: Int,
    navController: NavController,
    viewModel: RecipeViewModel
) {
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState(initial = emptyList())
    val selectedRecipe = favoriteRecipes.find { it.id == recipeId }


    selectedRecipe?.let { recipe ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RecipeCardDetail(
                title = recipe.title,
                ingredients = recipe.ingredients,
                imageUrl = recipe.imageUrl,
                instructions = recipe.instructions,
                servings = recipe.servings,
                readyInMinutes = recipe.readyInMinutes
            )
        }
    } ?: run {
        Text(
            text = "Recipe not found",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )
    }
}