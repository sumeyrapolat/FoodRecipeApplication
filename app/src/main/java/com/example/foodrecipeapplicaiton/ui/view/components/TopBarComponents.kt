package com.example.foodrecipeapplicaiton.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColor
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColorDark
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColor
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColorDark

@Composable
fun TopBar(darkTheme: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (darkTheme) TopAppBarColorDark else TopAppBarColor)
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "˗ˏˋ ★ ˎˊ˗",
            fontSize = 20.sp,
            color = if (darkTheme) TopBarTextColorDark else TopBarTextColor
        )
    }
}
