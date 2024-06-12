package com.example.foodrecipeapplicaiton.ui.view.screens

import UpdateRecipesWorker
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

@Composable
fun MainScreen(navController: NavController, favoriteRecipeDao: FavoriteRecipeDao) {
    val darkTheme = isSystemInDarkTheme()
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    val recipes by recipeViewModel.recipes.collectAsState()
    val isLoading by recipeViewModel.isLoading.collectAsState()
    val errorMessage by recipeViewModel.errorMessage.collectAsState()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val listState = rememberLazyListState()
    var endReached by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isNetworkAvailable = isNetworkAvailable(context)

    if (isNetworkAvailable) {
        LaunchedEffect(Unit) {
            recipeViewModel.fetchRecipes(API_KEY, 500)
        }

        LaunchedEffect(Unit) {
            val workRequest = PeriodicWorkRequestBuilder<UpdateRecipesWorker>(1, TimeUnit.DAYS)
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }

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
                Log.d("MainScreen", "Selected category: $category")
                recipeViewModel.fetchRecipesByCategory(API_KEY, 500, category)
            })

            Spacer(modifier = Modifier.padding(2.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
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
                                searchQuery = TextFieldValue(query)
                                recipeViewModel.searchRecipes(query, API_KEY)
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    val displayedRecipes = recipes.filter { recipe ->
                        recipe.title.contains(searchQuery.text, ignoreCase = true)
                    }

                    Log.d("MainScreen", "Displaying ${displayedRecipes.size} recipes")

                    items(displayedRecipes) { recipe ->
                        RecipeCardAdd(
                            id = recipe.id,
                            title = recipe.title,
                            ingredients = recipe.extendedIngredients,
                            instructions = recipe.instructions,
                            servings = recipe.servings,
                            readyInMinutes = recipe.readyInMinutes,
                            imageUrl = recipe.image ?: if (darkTheme) R.drawable.darknoimage.toString() else R.drawable.lightnoimage.toString(),
                            onClick = {
                                navController.navigate(Routes.detailScreenRoute(recipe.id))
                            },
                            favoriteRecipeDao = favoriteRecipeDao
                        )
                    }
                }
            }
        }
    } else {
        LaunchedEffect(Unit) {
            recipeViewModel.fetchRecipes(API_KEY, 500)
        }

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
                recipeViewModel.fetchRecipesByCategory(API_KEY, 500, category)
            })
            Spacer(modifier = Modifier.padding(2.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
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
                                searchQuery = TextFieldValue(query)
                                // Tarifleri aramak için view model üzerinden searchRecipes fonksiyonunu çağırın
                                recipeViewModel.searchRecipes(query, API_KEY)
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    val displayedRecipes = recipes
                    items(displayedRecipes) { recipe ->
                        RecipeCardAdd(
                            id = recipe.id,
                            title = recipe.title,
                            ingredients = recipe.extendedIngredients,
                            instructions = recipe.instructions,
                            servings = recipe.servings,
                            readyInMinutes = recipe.readyInMinutes,
                            imageUrl = recipe.image
                                ?: if (darkTheme) R.drawable.darknoimage.toString() else R.drawable.lightnoimage.toString(),
                            onClick = {
                                navController.navigate(Routes.detailScreenRoute(recipe.id))
                            },
                            favoriteRecipeDao = favoriteRecipeDao
                        )
                    }
                }
            }
        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}
