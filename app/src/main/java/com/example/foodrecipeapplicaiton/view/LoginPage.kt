package com.example.foodrecipeapplicaiton.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.view.components.LoginButton
import com.example.foodrecipeapplicaiton.view.components.PasswordTextField
import com.example.foodrecipeapplicaiton.view.components.UserNameTextField

@Composable
fun LoginScreen() {
    val darkTheme = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id =
                if (darkTheme) R.drawable.darkicon else R.drawable.ligthicon),
                contentDescription = "logo",
                modifier = Modifier.size(220.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserNameTextField()

                PasswordTextField()
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        LoginButton()

        Spacer(modifier = Modifier.height(30.dp))

    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}