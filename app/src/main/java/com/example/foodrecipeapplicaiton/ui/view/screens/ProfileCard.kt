package com.example.foodrecipeapplicaiton.ui.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ProfileCard(userEmail: String, username: String, onLogout: () -> Unit) {
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
                    text = "Merhaba : $username",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = userEmail,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = { onLogout() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)

                ) {
                    Text(
                        text = "Logout",
                        color = Color.White,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}
