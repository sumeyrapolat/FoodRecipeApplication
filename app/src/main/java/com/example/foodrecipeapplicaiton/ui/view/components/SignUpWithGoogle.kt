package com.example.foodrecipeapplicaiton.ui.view.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.ui.theme.GoogleBlue
import com.example.foodrecipeapplicaiton.ui.theme.GoogleGreen
import com.example.foodrecipeapplicaiton.ui.theme.GoogleRed
import com.example.foodrecipeapplicaiton.ui.theme.GoogleYellow
@Composable
fun SignUpWithGoogle(
    icon: Painter = painterResource(id = R.drawable.google),
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    onClicked: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "S",
                color = GoogleBlue,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "i",
                color = GoogleRed,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "g",
                color = GoogleYellow,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "n",
                color = GoogleGreen,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = " ",
                color = Color.Transparent,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "U",
                color = GoogleBlue,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "p",
                color = GoogleRed,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = " ",
                color = Color.Transparent,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "W",
                color = GoogleBlue,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "i",
                color = GoogleRed,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "t",
                color = GoogleYellow,
                modifier = Modifier.padding(end = 1.dp)
            )
            Text(
                text = "h",
                color = GoogleGreen,
                modifier = Modifier.padding(end = 1.dp)
            )
        }

        if (clicked) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = progressIndicatorColor
            )
            onClicked()
        }

        Icon(
            modifier = Modifier
                .width(100.dp)
                .clickable {
                    clicked = !clicked
                },
            painter = icon,
            contentDescription = "Google Logo",
            tint = Color.Unspecified,
        )
    }
}

@Composable
@Preview
fun SignUpWithGooglePreview() {
    SignUpWithGoogle(
        onClicked = {
            Log.i("google button", "clicked")
        }
    )
}