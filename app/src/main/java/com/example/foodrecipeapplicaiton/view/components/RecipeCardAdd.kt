package com.example.foodrecipeapplicaiton.view.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.room.AppDatabase
import com.example.foodrecipeapplicaiton.room.FavoriteRecipe
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


@Composable
fun RecipeCardAdd(
    id: Int,
    title: String,
    ingredients: String,
    instructions: String,
    servings: Int,
    readyInMinutes: Int,
    imageUrl: String,
    onClick: () -> Unit,
    context: Context
) {
    var isFavorite by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope() // Add this line to remember the coroutine scope

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(27.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .padding(end = if (title.length > 30) 0.dp else 8.dp)
                        .clickable {
                            isFavorite = !isFavorite
                            coroutineScope.launch { // Launch a coroutine for database operations
                                val db = AppDatabase.getInstance(context)
                                val favoriteRecipeDao = db.favoriteRecipeDao()
                                if (isFavorite) {
                                    favoriteRecipeDao.insertFavoriteRecipe(
                                        FavoriteRecipe(
                                            id = id, // Kullanıcı tarafından belirtilen benzersiz kimlik
                                            title = title,
                                            ingredients = ingredients,
                                            imageUrl = imageUrl,
                                            instructions = instructions,
                                            servings = servings,
                                            readyInMinutes = readyInMinutes

                                        )
                                    )
                                    //Toast.makeText(context, "Recipe added to favorites", Toast.LENGTH_SHORT).show()
                                } else {
                                    favoriteRecipeDao.deleteFavoriteRecipe(
                                        FavoriteRecipe(
                                            id = id,
                                            title = title,
                                            ingredients = ingredients,
                                            imageUrl = imageUrl,
                                            instructions = instructions,
                                            servings = servings,
                                            readyInMinutes = readyInMinutes
                                        )
                                    )
                                    //Toast.makeText(context, "Recipe removed from favorites", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                ) {
                    Image(
                        painter = painterResource(if (isFavorite) R.drawable.liked else R.drawable.like),
                        contentDescription = "Favorite Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Ingredients:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Text(
                    text = ingredients,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
