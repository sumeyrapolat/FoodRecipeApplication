package com.example.foodrecipeapplicaiton.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapplicaiton.api.key.Constants.API_KEY
import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.repository.RecipeRepository
import com.example.foodrecipeapplicaiton.room.FavoriteRecipe
import com.example.foodrecipeapplicaiton.room.MainDao
import com.example.foodrecipeapplicaiton.room.MainEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val mainDao: MainDao

) : ViewModel() {


    private val _recipes = MutableStateFlow<List<MainEntity>>(emptyList())
    val recipes: StateFlow<List<MainEntity>> = _recipes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _searchResults = MutableStateFlow<List<Recipe>>(emptyList())
    val searchResults: StateFlow<List<Recipe>> = _searchResults


    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe

    val favoriteRecipes: Flow<List<FavoriteRecipe>> = repository.getFavoriteRecipes()

    init {
        fetchRecipes(API_KEY, 200)
    }


    fun addFavoriteRecipe(recipe: FavoriteRecipe) {
        viewModelScope.launch {
            repository.addFavoriteRecipe(recipe)
        }
    }

    fun removeFavoriteRecipe(recipe: FavoriteRecipe) {
        viewModelScope.launch {
            repository.removeFavoriteRecipe(recipe)
        }
    }

    fun fetchRecipes(apiKey: String, number: Int, category: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val localRecipes = mainDao.getAllRecipes()
                if (localRecipes.isEmpty()) {
                    val allRecipes = repository.getRandomRecipes(apiKey, number)
                    val recipeEntities = allRecipes.map { MainEntity.fromRecipe(it) }
                    mainDao.insertRecipes(recipeEntities)
                    _recipes.value = recipeEntities
                } else {
                    _recipes.value = localRecipes
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch recipes"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun fetchMoreRecipes(apiKey: String, number: Int, category: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val moreRecipes = repository.getRandomRecipes(apiKey, number)
                val recipeEntities = moreRecipes.map { MainEntity.fromRecipe(it) }
                mainDao.insertRecipes(recipeEntities)
                _recipes.value += recipeEntities
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch more recipes"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun fetchRecipeDetails(recipeId: Int, apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val recipeDetails = repository.getRecipeDetails(recipeId, apiKey)
            _selectedRecipe.value = recipeDetails
            _isLoading.value = false
        }
    }

    private fun filterRecipesByCategory(recipes: List<Recipe>, category: String): List<Recipe> {
        return when (category) {
            "Popular" -> recipes.filter { it.veryPopular }
            "Healthy" -> recipes.filter { it.veryHealthy }
            "Vegetarian" -> recipes.filter { it.vegetarian }
            "Gluten Free" -> recipes.filter { it.glutenFree }
            "Vegan" -> recipes.filter { it.vegan }
            "Dairy Free" -> recipes.filter { it.dairyFree }
            else -> recipes
        }
    }
//
//    fun getRecipeById(recipeId: Int): Recipe? {
//        return recipes.value.find { it.id == recipeId }
//    }

    fun searchRecipes(query: String, apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val searchResults = repository.searchRecipes(query, apiKey)
                _searchResults.value = searchResults
                Log.d("RecipeViewModel", "Search results updated: ${searchResults.size} recipes found")
            } catch (e: Exception) {
                _searchResults.value = emptyList()
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun fetchRecipesByCategory(apiKey: String, number: Int, category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("RecipeViewModel", "Fetching recipes for category: $category")
                val filteredRecipes = repository.getRecipesByCategory(apiKey, number, category)
                val recipeEntities = filteredRecipes.map { MainEntity.fromRecipe(it) }
                mainDao.insertRecipes(recipeEntities)
                _recipes.value = recipeEntities
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch recipes by category"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }




}