package com.example.foodrecipeapplicaiton.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipeapplicaiton.MainActivity
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.ui.theme.FoodRecipeApplicaitonTheme
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColor
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColorDark
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColor
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColorDark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FoodRecipeApplicaitonTheme {
                SplashScreen()
            }
        }

        // Delay using Coroutine
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000) // 1 seconds delay
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}

@Composable
fun SplashScreen() {
    val darkTheme = isSystemInDarkTheme()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (darkTheme) TopAppBarColorDark else TopAppBarColor
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id =
            if (darkTheme) R.drawable.darkicon else R.drawable.lighticon),
            contentDescription = "logo",
            modifier = Modifier.size(220.dp)
        )

    }
}
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    FoodRecipeApplicaitonTheme {
        SplashScreen()
    }
}