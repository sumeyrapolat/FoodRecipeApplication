package com.example.foodrecipeapplicaiton.api.service

import com.example.foodrecipeapplicaiton.api.models.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): Response<RecipeResponse>
}