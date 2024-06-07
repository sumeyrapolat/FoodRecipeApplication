package com.example.foodrecipeapplicaiton.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    fun fetchRecipes(apiKey: String, number: Int, category: String? = null) {
        viewModelScope.launch {
            val allRecipes = repository.getRandomRecipes(apiKey, number)
            _recipes.value = if (category.isNullOrEmpty()) {
                allRecipes
            } else {
                filterRecipesByCategory(allRecipes, category)
            }
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