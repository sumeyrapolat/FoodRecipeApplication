package com.example.foodrecipeapplicaiton.api.models

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)