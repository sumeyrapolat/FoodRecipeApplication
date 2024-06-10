package com.example.foodrecipeapplicaiton.repository

import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.api.service.RecipeApiService
import com.example.foodrecipeapplicaiton.room.AppDatabase
import com.example.foodrecipeapplicaiton.room.FavoriteRecipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(
    private val apiService: RecipeApiService,
    private val database: AppDatabase
) {

    suspend fun getRandomRecipes(apiKey: String, number: Int): List<Recipe> {
        return apiService.getRandomRecipes(apiKey, number).recipes
    }

    // Room veritabanından favori tarifleri almak için işlev
    fun getFavoriteRecipes(): Flow<List<FavoriteRecipe>> {
        return database.favoriteRecipeDao().getAllFavoriteRecipes()
    }

    // Room veritabanına favori tarif eklemek için işlev
    suspend fun addFavoriteRecipe(recipe: FavoriteRecipe) {
        database.favoriteRecipeDao().insertFavoriteRecipe(recipe)
    }

    // Room veritabanından favori tarif çıkarmak için işlev
    suspend fun removeFavoriteRecipe(recipe: FavoriteRecipe) {
        database.favoriteRecipeDao().deleteFavoriteRecipe(recipe)
    }
}
