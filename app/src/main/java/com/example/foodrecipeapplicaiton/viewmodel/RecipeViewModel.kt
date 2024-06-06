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

    fun fetchRandomRecipes(apiKey: String, number: Int) {
        viewModelScope.launch {
            val response = repository.getRandomRecipes(apiKey, number)
            if (response.isSuccessful) {
                _recipes.value = response.body()?.recipes ?: emptyList()
            }
        }
    }
}