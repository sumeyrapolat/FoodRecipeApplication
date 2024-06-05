package com.example.foodrecipeapplicaiton.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.foodrecipeapplicaiton.viewmodel.SignUpViewModel
import com.example.foodrecipeapplicaiton.view.components.PasswordTextField
import com.example.foodrecipeapplicaiton.view.components.SignUpButton
import com.example.foodrecipeapplicaiton.view.components.SignUpWithGoogle
import com.example.foodrecipeapplicaiton.view.components.UserEmailTextField
import com.example.foodrecipeapplicaiton.view.components.UserNameTextField
import com.example.foodrecipeapplicaiton.view.routes.Routes


@Composable
fun SignUpScreen(navController: NavController, signUpViewModel: SignUpViewModel = viewModel()) {
    val darkTheme = isSystemInDarkTheme()

    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                painter = painterResource(id = if (darkTheme) R.drawable.darkicon else R.drawable.lighticon),
                contentDescription = "logo",
                modifier = Modifier.size(220.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserNameTextField(onValueChange = { userName = it })
                UserEmailTextField(onValueChange = { userEmail = it })
                PasswordTextField(onValueChange = { password = it })
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        SignUpButton(
            onClick = {
                signUpViewModel.signUpUser(
                    email = userEmail,
                    username = userName,
                    password = password,
                    onSuccess = {
                        Log.i("SignUp", "Success")
                        navController.navigate("main")
                    },
                    onFailure = { Log.e("SignUp", "Error: ${it.message}") }
                )
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SignUpWithGoogle(onClicked = {
                Log.i("google button", "clicked")
            })
        }

        Text(
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                    navController.navigate(Routes.LOGIN)
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
    val navController = rememberNavController()
    SignUpScreen(navController = navController,)
}