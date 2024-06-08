package com.example.foodrecipeapplicaiton.view.components


import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun UserChatTypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse), label = ""
    )
    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000, delayMillis = 300), RepeatMode.Reverse),
        label = ""
    )
    val alpha3 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000, delayMillis = 600), RepeatMode.Reverse),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background( color = Color.LightGray)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleUser(alpha1)
            Spacer(modifier = Modifier.size(4.dp))
            CircleUser(alpha2)
            Spacer(modifier = Modifier.size(4.dp))
            CircleUser(alpha3)
            Spacer(modifier = Modifier.size(4.dp))
            CircleUser(alpha1)
            Spacer(modifier = Modifier.size(4.dp))
            CircleUser(alpha2)
        }
    }
}

@Composable
fun ModelChatTypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse), label = ""
    )
    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000, delayMillis = 300), RepeatMode.Reverse),
        label = ""
    )
    val alpha3 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000, delayMillis = 600), RepeatMode.Reverse),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background( color = Color.DarkGray)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleModel(alpha1)
            Spacer(modifier = Modifier.size(4.dp))
            CircleModel(alpha2)
            Spacer(modifier = Modifier.size(4.dp))
            CircleModel(alpha3)
            Spacer(modifier = Modifier.size(4.dp))
            CircleModel(alpha1)
            Spacer(modifier = Modifier.size(4.dp))
            CircleModel(alpha2)
        }
    }
}


@Composable
fun CircleUser(alpha: Float) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(Color.Black)
    )
}


@Composable
fun CircleModel(alpha: Float) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(Color.White)
    )
}