package com.example.foodrecipeapplicaiton.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColorDark

@Composable
fun SideDrawerContent(
    userName: String,
    userEmail: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp)
            .background(TopAppBarColorDark)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Hello, $userName", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = userEmail, fontSize = 16.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(text = "Logout", color = Color.Black)
        }
    }
}
