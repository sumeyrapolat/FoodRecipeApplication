package com.example.foodrecipeapplicaiton.ui.view.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.api.key.Constants.API_KEY
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
import com.example.foodrecipeapplicaiton.room.FavoriteRecipeDao
import com.example.foodrecipeapplicaiton.ui.view.components.CategoryTabs
import com.example.foodrecipeapplicaiton.ui.view.components.RecipeCardAdd
import com.example.foodrecipeapplicaiton.ui.view.components.SearchBar
import com.example.foodrecipeapplicaiton.ui.view.routes.Routes

@Composable
fun MainScreen(navController: NavController, favoriteRecipeDao: FavoriteRecipeDao) {
    val darkTheme = isSystemInDarkTheme()
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    val recipes by recipeViewModel.recipes.collectAsState()
    val searchResults by recipeViewModel.searchResults.collectAsState()
    val isLoading by recipeViewModel.isLoading.collectAsState()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val listState = rememberLazyListState()
    var endReached by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1 }
            .collect { isEnd ->
                if (isEnd && !endReached) {
                    endReached = true
                    recipeViewModel.fetchMoreRecipes(API_KEY, 100)
                    endReached = false
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CategoryTabs(onCategorySelected = { category ->
            recipeViewModel.fetchRecipes(API_KEY, 500, category)
        })

        Spacer(modifier = Modifier.padding(2.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SearchBar(
                    state = remember { mutableStateOf(searchQuery) },
                    onSearch = { query ->
                        Log.d("MainScreen", "Search query: $query")
                        searchQuery = TextFieldValue(query)
                        recipeViewModel.searchRecipes(query, API_KEY)
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }

            val displayedRecipes = if (searchQuery.text.isEmpty()) recipes else searchResults
            Log.d("MainScreen", "Displayed recipes count: ${displayedRecipes.size}")
            items(displayedRecipes) { recipe ->
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
                    favoriteRecipeDao = favoriteRecipeDao
                )
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    LaunchedEffect(Unit) {
        recipeViewModel.fetchRecipes(API_KEY, 500)
    }
}
