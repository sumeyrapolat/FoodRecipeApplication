package com.example.foodrecipeapplicaiton.viewmodel.state

import android.graphics.Bitmap
import com.example.foodrecipeapplicaiton.api.chatmodel.Chat

data class DataState(
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt : String = "",
    val bitmap: Bitmap? = null,
    val isAwaitingName: Boolean = true,
    val isAIResponding: Boolean = false


)

