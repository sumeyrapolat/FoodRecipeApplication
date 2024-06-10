package com.example.foodrecipeapplicaiton.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CategoryTabs(onCategorySelected: (String) -> Unit) {
    val categories = listOf("Popular", "Healthy", "Vegetarian", "Gluten Free", "Vegan", "Dairy Free")
    var selectedCategory by remember { mutableStateOf(0) }

   Column(
       modifier = Modifier.fillMaxWidth()
           .background(Color.White),
       horizontalAlignment = Alignment.Start

   ) {

       Text(
           modifier = Modifier.fillMaxWidth()
               .padding(start= 10.dp, bottom = 5.dp),
           text = "Categories",
           fontSize = 22.sp,
           fontWeight = FontWeight.Bold,
           color = Color.Black,

       )

       ScrollableTabRow(
           modifier = Modifier
               .padding(horizontal = 8.dp)
               .background(Color(0xFF1E1E1E)),
           selectedTabIndex = selectedCategory,
           contentColor = Color.White,
           edgePadding = 5.dp,
           indicator = { tabPositions ->
               TabRowDefaults.Indicator(
                   Modifier.tabIndicatorOffset(tabPositions[selectedCategory]),
                   color = Color.DarkGray
               )
           }
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
                   onClick = {
                       selectedCategory = index
                       onCategorySelected(category) // Call the callback when a category is selected
                   },
                   modifier = Modifier
                       .padding(vertical = 5.dp, horizontal = 5.dp)
                       .clip(RoundedCornerShape(20.dp))
                       .background(if (selectedCategory == index) Color.Black else Color.Transparent)
               )
           }
       }
   }
}


