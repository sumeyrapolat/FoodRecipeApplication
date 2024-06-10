package com.example.foodrecipeapplicaiton.ui.view.splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.ui.theme.FoodRecipeApplicaitonTheme
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColor
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColorDark


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