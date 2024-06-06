package com.example.foodrecipeapplicaiton.api.models

import com.example.foodrecipeapplicaiton.api.models.Equipment
import com.example.foodrecipeapplicaiton.api.models.Ingredient
import com.example.foodrecipeapplicaiton.api.models.Length

data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)