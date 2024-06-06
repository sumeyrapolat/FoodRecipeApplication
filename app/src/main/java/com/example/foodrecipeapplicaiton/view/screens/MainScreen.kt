package com.example.foodrecipeapplicaiton.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.view.components.CategoryTabs
import com.example.foodrecipeapplicaiton.view.components.RecipeCard

@Composable
fun MainScreen() {

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.padding(10.dp))

        CategoryTabs()

        Spacer(modifier = Modifier.padding(10.dp))

        RecipeCard(
            title = "Delicious Pasta",
            ingredients = "Tomatoes, Basil, Garlic, Olive oil, Pasta, Salt, Pepper, Parmesan",
            imageRes = R.drawable.ic_launcher_background
        )

        RecipeCard(
            title = "Delicious Pasta",
            ingredients = "Tomatoes, Basil, Garlic, Olive oil, Pasta, Salt, Pepper, Parmesan",
            imageRes = R.drawable.ic_launcher_background
        )

    }

}


@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()

}
