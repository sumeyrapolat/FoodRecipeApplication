package com.example.foodrecipeapplicaiton.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.repository.RecipeRepository
import com.example.foodrecipeapplicaiton.room.FavoriteRecipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

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
            _isLoading.value = true // Yükleme başladığında isLoading durumunu true yap
            val allRecipes = repository.getRandomRecipes(apiKey, number)
            _recipes.value = if (category.isNullOrEmpty()) {
                allRecipes
            } else {
                filterRecipesByCategory(allRecipes, category)
            }
            _isLoading.value = false // Yükleme tamamlandığında isLoading durumunu false yap
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
}
