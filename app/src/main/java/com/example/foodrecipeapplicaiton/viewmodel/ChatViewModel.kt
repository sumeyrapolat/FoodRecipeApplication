package com.example.foodrecipeapplicaiton.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapplicaiton.api.chatmodel.Chat
import com.example.foodrecipeapplicaiton.api.chatmodel.ChatData
import com.example.foodrecipeapplicaiton.viewmodel.event.ChatUIEvent
import com.example.foodrecipeapplicaiton.viewmodel.state.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ChatViewModel : ViewModel() {

    private val _chatState = MutableStateFlow(DataState())
    val chatState = _chatState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    private val _userName = MutableStateFlow("User")
    val userName = _userName.asStateFlow()

    init {
        fetchUserName()
    }

    private fun fetchUserName() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _userName.value = document.getString("username") ?: "User"
                        // After fetching the user's name, add the initial chat message
                        createInitialChat()
                    } else {
                        // If user document does not exist, just create the initial chat
                        createInitialChat()
                    }
                }
        } else {
            // If user is not logged in, just create the initial chat
            createInitialChat()
        }
    }

    private fun createInitialChat() {
        val welcomeMessage = "Hello ${_userName.value}, I am your Food Assistant. How can I help you today? Are you looking for any specific recipes or places to eat?"
        _chatState.update { currentState ->
            currentState.copy(
                chatList = currentState.chatList.toMutableList().apply {
                    add(Chat(welcomeMessage, null, Date(), false))
                }
            )
        }
    }

    fun onEvent(event: ChatUIEvent) {
        when (event) {
            is ChatUIEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()) {
                    addPrompt(event.prompt, event.bitmap)
                    _chatState.update { it.copy(isAIResponding = true) }
                    if (event.bitmap != null) {
                        getResponseWithImage(event.prompt, event.bitmap)
                    } else {
                        getResponse(event.prompt)
                    }
                }
            }
            is ChatUIEvent.UpdatePrompt -> {
                _chatState.update { it.copy(prompt = event.newPrompt) }
            }
        }
    }

    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(Chat(prompt, bitmap, Date(), true))
                },
                prompt = "",
                bitmap = null
            )
        }
    }

    private fun addResponse(response: String) {
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(Chat(response, null, Date(), false))
                },
                isAIResponding = false
            )
        }
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            delay(2000) // Simulate network delay
            val chat = ChatData.getResponse(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(chat)
                    },
                    isAIResponding = false
                )
            }
        }
    }

    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            delay(2000) // Simulate network delay
            val chat = ChatData.getResponseWithImage(prompt, bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(chat)
                    },
                    isAIResponding = false
                )
            }
        }
    }
}
