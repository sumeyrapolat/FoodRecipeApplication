package com.example.foodrecipeapplicaiton.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteRecipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRecipeDao(): FavoriteRecipeDao
}
