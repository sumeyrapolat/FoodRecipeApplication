package com.example.foodrecipeapplicaiton.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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


@Composable
fun RecipeCardDetail(
    title: String,
    ingredients: String,
    imageUrl: String,
    instructions: String,
    servings: Int,
    readyInMinutes: Int
) {

    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 5.dp)
            .clip(RoundedCornerShape(16.dp)),
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
                    modifier = Modifier.padding(end = if (title.length > 30) 0.dp else 8.dp)
                        .clickable {
                            isFavorite = !isFavorite
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

            InformationColumn(title = "Ingredients", value = ingredients)
            InformationColumn(title = "Instructions", value = instructions)
            InformationRow(title = "Servings", value = servings.toString())
            InformationRow(title = "Ready Minutes", value = readyInMinutes.toString())

        }
    }
}

@Composable
fun InformationColumn(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$title: ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.DarkGray,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun InformationRow(title: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$title: ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.DarkGray,
            overflow = TextOverflow.Ellipsis
        )
    }
}
