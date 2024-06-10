package com.example.foodrecipeapplicaiton.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_recipes")
data class FavoriteRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val ingredients: String,
    val imageUrl: String,
    val instructions: String,
    val servings: Int,
    val readyInMinutes: Int
)