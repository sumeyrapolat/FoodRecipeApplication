package com.example.foodrecipeapplicaiton.view.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun UserChatItem(prompt: String, bitmap: Bitmap?, sentTime: Date) {
    ChatItem(
        content = {
            bitmap?.let {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(bottom = 3.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    bitmap = it.asImageBitmap()
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = prompt,
                fontSize = 17.sp,
                color = Color.Black
            )
        },
        sentTime = sentTime,
        backgroundColor = Color.LightGray,
        alignmentModifier = Modifier.padding(start = 100.dp, bottom = 16.dp)
    )
}

@Composable
fun ModelChatItem(response: String, sentTime: Date) {
    ChatItem(
        content = {
            Text(
                text = response,
                fontSize = 17.sp,
                color = Color.White
            )
        },
        sentTime = sentTime,
        backgroundColor = Color.DarkGray,
        alignmentModifier = Modifier.padding(end = 100.dp, bottom = 16.dp)
    )
}

@Composable
fun ChatItem(
    content: @Composable () -> Unit,
    sentTime: Date,
    backgroundColor: Color,
    alignmentModifier: Modifier
) {
    Column(modifier = alignmentModifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .padding(14.dp)
        ) {
            content()
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(sentTime),
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 2.dp, bottom = 2.dp)
            )
        }
    }
}