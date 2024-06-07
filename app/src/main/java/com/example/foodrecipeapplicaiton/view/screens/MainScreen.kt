package com.example.foodrecipeapplicaiton.view.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodrecipeapplicaiton.api.service.RetrofitClient
import com.example.foodrecipeapplicaiton.repository.RecipeRepository
import com.example.foodrecipeapplicaiton.view.components.CategoryTabs
import com.example.foodrecipeapplicaiton.view.components.RecipeCard
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModelFactory



@Composable
fun MainScreen(recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(
    RecipeRepository(RetrofitClient.recipeApiService)
))) {

    val recipes = recipeViewModel.recipes.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(10.dp))

        CategoryTabs()

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
                    imageUrl = recipe.image
                )
            }
        }
    }

    LaunchedEffect(recipeViewModel) {
        recipeViewModel.fetchRandomRecipes("ac2c2e45e95d4fd7b935bf5994abd918", 200)
    }
}



@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()

}
