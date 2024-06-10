package com.example.foodrecipeapplicaiton.repository

import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.api.service.RecipeApiService
import com.example.foodrecipeapplicaiton.room.AppDatabase
import com.example.foodrecipeapplicaiton.room.FavoriteRecipe
import com.example.foodrecipeapplicaiton.room.FavoriteRecipeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val apiService: RecipeApiService,
    private val database: AppDatabase
) {

    suspend fun getRandomRecipes(apiKey: String, number: Int): List<Recipe> {
        return apiService.getRandomRecipes(apiKey, number).recipes
    }

    suspend fun getRecipeDetails(recipeId: Int, apiKey: String): Recipe {
        return apiService.getRecipeDetails(recipeId, apiKey)
    }


    fun getFavoriteRecipes(): Flow<List<FavoriteRecipe>> {
        return database.favoriteRecipeDao().getAllFavoriteRecipes()
    }

    suspend fun addFavoriteRecipe(recipe: FavoriteRecipe) {
        database.favoriteRecipeDao().insertFavoriteRecipe(recipe)
    }

    suspend fun removeFavoriteRecipe(recipe: FavoriteRecipe) {
        database.favoriteRecipeDao().deleteFavoriteRecipe(recipe)
    }
}
