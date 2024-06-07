package com.example.foodrecipeapplicaiton.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodrecipeapplicaiton.view.routes.Routes

@Composable
fun BottomBar(navController: NavController) {
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
            val items = listOf(
                BottomNavItem("home", Icons.Filled.Home, "Home", Routes.MAIN),
                BottomNavItem("favorites", Icons.Filled.Favorite, "Favorites", Routes.MAIN), // Değiştirilecek route
                BottomNavItem("notifications", Icons.Filled.Notifications, "Notifications", Routes.MAIN), // Değiştirilecek route
                BottomNavItem("profile", Icons.Filled.Person, "Profile", Routes.MAIN) // Değiştirilecek route
            )
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.label, tint = Color.White) },
                    label = { Text(item.label, color = Color.White) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            // Clear back stack to prevent multiple instances of the same route
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String, val screenRoute: String)
