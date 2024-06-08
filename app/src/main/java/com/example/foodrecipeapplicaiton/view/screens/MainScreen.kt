package com.example.foodrecipeapplicaiton.view.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.api.key.Constants.API_KEY
import com.example.foodrecipeapplicaiton.view.components.CategoryTabs
import com.example.foodrecipeapplicaiton.view.components.RecipeCard
import com.example.foodrecipeapplicaiton.view.routes.Routes
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel

@Composable
fun MainScreen(navController: NavController, recipeViewModel: RecipeViewModel) {

    val darkTheme = isSystemInDarkTheme()
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
                RecipeCard(
                    title = recipe.title,
                    ingredients = recipe.extendedIngredients.joinToString(", ") { it.name },
                    imageUrl = recipe.image ?: if (darkTheme) R.drawable.darknoimage.toString() else R.drawable.lightnoimage.toString(),
                    onClick = {

                        Log.d("MainScreen", "Recipe card clicked, navigating to detail screen...")
                        navController.navigate(Routes.detailScreenRoute(recipe.id))

                    }
                )
            }
        }
    }

    LaunchedEffect(recipeViewModel) {
        recipeViewModel.fetchRecipes(API_KEY, 250)
    }
}