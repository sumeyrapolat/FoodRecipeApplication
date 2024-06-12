package com.example.foodrecipeapplicaiton.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MainDao {
    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<MainEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<MainEntity>)

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Int): MainEntity?
}
