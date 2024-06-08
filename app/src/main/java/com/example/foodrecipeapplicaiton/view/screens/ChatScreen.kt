package com.example.foodrecipeapplicaiton.view.screens

import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.foodrecipeapplicaiton.view.components.ModelChatItem
import com.example.foodrecipeapplicaiton.view.components.ModelChatTypingIndicator
import com.example.foodrecipeapplicaiton.view.components.UserChatItem
import com.example.foodrecipeapplicaiton.view.components.UserChatTypingIndicator
import com.example.foodrecipeapplicaiton.viewmodel.ChatViewModel
import com.example.foodrecipeapplicaiton.viewmodel.event.ChatUIEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
    uriState: MutableStateFlow<String>
) {
    val chatViewModel = viewModel<ChatViewModel>()
    val chatState = chatViewModel.chatState.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    var isTyping by remember { mutableStateOf(false) }

    val bitmap = getBitmap(uriState)

    val lazyListState = rememberLazyListState()
    val darkTheme = isSystemInDarkTheme()


    // Mesaj eklediğinde otomatik olarak en altına kaydır
    if (chatState.chatList.isNotEmpty()) {
        LaunchedEffect(chatState.chatList) {
            lazyListState.scrollToItem(chatState.chatList.size - 1)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            reverseLayout = false,
            state = lazyListState
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            itemsIndexed(chatState.chatList) { index, chat ->
                if (chat.isFromUser) {
                    UserChatItem(
                        prompt = chat.prompt,
                        bitmap = chat.bitmap,
                        sentTime = chat.sentTime
                    )
                } else {
                    ModelChatItem(response = chat.prompt, sentTime = chat.sentTime)
                }
            }
            // Kullanıcı veya AI yazıyorsa ilgili kısmı göster
            item {
                if (isTyping) {
                    UserChatTypingIndicator()
                } else if (chatState.isAIResponding) {
                    ModelChatTypingIndicator()
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(android.graphics.Color.parseColor("#F691A9"))),
                value = chatState.prompt,
                onValueChange = {
                    chatViewModel.onEvent(ChatUIEvent.UpdatePrompt(it))
                    isTyping = it.isNotEmpty()
                },
                placeholder = {
                    Text(text = "Type a prompt")
                },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                imagePicker.launch(
                                    PickVisualMediaRequest
                                        .Builder()
                                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        .build()
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "picked image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.clip(RoundedCornerShape(6.dp))
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.BrowseGallery,
                                contentDescription = "Add Photo",
                                tint = if (darkTheme) Color.White else Color.Black
                            )
                        }
                    }
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                chatViewModel.onEvent(
                                    ChatUIEvent.SendPrompt(
                                        chatState.prompt,
                                        bitmap
                                    )
                                )
                                uriState.update { "" }
                                isTyping = false
                                keyboardController?.hide()
                            },
                        imageVector = Icons.Rounded.Send,
                        contentDescription = "Send prompt",
                        tint = if (darkTheme) Color.White else Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        chatViewModel.onEvent(
                            ChatUIEvent.SendPrompt(
                                chatState.prompt,
                                bitmap
                            )
                        )
                        uriState.update { "" }  // Clear URI state
                        keyboardController?.hide()  // Hide keyboard
                    }
                ),
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    unfocusedTextColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    disabledTextColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    disabledIndicatorColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    focusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    unfocusedIndicatorColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.White,
                    disabledPlaceholderColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    focusedPlaceholderColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    unfocusedPlaceholderColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        backgroundColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
                    )
                )

            )
        }
    }
}


@Composable
private fun getBitmap(uriState: MutableStateFlow<String>): Bitmap? {
    val uri = uriState.collectAsState().value

    val imageState: AsyncImagePainter.State = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    if (imageState is AsyncImagePainter.State.Success) {
        return imageState.result.drawable.toBitmap()
    }

    return null
}