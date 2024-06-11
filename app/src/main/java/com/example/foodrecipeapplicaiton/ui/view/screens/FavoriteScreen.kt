    package com.example.foodrecipeapplicaiton.ui.view.screens

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavController
    import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
    import androidx.compose.ui.Alignment
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.Card
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.sp
    import com.example.foodrecipeapplicaiton.ui.view.components.FavoriteRecipeCard
    import com.example.foodrecipeapplicaiton.ui.view.routes.Routes


    @Composable
    fun FavoriteScreen(
        navController: NavController,
        viewModel: RecipeViewModel
    ) {
        val favoriteRecipes by viewModel.favoriteRecipes.collectAsState(initial = emptyList())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start= 10.dp),
                        text = "Favorite Recipes",
                        fontSize = 22.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,

                        )
                }
            }

            if (favoriteRecipes.isNotEmpty()) {
                val list = favoriteRecipes.reversed()
                items(list.size) { index ->
                    val recipe = list[index]
                    FavoriteRecipeCard(
                        id = recipe.id,
                        title = recipe.title,
                        ingredients = recipe.ingredients,
                        imageUrl = recipe.imageUrl,
                        onClick = {
                            navController.navigate(Routes.favoriteDetailScreenRoute(recipe.id))
                                  },
                        onDeleteClicked = {
                            viewModel.removeFavoriteRecipe(recipe)
                        }
                    )
                }
            } else {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .clip(RoundedCornerShape(25.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "No favorite recipes found.",
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                    maxLines = 1,
                                )
                            }
                        }
                    }

                }
            }
        }
    }
