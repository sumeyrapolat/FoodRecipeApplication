package com.example.foodrecipeapplicaiton.api.models


data class SimpleRecipe(
    val id: Int,
    val title: String,
    val image: String?,
    val extendedIngredients: List<String>, // Basitleştirilmiş malzemeler listesi
    val instructions: String,
    val servings: Int,
    val readyInMinutes: Int
)
