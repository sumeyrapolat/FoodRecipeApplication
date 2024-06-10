package com.example.foodrecipeapplicaiton.di

import android.content.Context
import androidx.room.Room
import com.example.foodrecipeapplicaiton.room.AppDatabase
import com.example.foodrecipeapplicaiton.room.FavoriteRecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    @Provides
    fun provideFavoriteRecipeDao(database: AppDatabase): FavoriteRecipeDao {
        return database.favoriteRecipeDao()
    }
}
