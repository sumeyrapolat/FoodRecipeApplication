package com.example.foodrecipeapplicaiton.view.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.view.components.FavoriteRecipeCard
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel

@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: RecipeViewModel
) {
    // Favori tariflerin listesini alın
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()

    // Favori tariflerin detaylarına gitmek için işlev
    val onRecipeClicked: (Recipe) -> Unit = { recipe ->
        // Burada favori tarifin detaylarına gitmek için navController'ı kullanabilirsiniz
        // Örneğin:
        navController.navigate("detail/${recipe.id}")
    }

    // Favori tarifleri favorilerden kaldırmak için işlev
    val onRemoveFromFavorites: (Recipe) -> Unit = { recipe ->
        // Favori tarifleri listesinden tarifi kaldır
        viewModel.removeFavoriteRecipe(recipe)
    }

    // Favori tariflerin listelendiği kısmı oluşturun
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Favorite Recipes", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.padding(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items (favoriteRecipes) { recipe ->
                FavoriteRecipeCard(
                    title = recipe.title,
                    ingredients = recipe.extendedIngredients.joinToString(", "),
                    imageUrl = recipe.image ?: if (isSystemInDarkTheme()) R.drawable.darknoimage.toString() else R.drawable.lightnoimage.toString(),
                    onClick = { onRecipeClicked(recipe) },
                    isFavorite = true,
                    onFavoriteClicked = { onRemoveFromFavorites(recipe) }
                )
            }
        }
    }
}
