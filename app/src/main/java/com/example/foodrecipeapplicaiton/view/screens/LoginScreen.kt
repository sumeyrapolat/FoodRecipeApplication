package com.example.foodrecipeapplicaiton.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.viewmodel.LoginViewModel
import com.example.foodrecipeapplicaiton.view.components.LoginButton
import com.example.foodrecipeapplicaiton.view.components.PasswordTextField
import com.example.foodrecipeapplicaiton.view.components.UserNameTextField
import com.example.foodrecipeapplicaiton.view.routes.Routes

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val darkTheme = isSystemInDarkTheme()

    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


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
                if (darkTheme) R.drawable.darkicon else R.drawable.lighticon),
                contentDescription = "logo",
                modifier = Modifier.size(220.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserNameTextField(onValueChange = { userName = it })

                PasswordTextField(onValueChange = { password = it })
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        LoginButton(

            onClick = {
                loginViewModel.loginUser(
                    username = userName,
                    password = password,
                    onSuccess = {
                        Log.i("Login", "Success")
                        navController.navigate("main")
                    },
                    onFailure = { Log.e("Login", "Error: ${it.message}") }
                )
            }

        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                   navController.navigate(Routes.SIGN_UP)
                },
            text = "Go To Sign Up Screen",
            color = if (darkTheme) Color.White else Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )

    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}