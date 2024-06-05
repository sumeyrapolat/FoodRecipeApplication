package com.example.foodrecipeapplicaiton.view

import androidx.compose.runtime.Composable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.view.components.PasswordTextField
import com.example.foodrecipeapplicaiton.view.components.SignUpButton
import com.example.foodrecipeapplicaiton.view.components.SignUpWithGoogle
import com.example.foodrecipeapplicaiton.view.components.UserEmailTextField
import com.example.foodrecipeapplicaiton.view.components.UserNameTextField

@Composable
fun SignUpScreen(paddingValues: PaddingValues) {
    val darkTheme = isSystemInDarkTheme()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id =
                if (darkTheme) R.drawable.darkicon else R.drawable.lighticon),
                contentDescription = "logo",
                modifier = Modifier.size(220.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserNameTextField()

                UserEmailTextField()

                PasswordTextField()

            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        SignUpButton()

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SignUpWithGoogle( onClicked = {
                Log.i("google button", "clicked")
            })
        }

        Text(
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                    //onClick()
                           },
            text = "I Have Already an Account",
            color = if (darkTheme) Color.White else Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            )
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(paddingValues = PaddingValues(1.dp))
}