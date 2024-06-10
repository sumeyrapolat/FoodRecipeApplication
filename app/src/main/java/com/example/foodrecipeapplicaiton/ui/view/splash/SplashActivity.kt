package com.example.foodrecipeapplicaiton.ui.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.foodrecipeapplicaiton.MainActivity
import com.example.foodrecipeapplicaiton.ui.theme.FoodRecipeApplicaitonTheme
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
