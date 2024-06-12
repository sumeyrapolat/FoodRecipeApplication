package com.example.foodrecipeapplicaiton.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.repository.RecipeRepository
import com.example.foodrecipeapplicaiton.room.FavoriteRecipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _searchResults = MutableStateFlow<List<Recipe>>(emptyList())
    val searchResults: StateFlow<List<Recipe>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe

    val favoriteRecipes: Flow<List<FavoriteRecipe>> = repository.getFavoriteRecipes()

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
            val allRecipes = repository.getRandomRecipes(apiKey, number)
            _recipes.value = if (category.isNullOrEmpty()) {
                allRecipes
            } else {
                filterRecipesByCategory(allRecipes, category)
            }
            _isLoading.value = false
        }
    }

    fun fetchMoreRecipes(apiKey: String, number: Int, category: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            val moreRecipes = repository.getRandomRecipes(apiKey, number)
            _recipes.value = _recipes.value + moreRecipes
            _isLoading.value = false
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

    fun getRecipeById(recipeId: Int): Recipe? {
        return recipes.value.find { it.id == recipeId }
    }

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
}