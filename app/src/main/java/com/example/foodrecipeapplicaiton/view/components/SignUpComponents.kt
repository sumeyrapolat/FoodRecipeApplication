package com.example.foodrecipeapplicaiton.view.components

import android.util.Log
import android.widget.Button
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.foodrecipeapplicaiton.R

@Composable
fun UserNameTextField() {

    var username by remember { mutableStateOf("") }

    OutlinedTextField(
        value = username,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        singleLine = true,      // giriş bilgilerinin tek bir satırda kalmasını
        maxLines = 1,
        onValueChange = { username = it },
        label = { Text(text = "User Name") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send
        ),

        keyboardActions = KeyboardActions(
            onSearch = {
                Log.i("ImeAction", "clicked")
            }
        )
    )
}

@Composable
fun PasswordTextField() {

    var password by remember { mutableStateOf("") }
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    val icon = if(passwordVisibility){
        painterResource(id = R.drawable.outline_visibility_24)
    }else {
        painterResource(id = R.drawable.outline_visibility_off_24)
    }


    OutlinedTextField(
        value = password,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        singleLine = true,      // giriş bilgilerinin tek bir satırda kalmasını
        maxLines = 1,
        onValueChange = {
            password = it
        },
        label = {
            Text(text = "Password")
        },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = icon,
                    contentDescription ="Visibilitiy Lock Icon")

            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done

        ),
        keyboardActions = KeyboardActions(
            onDone = {
                Log.i("ImeAction", "clicked")

            }
        ),
        visualTransformation = if(passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation()


    )
}

@Composable
fun UserEmailTextField() {

    var email by remember { mutableStateOf("") }

    OutlinedTextField(
        value = email,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        singleLine = true,      // giriş bilgilerinin tek bir satırda kalmasını
        maxLines = 1,
        onValueChange = { email = it },
        label = { Text(text = "Email Address") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Search
        ),

        keyboardActions = KeyboardActions(
            onSearch = {
                Log.i("ImeAction", "clicked")
            }
        )
    )
}

@Composable
fun ActionButton() {
    Button(
        onClick = {
            //println("Credentials: Email = $email , Password = $password")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black, // Arka plan rengi
            contentColor = Color.White // Metin rengi
        )
    ) {
        Text(text = "Sign Up", fontSize = 16.sp)
    }
}

