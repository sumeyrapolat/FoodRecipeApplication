package com.example.foodrecipeapplicaiton.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBar(navController: NavController, bottomNavItems: List<BottomNavItem>, onItemClick: (String) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(32.dp)
            )
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentRoute == item.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.route,
                            tint = if (selected) Color.Black else Color.White
                        )
                    },
                    selected = selected,
                    onClick = {
                        onItemClick(item.screenRoute)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.White,
                        indicatorColor = Color.White
                    )
                )
            }
        }
    }
}


data class BottomNavItem(val route: String, val icon: ImageVector, val screenRoute: String)
