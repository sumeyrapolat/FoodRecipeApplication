package com.example.foodrecipeapplicaiton.room


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipeapplicaiton.api.models.Recipe
import com.example.foodrecipeapplicaiton.api.models.SimpleRecipe

@Entity(tableName = "recipes")
data class MainEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val image: String?,
    val veryPopular: Boolean,
    val veryHealthy: Boolean,
    val vegetarian: Boolean,
    val glutenFree: Boolean,
    val vegan: Boolean,
    val dairyFree: Boolean,
    val extendedIngredients: String,
    val instructions: String,
    val servings: Int,
    val readyInMinutes: Int
) {
    companion object {
        fun fromRecipe(recipe: Recipe): MainEntity {
            return MainEntity(
                id = recipe.id,
                title = recipe.title,
                image = recipe.image,
                veryPopular = recipe.veryPopular,
                veryHealthy = recipe.veryHealthy,
                vegetarian = recipe.vegetarian,
                glutenFree = recipe.glutenFree,
                vegan = recipe.vegan,
                dairyFree = recipe.dairyFree,
                extendedIngredients = recipe.extendedIngredients.joinToString(", ") { it.name },
                instructions = recipe.instructions,
                servings = recipe.servings,
                readyInMinutes = recipe.readyInMinutes
            )
        }
    }
}
