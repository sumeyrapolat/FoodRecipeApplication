package com.example.foodrecipeapplicaiton.repository

import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.api.service.RecipeApiService

class RecipeRepository(private val apiService: RecipeApiService) {

    suspend fun getRandomRecipes(apiKey: String, number: Int): List<Recipe> {
        return apiService.getRandomRecipes(apiKey, number).recipes
    }
}