package com.example.foodrecipeapplicaiton.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CategoryTabs() {
    val categories = listOf("Popular", "Vegetarian", "Gluten Free", "Vegan", "Dairy Free")
    var selectedCategory by remember { mutableStateOf(0) }

    ScrollableTabRow(
        modifier = Modifier
            .background(Color(0xFF1E1E1E))
            .padding(horizontal = 8.dp),
        selectedTabIndex = selectedCategory,
        contentColor = Color.White,
        edgePadding = 16.dp
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                text = {
                    Text(
                        text = category,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedCategory == index) Color.White else Color.Gray,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = selectedCategory == index,
                onClick = { selectedCategory = index },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (selectedCategory == index) Color.Black else Color.Transparent)
                    .padding(vertical = 5.dp, horizontal = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryTabsPreview() {
    CategoryTabs()
}
