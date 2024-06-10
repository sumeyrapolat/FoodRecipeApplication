package com.example.foodrecipeapplicaiton.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.ui.theme.HintColor
import com.example.foodrecipeapplicaiton.ui.theme.HintColorDark
import com.example.foodrecipeapplicaiton.ui.theme.LabelColor
import com.example.foodrecipeapplicaiton.ui.theme.LabelColorDark
import com.example.foodrecipeapplicaiton.ui.theme.OutlinedTextFieldColor
import com.example.foodrecipeapplicaiton.ui.theme.OutlinedTextFieldColorDark

@Composable
fun SearchBar(
    state: MutableState<TextFieldValue>,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val darkTheme = isSystemInDarkTheme()
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFF0F0F0), CircleShape)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = state.value,
                onValueChange = {
                    state.value = it
                },
                placeholder = { Text("Search", color = Color.Gray) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(state.value.text)
                        keyboardController?.hide()
                    }
                ),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if(darkTheme) HintColorDark else HintColor,
                    unfocusedBorderColor = if(darkTheme) OutlinedTextFieldColorDark else OutlinedTextFieldColor,
                    focusedLabelColor = if(darkTheme) LabelColorDark else LabelColor,
                    unfocusedLabelColor = if(darkTheme) HintColorDark else HintColor,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


