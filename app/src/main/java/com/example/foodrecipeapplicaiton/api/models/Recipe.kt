package com.example.foodrecipeapplicaiton.api.models

import com.example.foodrecipeapplicaiton.api.models.AnalyzedInstruction
import com.example.foodrecipeapplicaiton.api.models.ExtendedIngredient

data class Recipe(
    val aggregateLikes: Int,
    val analyzedInstructions: List<AnalyzedInstruction>,
    val cheap: Boolean,
    val cookingMinutes: Any,
    val creditsText: String,
    val cuisines: List<Any>,
    val dairyFree: Boolean,
    val diets: List<String>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredient>,
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Int,
    val id: Int,
    val image: String?,
    val imageType: String,
    val instructions: String,
    val license: String,
    val lowFodmap: Boolean,
    val occasions: List<Any>,
    val originalId: Any,
    val preparationMinutes: Any,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String,
    val summary: String,
    val sustainable: Boolean,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int
)
fun Recipe.toSimpleRecipe(): SimpleRecipe {
    return SimpleRecipe(
        id = this.id,
        title = this.title,
        image = this.image,
        extendedIngredients = this.extendedIngredients.map { it.name }, // Örnek bir dönüşüm, lütfen kendi gereksinimlerinize göre ayarlayın
        instructions = this.instructions,
        servings = this.servings,
        readyInMinutes = this.readyInMinutes
    )
}
