package com.example.foodrecipeapplicaiton.ui.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColor
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColorDark
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColor
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColorDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(darkTheme: Boolean, onMenuClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "˗ˏˋ ★ ˎˊ˗",
                fontSize = 20.sp,
                color = if (darkTheme) TopBarTextColorDark else TopBarTextColor
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = if (darkTheme) Color.White else Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = if (darkTheme) TopAppBarColorDark else TopAppBarColor,
            titleContentColor = if (darkTheme) TopBarTextColorDark else TopBarTextColor,
            navigationIconContentColor = if (darkTheme) Color.White else Color.Black
        )
    )
}
