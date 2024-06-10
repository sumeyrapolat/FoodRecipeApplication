package com.example.foodrecipeapplicaiton.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipeDao {

    @Query("SELECT * FROM favorite_recipes")
    fun getAllFavoriteRecipes(): Flow<List<FavoriteRecipe>>

    @Insert
    suspend fun insertFavoriteRecipe(recipe: FavoriteRecipe): Long

    @Delete
    suspend fun deleteFavoriteRecipe(recipe: FavoriteRecipe)
}