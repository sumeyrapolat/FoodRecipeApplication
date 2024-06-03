package com.example.foodrecipeapplicaiton.view.components

import android.util.Log
import android.widget.Button
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.example.foodrecipeapplicaiton.ui.theme.BackgroundColor
import com.example.foodrecipeapplicaiton.ui.theme.BackgroundColorDark
import com.example.foodrecipeapplicaiton.ui.theme.ButtonColor
import com.example.foodrecipeapplicaiton.ui.theme.ButtonColorDark
import com.example.foodrecipeapplicaiton.ui.theme.ButtonTextColor
import com.example.foodrecipeapplicaiton.ui.theme.ButtonTextColorDark
import com.example.foodrecipeapplicaiton.ui.theme.HintColor
import com.example.foodrecipeapplicaiton.ui.theme.HintColorDark
import com.example.foodrecipeapplicaiton.ui.theme.LabelColor
import com.example.foodrecipeapplicaiton.ui.theme.LabelColorDark
import com.example.foodrecipeapplicaiton.ui.theme.OutlinedTextFieldColor
import com.example.foodrecipeapplicaiton.ui.theme.OutlinedTextFieldColorDark
import com.example.foodrecipeapplicaiton.ui.theme.TextColor
import com.example.foodrecipeapplicaiton.ui.theme.TextColorDark

@Composable
fun UserNameTextField() {

    var username by remember { mutableStateOf("") }
    val darkTheme = isSystemInDarkTheme()

    OutlinedTextField(
        value = username,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if(darkTheme) HintColorDark else HintColor,
            unfocusedBorderColor =  if(darkTheme) OutlinedTextFieldColorDark else OutlinedTextFieldColor,
            focusedLabelColor =  if(darkTheme) LabelColorDark else LabelColor,
            unfocusedLabelColor =  if(darkTheme) HintColorDark else HintColor,
            focusedContainerColor =  if(darkTheme) BackgroundColorDark else BackgroundColor,
            unfocusedContainerColor = if(darkTheme) BackgroundColorDark else BackgroundColor
        ),
        singleLine = true,      // giriş bilgilerinin tek bir satırda kalmasını
        maxLines = 1,
        onValueChange = { username = it },
        label = { Text(text = "Password", color = if(darkTheme) LabelColorDark else LabelColor) },
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
    val darkTheme = isSystemInDarkTheme()


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
            focusedBorderColor = if(darkTheme) HintColorDark else HintColor,
            unfocusedBorderColor =  if(darkTheme) OutlinedTextFieldColorDark else OutlinedTextFieldColor,
            focusedLabelColor =  if(darkTheme) LabelColorDark else LabelColor,
            unfocusedLabelColor =  if(darkTheme) HintColorDark else HintColor,
            focusedContainerColor =  if(darkTheme) BackgroundColorDark else BackgroundColor,
            unfocusedContainerColor = if(darkTheme) BackgroundColorDark else BackgroundColor
        ),
        singleLine = true,      // giriş bilgilerinin tek bir satırda kalmasını
        maxLines = 1,
        onValueChange = {
            password = it
        },
        label = {
            Text(text = "Password", color = if(darkTheme) LabelColorDark else LabelColor)
        },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = icon,
                    contentDescription = "Visibility Lock Icon",
                    tint = if(darkTheme) TextColorDark else TextColor
                )
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
    val darkTheme = isSystemInDarkTheme()
    var email by remember { mutableStateOf("") }

    OutlinedTextField(
        value = email,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if(darkTheme) HintColorDark else HintColor,
            unfocusedBorderColor =  if(darkTheme) OutlinedTextFieldColorDark else OutlinedTextFieldColor,
            focusedLabelColor =  if(darkTheme) LabelColorDark else LabelColor,
            unfocusedLabelColor =  if(darkTheme) HintColorDark else HintColor,
            focusedContainerColor =  if(darkTheme) BackgroundColorDark else BackgroundColor,
            unfocusedContainerColor = if(darkTheme) BackgroundColorDark else BackgroundColor
        ),
        singleLine = true,      // giriş bilgilerinin tek bir satırda kalmasını
        maxLines = 1,
        onValueChange = { email = it },
        label = { Text(text = "Email Address", color = if(darkTheme) LabelColorDark else LabelColor) },
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
fun SignUpButton() {
    val darkTheme = isSystemInDarkTheme()

    Button(
        onClick = {
            //println("Credentials: Email = $email , Password = $password")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor =  if(darkTheme) ButtonColorDark else ButtonColor, // Arka plan rengi
            contentColor =  if(darkTheme)  ButtonTextColorDark else ButtonTextColor // Metin rengi
        )
    ) {
        Text(text = "Sign Up", fontSize = 16.sp)
    }
}

@Composable
fun LoginButton() {
    val darkTheme = isSystemInDarkTheme()

    Button(
        onClick = {
            //println("Credentials: Email = $email , Password = $password")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor =  if(darkTheme) ButtonColorDark else ButtonColor, // Arka plan rengi
            contentColor =  if(darkTheme)  ButtonTextColorDark else ButtonTextColor // Metin rengi
        )
    ) {
        Text(text = "Login", fontSize = 16.sp)
    }
}