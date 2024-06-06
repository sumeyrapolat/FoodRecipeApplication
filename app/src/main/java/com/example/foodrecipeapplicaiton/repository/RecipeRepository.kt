package com.example.foodrecipeapplicaiton.repository

import com.example.foodrecipeapplicaiton.api.models.RecipeResponse
import com.example.foodrecipeapplicaiton.api.service.RecipeApiService
import retrofit2.Response

class RecipeRepository(private val apiService: RecipeApiService) {
    suspend fun getRandomRecipes(apiKey: String, number: Int): Response<RecipeResponse> {
        return apiService.getRandomRecipes(apiKey, number)
    }
}
