package com.example.foodrecipeapplicaiton.api.service

import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.api.models.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("recipes/random")
    suspend fun getRandomRecipes(@Query("apiKey") apiKey: String, @Query("number") number: Int): RecipeResponse

    @GET("recipes/complexSearch")
    suspend fun getRecipesByCategory(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int,
        @Query("diet") category: String
    ): RecipeResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): Recipe
}