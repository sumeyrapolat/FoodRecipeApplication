package com.example.foodrecipeapplicaiton.di

import android.content.Context
import androidx.room.Room
import com.example.foodrecipeapplicaiton.room.FavoriteAppDatabase
import com.example.foodrecipeapplicaiton.room.FavoriteRecipeDao
import com.example.foodrecipeapplicaiton.room.MainDao
import com.example.foodrecipeapplicaiton.room.MainDatabase
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
    fun provideDatabase(@ApplicationContext appContext: Context): FavoriteAppDatabase {
        return Room.databaseBuilder(
            appContext,
            FavoriteAppDatabase::class.java,
            "app-database"
        ).build()
    }

    @Provides
    fun provideFavoriteRecipeDao(database: FavoriteAppDatabase): FavoriteRecipeDao {
        return database.favoriteRecipeDao()
    }


    @Provides
    fun provideMainDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MainDatabase::class.java,
            "main_database"
        ).build()
    }


    @Provides
    fun provideMainDao(mainDatabase: MainDatabase): MainDao {
        return mainDatabase.mainDao()
    }
}