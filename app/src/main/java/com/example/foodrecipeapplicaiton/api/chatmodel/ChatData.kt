package com.example.foodrecipeapplicaiton.api.chatmodel

import android.graphics.Bitmap
import android.hardware.biometrics.BiometricPrompt
import com.example.foodrecipeapplicaiton.api.key.Constants.API_KEY_GEMINI
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

object ChatData {


    suspend fun getResponse(prompt: String): Chat{

        val generativeModel = GenerativeModel(
            modelName = "gemini-pro", apiKey = API_KEY_GEMINI
        )
        try {
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }

            return Chat(
                prompt= response.text ?: "Error",
                bitmap = null,
                isFromUser = false,
                sentTime = Date()

            )

        }catch (e: Exception){

            return Chat(
                prompt= e.message ?: "Error",
                bitmap = null,
                isFromUser = false,
                sentTime = Date()

            )

        }
    }

    suspend fun getResponseWithImage(prompt: String, bitmap: Bitmap): Chat{

        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision", apiKey = API_KEY_GEMINI
        )
        try {
            val inputContent =  content {
                image(bitmap) // I just
                text(prompt)
            }

            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(inputContent)
            }

            return Chat(
                prompt= response.text ?: "Error",
                bitmap = null,
                isFromUser = false,
                sentTime = Date()

            )

        }catch (e: Exception){

            return Chat(
                prompt= e.message ?: "Error",
                bitmap = null,
                isFromUser = false,
                sentTime = Date()

            )

        }
    }
}