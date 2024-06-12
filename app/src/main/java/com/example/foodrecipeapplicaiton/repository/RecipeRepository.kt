package com.example.foodrecipeapplicaiton.repository

import android.util.Log
import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.api.service.RecipeApiService
import com.example.foodrecipeapplicaiton.room.FavoriteAppDatabase
import com.example.foodrecipeapplicaiton.room.FavoriteRecipe
import com.example.foodrecipeapplicaiton.room.MainDao
import com.example.foodrecipeapplicaiton.room.MainEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val apiService: RecipeApiService,
    private val database: FavoriteAppDatabase,
    private val mainDao: MainDao

) {

    suspend fun getRandomRecipes(apiKey: String, number: Int): List<Recipe> {
        return apiService.getRandomRecipes(apiKey, number).recipes
    }

    suspend fun getRecipeDetails(recipeId: Int, apiKey: String): Recipe {
        return apiService.getRecipeDetails(recipeId, apiKey)
    }


    suspend fun searchRecipes(query: String, apiKey: String): List<Recipe> {
        val allRecipes = getRandomRecipes(apiKey, 10000) // Büyük bir sayı vererek tüm tarifleri al
        val filteredRecipes = allRecipes.filter { it.title.contains(query, ignoreCase = true) }
        Log.d("RecipeRepository", "Search results for query '$query': ${filteredRecipes.size} recipes found")
        return filteredRecipes
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

    suspend fun getLocalRecipes(): List<MainEntity> {
        return mainDao.getAllRecipes()
    }

    suspend fun saveRecipesToLocalDatabase(recipes: List<Recipe>) {
        mainDao.insertRecipes(recipes.map { MainEntity.fromRecipe(it) })
    }

    suspend fun getRecipesByCategory(apiKey: String, number: Int, category: String): List<Recipe> {
        val allRecipes = getRandomRecipes(apiKey, number)
        val filteredRecipes = filterRecipesByCategory(allRecipes, category)
        return filteredRecipes
    }

    private fun filterRecipesByCategory(recipes: List<Recipe>, category: String): List<Recipe> {
        return when (category) {
            "Popular" -> recipes.filter { it.veryPopular }
            "Healthy" -> recipes.filter { it.veryHealthy }
            "Vegetarian" -> recipes.filter { it.vegetarian }
            "Gluten Free" -> recipes.filter { it.glutenFree }
            "Vegan" -> recipes.filter { it.vegan }
            "Dairy Free" -> recipes.filter { it.dairyFree }
            else -> recipes
        }
    }


}